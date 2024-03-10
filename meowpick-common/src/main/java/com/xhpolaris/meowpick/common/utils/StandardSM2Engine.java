package com.xhpolaris.meowpick.common.utils;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine.Mode;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * 自定义SM2Engine类，对加密解密数据进行了ASN.1编码
 */
public class StandardSM2Engine {

    private final Digest digest;
    private final Mode mode;

    private boolean forEncryption;
    private ECKeyParameters ecKey;
    private ECDomainParameters ecParams;
    private int curveLength;
    private SecureRandom random;

    public StandardSM2Engine() {
        this(new SM3Digest());
    }

    public StandardSM2Engine(Mode mode) {
        this(new SM3Digest(), mode);
    }

    public StandardSM2Engine(Digest digest) {
        this(digest, Mode.C1C3C2);
    }

    public StandardSM2Engine(Digest digest, Mode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("mode cannot be NULL");
        }
        this.digest = digest;
        this.mode = mode;
    }

    public void init(boolean forEncryption, CipherParameters param) {
        this.forEncryption = forEncryption;

        if (forEncryption) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;

            ecKey = (ECKeyParameters) rParam.getParameters();
            ecParams = ecKey.getParameters();

            ECPoint s = ((ECPublicKeyParameters) ecKey).getQ().multiply(ecParams.getH());
            if (s.isInfinity()) {
                throw new IllegalArgumentException("invalid key: [h]Q at infinity");
            }

            random = rParam.getRandom();
        } else {
            ecKey = (ECKeyParameters) param;
            ecParams = ecKey.getParameters();
        }

        curveLength = (ecParams.getCurve().getFieldSize() + 7) / 8;
    }

    public byte[] processBlock(byte[] in, int inOff, int inLen) throws InvalidCipherTextException {
        if (forEncryption) {
            return encrypt(in, inOff, inLen);
        } else {
            return decrypt(in, inOff, inLen);
        }
    }

    public int getOutputSize(int inputLen) {
        return (1 + 2 * curveLength) + inputLen + digest.getDigestSize();
    }

    protected ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    private byte[] encrypt(byte[] in, int inOff, int inLen) throws InvalidCipherTextException {
        byte[] c2 = new byte[inLen];

        System.arraycopy(in, inOff, c2, 0, c2.length);

        ECMultiplier multiplier = createBasePointMultiplier();

        ECPoint c1P;
        ECPoint kPB;
        do {
            BigInteger k = nextK();

            c1P = multiplier.multiply(ecParams.getG(), k).normalize();

            kPB = ((ECPublicKeyParameters) ecKey).getQ().multiply(k).normalize();

            kdf(digest, kPB, c2);
        } while (notEncrypted(c2, in, inOff));

        byte[] c3 = new byte[digest.getDigestSize()];

        addFieldElement(digest, kPB.getAffineXCoord());
        digest.update(in, inOff, inLen);
        addFieldElement(digest, kPB.getAffineYCoord());

        digest.doFinal(c3, 0);

        return convertToASN1(c1P, c2, c3);
    }

    private byte[] decrypt(byte[] in, int inOff, int inLen) throws InvalidCipherTextException {
        byte[] decryptData = new byte[inLen];
        System.arraycopy(in, inOff, decryptData, 0, decryptData.length);

        BigInteger x;
        BigInteger y;
        byte[] originC3;
        byte[] c2;
        ECPoint c1P;
        byte[] c1;
        try (ASN1InputStream aIn = new ASN1InputStream(decryptData)) {
            ASN1Sequence seq;
            try {
                seq = (ASN1Sequence) aIn.readObject();
            } catch (IOException e) {
                throw new InvalidCipherTextException();
            }
            x = ASN1Integer.getInstance(seq.getObjectAt(0)).getValue();
            y = ASN1Integer.getInstance(seq.getObjectAt(1)).getValue();
            c1P = ecParams.getCurve().validatePoint(x, y);
            c1 = c1P.getEncoded(false);
            if (mode == Mode.C1C3C2) {
                originC3 = ASN1OctetString.getInstance(seq.getObjectAt(2)).getOctets();
                c2 = ASN1OctetString.getInstance(seq.getObjectAt(3)).getOctets();
            } else {
                c2 = ASN1OctetString.getInstance(seq.getObjectAt(2)).getOctets();
                originC3 = ASN1OctetString.getInstance(seq.getObjectAt(3)).getOctets();
            }
        } catch (IOException e) {
            throw new InvalidCipherTextException();
        }

        ECPoint s = c1P.multiply(ecParams.getH());
        if (s.isInfinity()) {
            throw new InvalidCipherTextException("[h]C1 at infinity");
        }

        c1P = c1P.multiply(((ECPrivateKeyParameters) ecKey).getD()).normalize();

        kdf(digest, c1P, c2);

        byte[] c3 = new byte[digest.getDigestSize()];

        addFieldElement(digest, c1P.getAffineXCoord());
        digest.update(c2, 0, c2.length);
        addFieldElement(digest, c1P.getAffineYCoord());

        digest.doFinal(c3, 0);

        int check = 0;
        for (int i = 0; i != c3.length; i++) {
            check |= c3[i] ^ originC3[i];
        }

        Arrays.fill(c1, (byte) 0);
        Arrays.fill(c3, (byte) 0);

        if (check != 0) {
            Arrays.fill(c2, (byte) 0);
            throw new InvalidCipherTextException("invalid cipher text");
        }

        return c2;
    }

    private boolean notEncrypted(byte[] encData, byte[] in, int inOff) {
        for (int i = 0; i != encData.length; i++) {
            if (encData[i] != in[inOff + i]) {
                return false;
            }
        }

        return true;
    }

    private void kdf(Digest digest, ECPoint c1, byte[] encData) {
        int digestSize = digest.getDigestSize();
        byte[] buf = new byte[Math.max(4, digestSize)];
        int off = 0;

        Memoable memo = null;
        Memoable copy = null;

        if (digest instanceof Memoable) {
            addFieldElement(digest, c1.getAffineXCoord());
            addFieldElement(digest, c1.getAffineYCoord());
            memo = (Memoable) digest;
            copy = memo.copy();
        }

        int ct = 0;

        while (off < encData.length) {
            if (memo != null) {
                memo.reset(copy);
            } else {
                addFieldElement(digest, c1.getAffineXCoord());
                addFieldElement(digest, c1.getAffineYCoord());
            }

            Pack.intToBigEndian(++ct, buf, 0);
            digest.update(buf, 0, 4);
            digest.doFinal(buf, 0);

            int xorLen = Math.min(digestSize, encData.length - off);
            xor(encData, buf, off, xorLen);
            off += xorLen;
        }
    }

    private void xor(byte[] data, byte[] kdfOut, int dOff, int dRemaining) {
        for (int i = 0; i != dRemaining; i++) {
            data[dOff + i] ^= kdfOut[i];
        }
    }

    private BigInteger nextK() {
        int qBitLength = ecParams.getN().bitLength();

        BigInteger k;
        do {
            k = BigIntegers.createRandomBigInteger(qBitLength, random);
        } while (k.equals(BigIntegers.ZERO) || k.compareTo(ecParams.getN()) >= 0);

        return k;
    }

    private void addFieldElement(Digest digest, ECFieldElement v) {
        byte[] p = BigIntegers.asUnsignedByteArray(curveLength, v.toBigInteger());

        digest.update(p, 0, p.length);
    }

    private byte[] convertToASN1(ECPoint c1P, byte[] c2, byte[] c3) {
        ASN1Integer x = new ASN1Integer(c1P.getXCoord().toBigInteger());
        ASN1Integer y = new ASN1Integer(c1P.getYCoord().toBigInteger());
        DEROctetString derDig = new DEROctetString(c3);
        DEROctetString derEnc = new DEROctetString(c2);
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (Objects.requireNonNull(mode) == Mode.C1C3C2) {
            v.add(x);
            v.add(y);
            v.add(derDig);
            v.add(derEnc);
        } else {
            v.add(x);
            v.add(y);
            v.add(derEnc);
            v.add(derDig);
        }
        DERSequence seq = new DERSequence(v);
        try {
            return seq.getEncoded();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
package com.xhpolaris.meowpick.infrastructure.rpc;

import com.xhpolaris.idlgen.platform.sts.PhotoCheckReq;
import com.xhpolaris.idlgen.platform.sts.PhotoCheckResp;
import com.xhpolaris.idlgen.platform.sts.StsServiceGrpc;
import io.grpc.Metadata;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class PlatformSts {
    @GrpcClient("platform-sts")
    private StsServiceGrpc.StsServiceBlockingStub stub;

    public PhotoCheckResp photoCheck(PhotoCheckReq req) {
        return this.stub.photoCheck(req);
    }
}

package com.xhpolaris.meowpick.infrastructure.rpc;

import com.xhpolaris.idlgen.platform.sts.PhotoCheckReq;
import com.xhpolaris.idlgen.platform.sts.PhotoCheckResp;
import com.xhpolaris.idlgen.platform.sts.StsServiceGrpc;
import com.xhpolaris.idlgen.platform.sts.TextCheckReq;
import com.xhpolaris.idlgen.platform.sts.TextCheckResp;
import com.xhpolaris.meowpick.domain.service.Context;
import io.grpc.Metadata;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlatformSts {
    @GrpcClient("platform-sts")
    private       StsServiceGrpc.StsServiceBlockingStub stub;
    private final Context                               context;

    public PhotoCheckResp photoCheck(PhotoCheckReq req) {
        return this.stub.photoCheck(req);
    }

    public TextCheckResp textCheck(String text) {
        return this.stub.textCheck(TextCheckReq.newBuilder()
                                               .setUser(context.getUser().getUserMeta())
                                               .setText(text)
                                               .build());
    }
}

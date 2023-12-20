package com.xhpolaris.meowchat.mall.infrastructure.rpc.meowchat_content;

import com.xhpolaris.idlgen.meowchat.content.ContentServiceGrpc;
import com.xhpolaris.idlgen.meowchat.content.ListPostReq;
import com.xhpolaris.idlgen.meowchat.content.ListPostResp;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;

public class MeowchatContentService {
    private final ManagedChannel managedChannel = Grpc.newChannelBuilderForAddress("meowchat-content.xh-polaris", 8080, InsecureChannelCredentials.create()).build();
    private final ContentServiceGrpc.ContentServiceBlockingStub stub = ContentServiceGrpc.newBlockingStub(managedChannel);

    public ListPostResp listPost(ListPostReq req) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("X_XH_ENV", Metadata.ASCII_STRING_MARSHALLER), "test");
        return this.stub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata)).listPost(req);
    }
}

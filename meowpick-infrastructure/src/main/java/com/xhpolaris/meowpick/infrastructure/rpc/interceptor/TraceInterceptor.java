package com.xhpolaris.meowpick.infrastructure.rpc.interceptor;

import io.grpc.*;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapSetter;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;

@RequiredArgsConstructor
@GrpcGlobalClientInterceptor
public class TraceInterceptor implements ClientInterceptor {
    private final OpenTelemetry openTelemetry;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                Context currentContext = Context.current();
                // 将 trace 上下文注入到 gRPC 请求的 Metadata 中
                TextMapSetter<Metadata> setter = (metadata, key, value) -> {
                    if (key.equals("traceparent")){
                        key = "rpc-transit-"+key;
                    }
                    metadata.put(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER), value);
                };
                openTelemetry.getPropagators().getTextMapPropagator().inject(currentContext, headers, setter);
                super.start(responseListener, headers);
            }
        };
    }
}
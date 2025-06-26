package fuji.fhttp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.HttpHeaderNames.*;

import java.nio.charset.StandardCharsets;

public class Response {
    private final ChannelHandlerContext ctx;
    private final boolean keepAlive;

    public Response(ChannelHandlerContext ctx, boolean keepAlive) {
        this.ctx = ctx;
        this.keepAlive = keepAlive;
    }

    public void send(String body) {
        ByteBuf content = ctx.alloc().buffer();
        content.writeCharSequence(body, StandardCharsets.UTF_8);

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, content.readableBytes());
        if (keepAlive)
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.writeAndFlush(response, ctx.voidPromise());
    }

    public void notFound(String body) {
        ByteBuf content = ctx.alloc().buffer();
        content.writeCharSequence(body, StandardCharsets.UTF_8);

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.NOT_FOUND, content);
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, content.readableBytes());
        if (keepAlive)
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.writeAndFlush(response, ctx.voidPromise());
    }
}

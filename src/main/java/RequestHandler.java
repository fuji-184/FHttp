import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class RequestHandler extends ChannelInboundHandlerAdapter {
    private HttpRequest request;
    private final Router router;
    private final StringBuilder bodyBuilder = new StringBuilder();

    public RequestHandler(Router router) {
        this.router = router;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof HttpRequest) {
                request = (HttpRequest) msg;
                bodyBuilder.setLength(0);
            }

            if (msg instanceof HttpContent) {
                ByteBuf content = ((HttpContent) msg).content();
                if (content.isReadable()) {
                    bodyBuilder.append(content.toString(StandardCharsets.UTF_8));
                }

                if (msg instanceof LastHttpContent) {
                    boolean keepAlive = HttpUtil.isKeepAlive(request);
                    Response res = new Response(ctx, keepAlive);
                    String method = request.method().name();
                    String path = request.uri().split("\\?")[0];

                    BasicRequest req = new BasicRequest(request, bodyBuilder.toString());
                    router.route(method, path).accept(req, res);

                    request = null;
                }
            }
        } finally {
            if (msg instanceof HttpContent) {
                ((HttpContent) msg).release();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

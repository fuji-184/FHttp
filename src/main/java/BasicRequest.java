package fuji.fhttp;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

public class BasicRequest {
    private final HttpRequest request;
    private final String body;

    public BasicRequest(HttpRequest request, String body) {
        this.request = request;
        this.body = body;
    }

    public String method() {
        return request.method().name();
    }

    public String uri() {
        return request.uri();
    }

    public HttpHeaders headers() {
        return request.headers();
    }

    public String body() {
        return body;
    }
}

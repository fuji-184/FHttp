package fuji.fhttp;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.ConcurrentHashMap;

public class Router {
    private final Map<String, BiConsumer<BasicRequest, Response>> routes;

    public Router(Map<String, BiConsumer<BasicRequest, Response>> routes) {
        this.routes = Map.copyOf(routes);
    }

    public BiConsumer<BasicRequest, Response> route(String method, String path) {
        return routes.getOrDefault(method + " " + path,
            (req, res) -> res.notFound("Not Found"));
    }

    public static class Builder {
        private final Map<String, BiConsumer<BasicRequest, Response>> map = new ConcurrentHashMap<>();

        public Builder get(String path, BiConsumer<BasicRequest, Response> handler) {
            map.put("GET " + path, handler);
            return this;
        }

        public Router build() {
            return new Router(map);
        }
    }
}

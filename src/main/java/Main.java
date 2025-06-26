public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router.Builder()
            .get("/", (req, res) -> res.send("Hello from Netty!"))
            .get("/hello", (req, res) -> res.send("Hai Dunia!"))
            .build();

        new Server(8080, router).start();
    }
}

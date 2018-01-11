import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;

public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpClient client = vertx.createHttpClient();
        int delay = 5000;
        String host = "localhost";
        String url = "/";
        int port = 80;

        long timerjson = vertx.setPeriodic(delay, id -> {
            client.getNow(port, host, url, response -> {
                int responseCode = response.statusCode();
                if (responseCode == 200) {
                    response.bodyHandler(bufferResponse -> {
                        System.out.println(bufferResponse.toString());
                    });
                }
            });
        });
    }
}

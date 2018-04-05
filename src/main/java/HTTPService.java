import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class HTTPService {

    public static void run(String[] args) {

        Vertx vertx = Vertx.vertx();
        HttpClient client = vertx.createHttpClient();

        int delay = 5000;
        String host = "localhost";
        String url = "/";
        int port = 80;

        System.out.println("HTTP Service Started");

        vertx.setPeriodic(delay, id -> {
            client.getNow(port, host, url, response -> {
                int responseCode = response.statusCode();
                if (responseCode == 200) {
                    response.bodyHandler(bufferResponse -> {
                        System.out.println("HTTP Service: " + responseCode);
                        //client.post(port, host, url);
                    });
                }
            });
        });
    }
}

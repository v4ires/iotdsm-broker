package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class HTTPService {

    private static final Logger log = LoggerFactory.getLogger(HTTPService.class);

    public static void run() {

        Vertx vertx = Vertx.vertx();
        HttpClient client = vertx.createHttpClient();

        int delay = Integer.parseInt(PropertiesReader.getValue("BROKER_REQUEST_DELAY"));
        String host = PropertiesReader.getValue("BROKER_HOST");
        String url = PropertiesReader.getValue("BROKER_REQUEST_URL");
        int port = Integer.parseInt(PropertiesReader.getValue("BROKER_PORT"));

        log.info("HTTP Service Started");

        vertx.setPeriodic(delay, id -> {
            client.getNow(port, host, url, response -> {
                int responseCode = response.statusCode();
                if (responseCode == 200) {
                    response.bodyHandler(bufferResponse -> {
                        log.info("HTTP Service: " + responseCode);
                        //client.post(port, host, url);
                    });
                }
            });
        });
    }

    public static void main(String[] args) {
        run();
    }
}

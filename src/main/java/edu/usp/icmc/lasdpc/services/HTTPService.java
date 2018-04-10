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

        int number_of_clients = Integer.parseInt(PropertiesReader.getValue("BROKER_NUM_CLIENT"));

        String[] delay = PropertiesReader.getValue("BROKER_REQUEST_DELAY").split(",");
        String[] host = PropertiesReader.getValue("BROKER_HOST").split(",");
        String[] url = PropertiesReader.getValue("BROKER_REQUEST_URL").split(",");
        String[] port = PropertiesReader.getValue("BROKER_PORT").split(",");

        log.info("HTTP Service Started");

        for (int i = 0; i < number_of_clients; i++) {
            int curr_i = i;
            vertx.setPeriodic(Integer.parseInt(delay[curr_i]), id -> {
                client.getNow(Integer.parseInt(port[curr_i]), host[curr_i], url[curr_i], response -> {
                    int responseCode = response.statusCode();
                    if (responseCode == 200) {
                        response.bodyHandler(bufferResponse -> {
                            log.info("HTTP Service: " + responseCode + " Host: " + host[curr_i]);
                            //client.post(port, host, url);
                        });
                    }
                });
            });
        }
    }

    public static void main(String[] args) {
        run();
    }
}

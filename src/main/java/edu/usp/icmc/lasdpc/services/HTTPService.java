package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class HTTPService {

    private static final Logger log = LoggerFactory.getLogger(HTTPService.class);

    public static void runHttpClient() {

        Vertx vertx = Vertx.vertx();
        HttpClient client = vertx.createHttpClient();

        int number_of_clients = Integer.parseInt(PropertiesReader.getValue("BROKER_NUM_CLIENT"));

        String[] delay = PropertiesReader.getValue("BROKER_REQUEST_DELAY").split(",");
        String[] host = PropertiesReader.getValue("BROKER_HOSTNAME").split(",");
        String[] url = PropertiesReader.getValue("BROKER_REQUEST_URL").split(",");
        String[] port = PropertiesReader.getValue("BROKER_PORT").split(",");

        log.info("HTTP Service Started");

        IntStream.range(0, number_of_clients).forEach(i -> {
            vertx.setPeriodic(Integer.parseInt(delay[i]), id -> {
                client.getNow(Integer.parseInt(port[i]), host[i], url[i], response -> {
                    int responseCode = response.statusCode();
                    if (responseCode == 200) {
                        response.bodyHandler(bufferResponse -> {
                            log.info("HTTP Service: " + responseCode + " Host: " + host[i]);
                            //client.post(port, host, url);
                        });
                    }
                });
            });
        });
    }

    public static void runHttpServer() {

        String delay = PropertiesReader.getValue("BROKER_REQUEST_DELAY");
        String host = PropertiesReader.getValue("BROKER_HOSTNAME");
        String url = PropertiesReader.getValue("BROKER_REQUEST_URL");
        String port = PropertiesReader.getValue("BROKER_PORT");

        log.info("HTTP Service Started (" + port + ")");

        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.post("/sensor").handler(ctx -> {
            int status = ctx.response().getStatusCode();
            String msg = ctx.getBodyAsString();
            log.info("HTTP Service: " + status + " Host: " + host);
            //System.out.println(msg);
            //client.post(port, host, url);
            ctx.response().end();
        });

        server.requestHandler(router::accept).listen(8081);
    }

    public static void main(String[] args) {
        runHttpServer();
    }
}

package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
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
        WebClient webClient = WebClient.create(vertx);

        int number_of_clients = Integer.parseInt(PropertiesReader.getValue("BROKER_NUM_CLIENT"));

        //Broker Properties
        String[] delay = PropertiesReader.getValue("BROKER_REQUEST_DELAY").split(",");
        String[] host = PropertiesReader.getValue("BROKER_HOSTNAME").split(",");
        String[] url = PropertiesReader.getValue("BROKER_REQUEST_URL").split(",");
        String[] port = PropertiesReader.getValue("BROKER_PORT").split(",");

        //Service Properties
        String service_hostname = PropertiesReader.getValue("SERVICE_HOSTNAME");
        int service_port = Integer.parseInt(PropertiesReader.getValue("SERVICE_PORT"));
        String service_path = PropertiesReader.getValue("SERVICE_PATH");


        log.info("HTTP Service Started");

        IntStream.range(0, number_of_clients).forEach(i -> {
            vertx.setPeriodic(Integer.parseInt(delay[i]), id -> {
                client.getNow(Integer.parseInt(port[i]), host[i], url[i], response -> {
                    int responseCode = response.statusCode();
                    if (responseCode == 200) {
                        response.bodyHandler(bufferResponse -> {
                            System.out.println(bufferResponse.toString());
                            webClient.post(service_port,  service_hostname, service_path).sendJson(bufferResponse.toString(), ar ->{
                                if(ar.succeeded()){
                                    log.info("HTTP Service: " + responseCode + " Host: " + host[i]);
                                }
                            });
                        });
                    }
                });
            });
        });
    }

    public static void runHttpServer() {

        //Broker Properties
        String broker_hostname = PropertiesReader.getValue("BROKER_HOSTNAME");
        String broker_url = PropertiesReader.getValue("BROKER_REQUEST_URL");
        int broker_port = Integer.parseInt(PropertiesReader.getValue("BROKER_PORT"));

        //Service Properties
        String service_hostname = PropertiesReader.getValue("SERVICE_HOSTNAME");
        int service_port = Integer.parseInt(PropertiesReader.getValue("SERVICE_PORT"));
        String service_path = PropertiesReader.getValue("SERVICE_PATH");

        log.info("HTTP Service Started (" + broker_port + ")");

        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.post("/sensor").handler(ctx -> {
            int status = ctx.response().getStatusCode();
            String msg = ctx.getBodyAsString();
            log.info("HTTP Service: " + status + " Host: " + broker_hostname);
            /*client.post(service_port,  service_hostname, service_path).sendJson(msg, ar ->{
                if(ar.succeeded()){
                    ctx.response().end();
                }
            });*/
            System.out.println(msg);
        });
        server.requestHandler(router::accept).listen(broker_port);
    }

    public static void main(String[] args) {
        runHttpServer();
    }
}

package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
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

        //Broker Properties
        String broker_url = PropertiesReader.getValue("BROKER_REQUEST_URL");
        int broker_port = Integer.parseInt(PropertiesReader.getValue("BROKER_PORT"));

        //Service Properties
        String service_hostname = PropertiesReader.getValue("SERVICE_HOSTNAME");
        int service_port = Integer.parseInt(PropertiesReader.getValue("SERVICE_PORT"));
        String service_path = PropertiesReader.getValue("SERVICE_PATH");

        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.post(broker_url).handler(ctx -> {
            MultiMap headers = ctx.request().headers();
            headers.set("Content-Type", "application/json");

            String token = ctx.request().getHeader("Authorization");
            if (!token.isEmpty()) {
                headers.set("Authorization", token);
            }

            String payload = ctx.getBodyAsString();
            HttpRequest<Buffer> request = client.post(service_port, service_hostname, service_path);

            request.sendJson(payload, response -> {
                if(response.succeeded()){
                    log.info("Request executed successful.");
                } else {
                    log.info("Request executed with error.");
                    ctx.response().setStatusCode(500);
                }
                ctx.response().end();
            });
        });
        server.requestHandler(router::accept).listen(broker_port);
    }

    public static void main(String[] args) {
        run();
    }
}

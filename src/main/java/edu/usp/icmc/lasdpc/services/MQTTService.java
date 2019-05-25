package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class MQTTService {

    private static final Logger log = LoggerFactory.getLogger(MQTTService.class);

    public static void run() {

        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        //Service Properties
        String service_hostname = PropertiesReader.getValue("SERVICE_HOSTNAME");
        int service_port = Integer.parseInt(PropertiesReader.getValue("SERVICE_PORT"));
        String service_path = PropertiesReader.getValue("SERVICE_PATH");

        MqttServer server = MqttServer.create(vertx);

        server.endpointHandler(endpoint -> {
            endpoint.publishHandler(message -> {
                String msg = message.payload().toString();
                client.post(service_port, service_hostname, service_path).sendJson(msg, ar -> {
                    if (ar.succeeded()) {
                        log.info("Request executed successful.");
                    }else{
                        log.info("Request executed with error.");
                    }
                });
            });
            endpoint.accept(true);
        });
        server.listen(ar -> {
            if (ar.succeeded()) {
                log.info("MQTT Service Started (" + server.actualPort() + ")");
            } else {
                log.error("MQTT Service error on start" + ar.cause().getMessage());
            }
        });
    }

    public static void main(String[] args) {
        run();
    }
}

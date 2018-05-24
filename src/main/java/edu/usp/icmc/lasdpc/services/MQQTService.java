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
public class MQQTService {

    private static final Logger log = LoggerFactory.getLogger(MQQTService.class);

    public static void run() {

        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        //Broker Properties
        String host = PropertiesReader.getValue("BROKER_HOSTNAME");
        int port = Integer.parseInt(PropertiesReader.getValue("BROKER_PORT"));

        //Service Properties
        String service_hostname = PropertiesReader.getValue("SERVICE_HOSTNAME");
        int service_port = Integer.parseInt(PropertiesReader.getValue("SERVICE_PORT"));
        String service_path = PropertiesReader.getValue("SERVICE_PATH");

        /*MqttServerOptions options = new MqttServerOptions()
                .setPort(port)
                .setHost("localhost");
                .setHost(host);*/

        MqttServer server = MqttServer.create(vertx);

        server.endpointHandler(endpoint -> {
            endpoint.publishHandler(message -> {
                String msg = message.payload().toString();
                if(message.qosLevel() == MqttQoS.AT_LEAST_ONCE){
                    endpoint.publishAcknowledge(message.messageId());
                }
                else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
                    endpoint.publishRelease(message.messageId());
                }
                log.info("MQQT Service: OK");

                client.post(service_port, service_hostname, service_path).sendJson(msg, ar -> {
                    if (ar.succeeded()) {
                        if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
                            endpoint.publishAcknowledge(message.messageId());
                        } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
                            endpoint.publishReceived(message.messageId());
                        }
                        log.info("MSG MQTT: OK");
                    }else{
                        log.info("ERROW");
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
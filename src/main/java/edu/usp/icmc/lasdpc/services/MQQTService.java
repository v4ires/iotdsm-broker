package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.BrokerMain;
import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
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
        String host = PropertiesReader.getValue("BROKER_HOSTNAME");
        int port = Integer.parseInt(PropertiesReader.getValue("BROKER_PORT"));

        MqttServerOptions options = new MqttServerOptions()
                .setPort(port)
                .setHost(host);

        MqttServer server = MqttServer.create(vertx, options);

        server.endpointHandler(endpoint -> {
            endpoint.publishHandler(message -> {
                //String msg = "Just received message on [" + message.topicName() + "] payload [" + message.payload() + "] with QoS [" + message.qosLevel() + "]";
                log.info("MQQT Service: OK");
                //client.post(port, host, url);
                if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
                    endpoint.publishAcknowledge(message.messageId());
                } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
                    endpoint.publishReceived(message.messageId());
                }
            });
            endpoint.accept(false);
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
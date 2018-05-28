package edu.usp.icmc.lasdpc.client;


import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;

public class MQTTClient extends AbstractVerticle {

    private static final String MQTT_TOPIC = "/sensor";
    private static final String MQTT_MESSAGE = "Alo 123 Testando";
    private static final String BROKER_HOST = "localhost";
    private static final int BROKER_PORT = 1883;

    public static void run() {
        Vertx vertx = Vertx.vertx();
        MqttClient mqttClient = MqttClient.create(vertx);
        mqttClient.connect(BROKER_PORT, BROKER_HOST, ch -> {
            if (ch.succeeded()) {
                mqttClient.publish(
                        MQTT_TOPIC,
                        Buffer.buffer(MQTT_MESSAGE),
                        MqttQoS.AT_LEAST_ONCE,
                        false,
                        false,
                        s -> vertx.close());
            } else {
                System.out.println("Failed to connect to a server");
                System.out.println(ch.cause());
            }
        });
    }

    public static void main(String[] args) {
        run();
    }
}

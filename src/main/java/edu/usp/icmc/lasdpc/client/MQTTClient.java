package edu.usp.icmc.lasdpc.client;


import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MQTTClient extends AbstractVerticle {

    private static final String MQTT_TOPIC = "/sensor";
    private static final String MQTT_MESSAGE = "Alo 123 Testando";
    private static final String BROKER_HOST = "localhost";
    private static final int BROKER_PORT = 1883;

    public static void run(String msg) {
        Vertx vertx = Vertx.vertx();
        MqttClient mqttClient = MqttClient.create(vertx);
        mqttClient.connect(BROKER_PORT, BROKER_HOST, ch -> {
            if (ch.succeeded()) {
                mqttClient.publish(
                        MQTT_TOPIC,
                        Buffer.buffer(msg),
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

    public static void main(String[] args) throws IOException {
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(10);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("mqtt.log")));
        execService.scheduleAtFixedRate(() -> {
            try {
                Long t0 = System.nanoTime();
                run("Hello World");
                atomicInteger.incrementAndGet();
                if (atomicInteger.get() == 10) {
                    bw.close();
                    System.exit(0);
                }
                bw.write("" + (System.nanoTime() - t0));
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 1, 100L, TimeUnit.MILLISECONDS);
    }
}

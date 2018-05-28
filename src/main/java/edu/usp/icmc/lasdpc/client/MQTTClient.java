package edu.usp.icmc.lasdpc.client;


import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MQTTClient extends AbstractVerticle {

    private static final String MQTT_TOPIC = "/sensor";
    private static final String MQTT_MESSAGE = "Alo 123 Testando";
    private static final String[] BROKER_HOST = {"localhost", "tpnode10"};
    private static final int BROKER_PORT = 1883;

    private static String _log4jFile = "log4j.properties";
    private static String _logLevel = "ALL";
    private static final Logger log = LoggerFactory.getLogger(CoAPClient.class);


    public static void run(String msg, int host) {
        Vertx vertx = Vertx.vertx();
        MqttClient mqttClient = MqttClient.create(vertx);
        mqttClient.connect(BROKER_PORT, BROKER_HOST[host], ch -> {
            if (ch.succeeded()) {
                mqttClient.publish(
                        MQTT_TOPIC,
                        Buffer.buffer(msg),
                        MqttQoS.AT_LEAST_ONCE,
                        false,
                        false,
                        s -> mqttClient.disconnect());
                vertx.close();
                log.info("Foi");
            } else {
                log.info("Não Foi");
            }
        });
    }

    public static void main(String[] args) throws IOException {
        enableLog4J(_logLevel);
        String msg = "{\"id\":\"1\",\"name\":\"dht11-0\",\"lat\":\"-22.0039007\",\"lng\":\"-47.891811\",\"name\":\"temperature sensor\",\"create_time\":\"2018-02-12 09:29:05.441\",\"description\":\"the DHT11 is a temperature and humidity sensor\",\"sensor_source\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"descrition\":\"sensor network of temperature and humidity\",\"name\":\"dht11-sensor\"},\"sensor_measure\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"value\":\"36.4\",\"sensor_measure_type\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"unit\":\"C\"}}}";
        int core = Runtime.getRuntime().availableProcessors();
        int host = Integer.parseInt(args[1]);
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(core);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(args[2])));
        bw.write(LocalTime.now().toString());
        bw.newLine();
        execService.scheduleAtFixedRate(() -> {
            try {
                Long t0 = System.nanoTime();
                run(msg, host);
                atomicInteger.incrementAndGet();
                if (atomicInteger.get() > Integer.parseInt(args[0])) {
                    bw.close();
                    System.out.println("Terminou experimento");
                    System.exit(0);
                }
                bw.write("" + (System.nanoTime() - t0));
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 1, 100L, TimeUnit.MILLISECONDS);
    }

    private static void enableLog4J(String logLevel) {
        LogManager.getRootLogger().setLevel(Level.toLevel(_logLevel));
        Properties properties = PropertiesReader.initialize(_log4jFile);
        LogManager.getRootLogger().setLevel(Level.toLevel(logLevel));
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(properties);
    }
}

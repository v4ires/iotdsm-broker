package edu.usp.icmc.lasdpc.client;

import org.eclipse.californium.core.CoapClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.eclipse.californium.core.coap.MediaTypeRegistry.APPLICATION_JSON;

public class CoAPClient {

    public static void run(String msg, int host) {
        String url [] = {"coap://localhost:5683/sensor", "coap://tpnode10:5683/sensor"};
        CoapClient coapClient = new CoapClient(url[host]);
        coapClient.post(msg, APPLICATION_JSON);
    }

    public static void main(String[] args) throws IOException {
        String msg = "{\"id\":\"1\",\"name\":\"dht11-0\",\"lat\":\"-22.0039007\",\"lng\":\"-47.891811\",\"name\":\"temperature sensor\",\"create_time\":\"2018-02-12 09:29:05.441\",\"description\":\"the DHT11 is a temperature and humidity sensor\",\"sensor_source\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"descrition\":\"sensor network of temperature and humidity\",\"name\":\"dht11-sensor\"},\"sensor_measure\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"value\":\"36.4\",\"sensor_measure_type\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"unit\":\"C\"}}}";
        int core = Runtime.getRuntime().availableProcessors();
        int host = Integer.parseInt(args[1]);
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(core);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("coap.log")));
        execService.scheduleAtFixedRate(() -> {
            try {
                Long t0 = System.nanoTime();
                run(msg, host);
                atomicInteger.incrementAndGet();
                if (atomicInteger.get() > Integer.parseInt(args[0])) {
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

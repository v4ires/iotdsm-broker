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

    public static void run(String msg) {
        String url = "coap://localhost:5683/sensor";
        CoapClient coapClient = new CoapClient(url);
        coapClient.post(msg, APPLICATION_JSON);
    }

    public static void main(String[] args) throws IOException {
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(10);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("coap.log")));
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

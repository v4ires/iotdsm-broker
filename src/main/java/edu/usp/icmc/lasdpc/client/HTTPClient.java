package edu.usp.icmc.lasdpc.client;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HTTPClient {

    public static void run(String msg, int host){
        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        String[] service_hostname = {"localhost", "tpnode10"};
        int service_port = 8082;
        String service_path = "/sensor";

        client.post(service_port,  service_hostname[0], service_path).sendJson(msg, ar ->{
            if(ar.succeeded()){
                System.out.println("Foi");
            }else {
                System.out.println("Não Foi");
            }
            vertx.close();
        });
    }

    public static void main(String[] args) throws IOException {
        String msg = "{\"id\":\"1\",\"name\":\"dht11-0\",\"lat\":\"-22.0039007\",\"lng\":\"-47.891811\",\"name\":\"temperature sensor\",\"create_time\":\"2018-02-12 09:29:05.441\",\"description\":\"the DHT11 is a temperature and humidity sensor\",\"sensor_source\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"descrition\":\"sensor network of temperature and humidity\",\"name\":\"dht11-sensor\"},\"sensor_measure\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"value\":\"36.4\",\"sensor_measure_type\":{\"create_time\":\"2018-02-12 09:29:05.441\",\"unit\":\"C\"}}}";
        int core = Runtime.getRuntime().availableProcessors();
        int host = Integer.parseInt(args[1]);
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(core);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("http.log")));
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

package edu.usp.icmc.lasdpc.client;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

public class HTTPClient {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        String service_hostname = "localhost";
        int service_port = 8082;
        String service_path = "/sensor";

        client.post(service_port,  service_hostname, service_path).sendJson("lalala", ar ->{
            if(ar.succeeded()){
                System.out.println("Foi");
            }else {
                System.out.println("Não Foi");
            }
            vertx.close();
        });

    }
}

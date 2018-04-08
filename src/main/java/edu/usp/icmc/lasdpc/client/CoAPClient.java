package edu.usp.icmc.lasdpc.client;

import org.eclipse.californium.core.CoapClient;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.APPLICATION_JSON;

public class CoAPClient {

    public static void main(String[] args) {
        String url = "coap://localhost:5683/sensor";
        CoapClient coapClient = new CoapClient(url);
        coapClient.post("{\"type\": \"ACC\", \"ts\":12412214231, \"user\":\"qw3q)io\", \"value\" : [1,2,3]}", APPLICATION_JSON);
        System.exit(0);
    }
}

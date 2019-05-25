package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class CoAPService extends CoapServer {

    private static final Logger log = LoggerFactory.getLogger(CoAPService.class);

    private static Vertx vertx = Vertx.vertx();
    private static WebClient client = WebClient.create(vertx);

    public static void run() {
        CoAPService server = new CoAPService();
        server.addEndpoints();
        server.start();
    }

    public static void main(String[] args) {
        run();
    }

    private void addEndpoints() {
        int broker_port = Integer.parseInt(PropertiesReader.getValue("BROKER_PORT"));

        for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
            if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
                InetSocketAddress bindToAddress = new InetSocketAddress(addr, broker_port);
                addEndpoint(new CoapEndpoint(bindToAddress));
            }
        }
    }

    public CoAPService() {
        add(new CoapServerResource());
    }

    class CoapServerResource extends CoapResource {

        public CoapServerResource() {
            super("sensor");
        }

        @Override
        public void handlePOST(CoapExchange exchange) {

            //Service Properties
            String service_hostname = PropertiesReader.getValue("SERVICE_HOSTNAME");
            int service_port = Integer.parseInt(PropertiesReader.getValue("SERVICE_PORT"));
            String service_path = PropertiesReader.getValue("SERVICE_PATH");

            String payload = exchange.getRequestText();
            exchange.respond(CoAP.ResponseCode.VALID);

            client.post(service_port, service_hostname, service_path).sendJson(payload, ar -> {
                if(ar.succeeded()){
                    log.info("Request executed successful.");
                }else{
                    log.info("Request executed with error.");
                }
            });
        }
    }
}
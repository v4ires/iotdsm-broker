package edu.usp.icmc.lasdpc.services;

import edu.usp.icmc.lasdpc.util.PropertiesReader;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import java.io.IOException;


/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class CoAPService extends CoapServer {

    private static final Logger log = LoggerFactory.getLogger(CoapServer.class);
    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);
    private static Vertx vertx = Vertx.vertx();
    private static WebClient client = WebClient.create(vertx);

    public static void run() {
        try {
            CoAPService server = new CoAPService();
            server.addEndpoints();
            server.start();
        } catch (SocketException e) {
            log.error("Failed to initialize server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        run();
    }

    private void addEndpoints() {
        for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
            if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
                InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
                addEndpoint(new CoapEndpoint(bindToAddress));
            }
        }
    }

    public CoAPService() throws SocketException {
        add(new CoapServerResource());
    }

    class CoapServerResource extends CoapResource {

        public CoapServerResource() {
            super("sensor");
        }

        @Override
        public void handlePOST(CoapExchange exchange) {
            float temp = 0;
            String msg = exchange.getRequestText();
            //Service Properties
            String service_hostname = PropertiesReader.getValue("SERVICE_HOSTNAME");
            int service_port = Integer.parseInt(PropertiesReader.getValue("SERVICE_PORT"));
            String service_path = PropertiesReader.getValue("SERVICE_PATH");

            //exchange.accept();
            exchange.respond(CoAP.ResponseCode.VALID);

            //exchange.respond(CoAP.ResponseCode._UNKNOWN_SUCCESS_CODE);
            //exchange.respond(CoAP.ResponseCode.CONTENT, "Alo");
            client.post(service_port, service_hostname, service_path).sendJson(msg, ar -> {
//                if (ar.succeeded()) {
//                    log.info("MSG CoAP: OK");
//                }else{
//                    log.info("Errow");
//                }
            });
        }
    }
}
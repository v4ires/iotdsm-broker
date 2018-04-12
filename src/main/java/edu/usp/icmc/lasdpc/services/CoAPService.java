package edu.usp.icmc.lasdpc.services;

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

    public static void run(){
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
            String msg = exchange.getRequestText();
            log.info("CoAP Service: " + exchange.getRequestCode() + " Host: " + "localhost");
            //System.out.println(msg);
            //client.post(port, host, url);
            exchange.respond(CoAP.ResponseCode._UNKNOWN_SUCCESS_CODE);
        }
    }
}
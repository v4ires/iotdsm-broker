package edu.usp.icmc.lasdpc.services;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;

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

    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

    public static void run(){
        try {
            CoAPService server = new CoAPService();
            server.addEndpoints();
            server.start();
        } catch (SocketException e) {
            System.err.println("Failed to initialize server: " + e.getMessage());
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
        public void handleGET(CoapExchange exchange) {
            exchange.respond("Hello World!");
        }

        @Override
        public void handlePOST(CoapExchange exchange) {
            String msg = exchange.getRequestText();
            System.out.println(msg);
            exchange.accept();
        }
    }
}
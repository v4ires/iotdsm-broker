import java.net.SocketException;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class BrokerMain {

    public static void main(String[] args) throws SocketException {
        //Run all services
        //new HTTPService().run(args);
        //new MQQTService().run(args);
        new CoAPService().run(args);
    }
}

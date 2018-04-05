/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class Main {

    public static void main(String[] args) {
        //Run all services
        new HTTPService().run(args);
        new MQQTService().run(args);
        new CoAPService().run(args);
    }
}

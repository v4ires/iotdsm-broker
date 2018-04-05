import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class MQQTService {

    public static void run(String[] args) {

        Vertx vertx = Vertx.vertx();
        String host = "localhost";
        int port = 1883;

        MqttServerOptions options = new MqttServerOptions()
                .setPort(port)
                .setHost(host);

        MqttServer server = MqttServer.create(vertx, options);

        server.endpointHandler(endpoint -> {
            endpoint.publishHandler(message -> {
                //String msg = "Just received message on [" + message.topicName() + "] payload [" + message.payload() + "] with QoS [" + message.qosLevel() + "]";
                System.out.println("MQQT Service: OK");
                //client.post(port, host, url);
            });
            endpoint.accept(false);
        });
        server.listen(ar -> {
            if (ar.succeeded()) {
                System.out.println("MQTT Service Started (" + server.actualPort() + ")");
            } else {
                System.err.println("MQTT Service error on start" + ar.cause().getMessage());
            }
        });
    }

    public static void main(String[] args) {
        run(args);
    }
}

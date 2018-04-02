        import io.vertx.core.Vertx;
        import io.vertx.core.http.HttpClient;

        import io.vertx.core.AbstractVerticle;
        import io.vertx.mqtt.MqttServer;
        import io.vertx.mqtt.MqttServerOptions;

        /*import org.eclipse.paho.client.mqttv3.MqttClient;
        import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;
        import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
        */

        public class Main {

            public static void main(String[] args) {

                Vertx vertx = Vertx.vertx();
                HttpClient client = vertx.createHttpClient();
                int delay = 5000;
                String host = "localhost";
                String url = "/";
                int port = 80;
                /*
                long timerjson = vertx.setPeriodic(delay, id -> {
                    client.getNow(port, host, url, response -> {
                        int responseCode = response.statusCode();
                        if (responseCode == 200) {
                            response.bodyHandler(bufferResponse -> {
                                System.out.println(bufferResponse.toString());
                                //client.post(port, host, url);

                            });
                        }
                    });
                });*/

                MqttServerOptions options = new MqttServerOptions()
                            .setPort(8080)
                            .setHost("localhost");

                MqttServer server = MqttServer.create(vertx, options);

                server.endpointHandler(endpoint -> {
                    System.out.println("connected client " + endpoint.clientIdentifier());
                        endpoint.publishHandler(message -> {
                            System.out.println("Just received message on [" + message.topicName() + "] payload [" +
                                    message.payload() + "] with QoS [" +
                                    message.qosLevel() + "]");
                        });
                        endpoint.accept(false);
                    });
                server.listen(ar -> {
                    if (ar.succeeded()) {
                        System.out.println("MQTT server started and listening on port " + server.actualPort());
                    } else {
                        System.err.println("MQTT server error on start" + ar.cause().getMessage());
                    }
                });
            }
        }

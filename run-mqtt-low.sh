echo "running 25 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 750 1 mqtt_25.log

echo "running 50 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 1500 1 mqtt_50.log

echo "running 75 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 2250 1 mqtt_75.log

echo "running 100 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 3000 1 mqtt_100.log

echo "running 125 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 3750 1 mqtt_125.log


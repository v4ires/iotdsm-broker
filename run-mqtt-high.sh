echo "running 650 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 19500 1 mqtt_650.log

echo "running 750 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 22500 1 mqtt_750.log

echo "running 850 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 25500 1 mqtt_850.log

echo "running 950 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 28500 1 mqtt_950.log

echo "running 1050 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 31500 1 mqtt_1050.log

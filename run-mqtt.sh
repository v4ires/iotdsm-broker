echo "running 300 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 300 1 mqtt1.log

echo "running 600 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 600 1 mqtt2.log

echo "running 900 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 900 1 mqtt3.log

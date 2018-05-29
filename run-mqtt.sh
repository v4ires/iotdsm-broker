echo "running 250 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 300 1 mqtt1.log

echo "running 500 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 600 1 mqtt2.log

echo "running 1000 threads mqtt"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.MQTTClient 900 1 mqtt3.log

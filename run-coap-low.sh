echo "running 25 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 750 1 coap_25.log

echo "running 50 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 1500 1 coap_50.log

echo "running 75 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 2250 1 coap_75.log

echo "running 100 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 3000 1 coap_100.log

echo "running 125 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 3750 1 coap_125.log


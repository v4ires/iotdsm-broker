echo "running 250 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 7500 1 coap1.log

echo "running 500 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 15000 1 coap2.log

echo "running 1000 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 30000 1 coap3.log
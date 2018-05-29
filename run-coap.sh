echo "running 300 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 300 1 coap1.log

echo "running 600 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 600 1 coap2.log

echo "running 900 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 900 1 coap3.log

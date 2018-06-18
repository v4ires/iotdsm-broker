echo "running 650 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 19500 1 coap_150.log

echo "running 750 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 22500 1 coap_250.log

echo "running 850 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 25500 1 coap_350.log

echo "running 950 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 28500 1 coap_450.log

echo "running 1050 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 31500 1 coap_550.log
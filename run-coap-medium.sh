echo "running 150 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 4500 1 coap_150.log

echo "running 250 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 7500 1 coap_250.log

echo "running 350 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 10500 1 coap_350.log

echo "running 450 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 13500 1 coap_450.log

echo "running 550 threads coap"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.CoAPClient 16500 1 coap_550.log

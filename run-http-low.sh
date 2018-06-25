echo "running 25 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 750 1 http_25.log

echo "running 50 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 1500 1 http_50.log

echo "running 75 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 2250 1 http_75.log

echo "running 100 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 3000 1 http_100.log

echo "running 125 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 3750 1 http_125.log

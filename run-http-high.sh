echo "running 650 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 19500 1 http_150.log

echo "running 750 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 22500 1 http_250.log

echo "running 850 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 25500 1 http_350.log

echo "running 950 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 28500 1 http_450.log

echo "running 1050 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 31500 1 http_550.log
echo "running 250 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 7500 1 http1.log

echo "running 500 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 15000 1 http2.log

echo "running 1000 threads http"
java -cp iotdsm-broker-1.0-SNAPSHOT-all.jar edu.usp.icmc.lasdpc.client.HTTPClient 30000 1 http3.log
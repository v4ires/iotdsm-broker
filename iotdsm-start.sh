#!/bin/bash

comm_protocol="$1"

start_iotdsm(){

    if [ ! -f "build/libs/iotdsm-broker-1.0-SNAPSHOT-all.jar" ]; then
        bash -c "gradle build shadowJar"
    fi

    properties_file=""
    if [ "$comm_protocol" = "http" ]; then
        properties_file="http.properties"
    elif [ "$comm_protocol" = "mqtt" ]; then
        properties_file="mqtt.properties"
    elif [ "$comm_protocol" = "coap" ]; then
        properties_file="coap.properties"
    fi
    
    echo "init iotdsm-b application..."
    echo "java -jar build/libs/iotdsm-broker-1.0-SNAPSHOT-all.jar -c=$PWD/$properties_file"
    java -jar build/libs/iotdsm-broker-1.0-SNAPSHOT-all.jar -c="$PWD/$properties_file"
}

start_iotdsm
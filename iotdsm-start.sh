#!/bin/bash

comm_protocol=""

start_iotdsm(){

    if [ ! -f "build/libs/iotdsm-broker-1.0.0-all.jar" ]; then
        bash -c "gradle build shadowJar"
    fi

    properties_file=""
    if [ "$comm_protocol" = "http" ]; then
        properties_file="config.properties"
    elif [ "$comm_protocol" = "mqtt" ]; then
        properties_file="config.properties"
    elif [ "$comm_protocol" = "coap" ]; then
        properties_file="config.properties"
    fi
    
    echo "init iotdsm-b application..."
    bash -c "java -jar build/libs/iotdsm-broker-1.0.0-all.jar -c=$properties_file"
}

start_iotdsm

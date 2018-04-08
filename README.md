# **I**nternet **o**f **T**hings **D**ata as a **S**ervice **M**odule Broker (IoTDSM-B)

<p align="center"><img src="assets/logo/iot-dsm-logo.png"/></p>

## TL;DR

The IoTDSM is a project developed in the Laboratory of Distributed Systems and Concurrent Programming (LaSDPC) of the University of São Paulo (USP). This project aims to simplify the acquisition and storage of sensor data in the Internet of Things (IoT) context. For this, IoTDSM supports different storage strategies in SQL and NoSQL databases. Also, this tool provides a RESTful API for communication and data management of sensor networks.

## Getting Started

The IoTDSM aims to provide data from objects belonging to one or more intelligent environments in the context of IoT. This tool provides a simplified interface for connecting to different sensor network data sources, providing support for different types of databases, whether SQL or NoSQL.

### Installing

To install IoTDSM-B, merely compile the project with the command:

```bash
#Compile the project with all dependencies disregarding the execution of tests
~$ gradle build shadownJar
```

This command will generate a .jar file with all the dependencies required to run the project.
The generated .jar file is located in the directory:

```bash
#File: iotdsm-broker-all-1.0.0.jar
~$ build/libs
```

To run the IoTDSM-B, just run the command:

```bash
#Executes IoTDSM-B
~$ java -jar iotdsm-broker-all-1.0.0.jar <args>
```

If you only need to download the dependencies, execute the command:

```bash
~$ gradle build --refresh-dependencies
```

## Running Tests

To run the unit tests just run the command:

```bash
#Perform Unit Tests
~$ gradle test
```

## Running and Deployment

The IoTDSM provides an input interface for different configuration parameters.
These parameters are passed through system-defined *flags*.
To view the available parameters, execute the command:

```bash
#Shows the options parameters available
~$ java -jar iotdsm-broker-all-1.0.0.jar -help
```

The available parameters are as follows:

```bash
 -c,--configuration <arg>   Path to configuration file [config.properties].
 -h,--help                  Show help [true, false].
 -l,--log <arg>             Enable or disable log [true, false].
 -lf,--logfile <arg>        Log4J Configuration File [log4j.properties].
 -v,--log-level <arg>       Changes the log level [OFF, TRACE, DEBUG, WARN, ERROR, FATAL, ALL].
```
In addition to the default parameters other settings can be defined by means of a configuration file.
This file specifies the IoTDSM Database and Web Server configuration.
The table of available arguments is shown below.

**Table 1**: Web Server input variables.

<center>

|          Variable         |                   Description                |
|:-------------------------:|:--------------------------------------------:|
| BROKER_PROTOCOL           | Protocol Type (HTTP, MQQT or COAP)           |
| BROKER_HOST               | Broker Host Address                          |
| BROKER_PORT               | Broker Port Address                          |

</center>

These variables are passed through a configuration file (config.properties) through the command **-c= ${config_file}**.

```bash
~$ java -jar iotdsm-broker-all-1.0.0.jar -c=${config_file}
```

## Docker Images

This project provides docker images for use in production. The following is the official docker file available.
To compile the images in Docker from IoTDSM just run the command:

```bash
#To Compile Image (IoTDSM-B)
~$ docker build -f iotdsm-b.dockerfile -t iotdsm-b .
```

## Built With

* [Gradle](https://gradle.org/) - Dependency Manager.

## Documentation

The IoTDSM-B provides a wiki about this project, visit the [WIKI](https://github.com/v4ires/iotdsm-edu.usp.icmc.lasdpc.iotdsm.services/wiki) page.

## Contributing

Please read the [CONTRIBUTING.md](CONTRIBUTING.md) file for more details on how to contribute to this project.

## Authors

* **Vinicius Aires Barros** - *Initial Project Initializer* - [@v4ires](https://github.com/v4ires)
* **Sérgio Baptista**  - *Collaborator* - [@Pinobex](https://github.com/Pinobex)

## License

This project is licensed under the MIT license - see the   [LICENSE](LICENSE) file for more details.

## Acknowledgments

* University of São Paulo (USP)
* Institute of Mathematical and Computer Sciences (ICMC)
* Laboratory of Distributed Systems and Concurrent Programming (LaSDPC)
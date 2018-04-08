package edu.usp.icmc.lasdpc;

import edu.usp.icmc.lasdpc.services.CoAPService;
import edu.usp.icmc.lasdpc.services.HTTPService;
import edu.usp.icmc.lasdpc.services.MQQTService;
import edu.usp.icmc.lasdpc.util.PropertiesReader;
import org.apache.commons.cli.*;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * University of Sao Paulo
 * IoT Repository Module
 *
 * @author Vinicius A. Barros
 * @author Sergio Baptista
 */
public class BrokerMain {

    private static String _log4jFile = "log4j.properties";
    private static String _logLevel = "ALL";
    private static String _configFileName = "mqtt.properties";
    private static Options options = new Options();
    private static final Logger log = LoggerFactory.getLogger(BrokerMain.class);

    public static void main(String[] args) throws SocketException {
        initOptions(args);
        initProperties();
        initBroker();
    }

    private static void initBroker() throws SocketException {
        String brokerProtocol = PropertiesReader.getValue("BROKER_PROTOCOL");
        switch (brokerProtocol) {
            case "HTTP":
                new HTTPService().run();
                break;
            case "MQTT":
                new MQQTService().run();
                break;
            case "COAP":
                new CoAPService().run();
                break;
            case "ALL":
                new HTTPService().run();
                new MQQTService().run();
                new CoAPService().run();
                break;
            default:
                log.info("Invalid or unspecified communication protocol.");
        }
    }

    /**
     * Inicializa as configurações de sistema do IoTDSM.
     *
     * @param args Argumento de configurações passados pelo método ImportData
     */
    private static void initOptions(String[] args) {
        options.addOption("c", "configuration", true, "Path to the configuration file [config.properties].");
        options.addOption("h", "help", false, "Show help.");
        options.addOption("v", "version", false, "Show system version.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
            checkCMDOptions(cmd);
        } catch (ParseException e) {
            showHelp();
        }
    }

    /**
     * Mostra as opções de argumentos do sistema.
     */
    private static void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("IoTDSM Broker", options);
        System.exit(0);
    }

    /**
     * Método que recebe um cmd como parâmetro e executa os argumentos passados pelo usuário.
     *
     * @param cmd
     */
    private static void checkCMDOptions(CommandLine cmd) {
        if (cmd.hasOption("c")) {
            _configFileName = cmd.getOptionValue("c");
        } else if (cmd.hasOption("h")) {
            showHelp();
        } else if (cmd.hasOption("v")) {
            System.out.println("IoTDSM Broker v1.0.0");
        }
    }

    /**
     * Método que habilita a utilização do Log4J.
     */
    private static void enableLog4J(String logLevel) {
        LogManager.getRootLogger().setLevel(Level.toLevel(_logLevel));
        Properties properties = PropertiesReader.initialize(_log4jFile);
        LogManager.getRootLogger().setLevel(Level.toLevel(logLevel));
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(properties);
    }

    /**
     * Configura as propriedades do Servidor de Aplicação.
     */
    private static void initProperties() {
        Path path = Paths.get(_configFileName);
        enableLog4J(_logLevel);
        if (Files.exists(path)) {
            PropertiesReader.initialize(_configFileName);
            log.info("--------------------------");
            log.info("Config Properties File");
            log.info("BROKER_PROTOCOL: {}", PropertiesReader.getValue("BROKER_PROTOCOL"));
            log.info("BROKER_HOST: {}", PropertiesReader.getValue("BROKER_HOST"));
            log.info("BROKER_PORT: {}", PropertiesReader.getValue("BROKER_PORT"));
            log.info("--------------------------");
        } else {
            log.error("Configuration file not found in path \"{}\".", path);
        }
    }
}

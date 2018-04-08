package edu.usp.icmc.lasdpc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger log = LoggerFactory.getLogger(PropertiesReader.class);

    private static Properties properties;

    public static Properties initialize(String fileName) {
        properties = new Properties();
        try {
            InputStream in = new FileInputStream(fileName);
            properties.load(in);
            in.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return properties;
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
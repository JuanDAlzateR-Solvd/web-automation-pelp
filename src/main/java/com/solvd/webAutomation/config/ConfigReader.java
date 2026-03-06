package com.solvd.webAutomation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        String configFileName = "_config.properties";
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new RuntimeException("Config file '" + configFileName + "' not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file '" + configFileName + "'", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required configuration property: " + key);
        }
        return value;
    }
}

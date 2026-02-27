package com.solvd.webAutomation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("_config.properties")) {

            if (input == null) {
                throw new IllegalStateException("Config file _config.properties not found on classpath");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file _config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

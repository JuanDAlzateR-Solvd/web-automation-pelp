package com.solvd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    public static void main(String[] args) {
        final Logger LOGGER =LogManager.getLogger(App.class);
        LOGGER.info("Hello World!");
    }
}

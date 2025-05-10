package com.automation.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());
    private static final String CONFIG_FILE_NAME = "application.properties";
    private static final String DEFAULT_ENV = "dev";
    private static final String ENV_PROPERTY_KEY = "env";
    private static final String CONFIG_FILE_FORMAT = "application-%s.properties";
    private static ConfigManager instance;
    private final Map<String, String> configCache = new HashMap<>();

    private ConfigManager() {
        String environment = System.getenv(ENV_PROPERTY_KEY);
        if (environment == null || environment.isEmpty()) {
            environment = System.getProperty(ENV_PROPERTY_KEY, DEFAULT_ENV);
        }
        LOGGER.info("Loading configuration for environment: " + environment);
        loadProperties(CONFIG_FILE_NAME, "DEFAULT");
        loadProperties(String.format(CONFIG_FILE_FORMAT, environment), environment.toUpperCase());
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties(String fileName, String source) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                LOGGER.log(Level.WARNING, "Config file not found: {0} ({1})", new Object[]{fileName, source});
                return;
            }
            Properties properties = new Properties();
            properties.load(input);
            for (String key : properties.stringPropertyNames()) {
                configCache.putIfAbsent(key, properties.getProperty(key));
                LOGGER.log(Level.INFO, "Loaded property: {0} from {1} ({2})", new Object[]{key, fileName, source});
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading properties from {0} ({1})", new Object[]{fileName, source});
            throw new RuntimeException("Error loading properties from " + fileName, e);
        }
    }

    public String get(String key) {
        String value = System.getenv(key);
        if (value != null) return value;
        value = System.getProperty(key);
        if (value != null) return value;
        return configCache.getOrDefault(key, "");
    }

    public void clearCache() {
        configCache.clear();
        LOGGER.info("Configuration cache cleared.");
    }
}

package com.automation.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());
    private static final String CONFIG_FILE_NAME = "application.properties";
    private static final String CORE_CONFIG_PATH = Objects.requireNonNull(ConfigManager.class.getClassLoader().getResource(CONFIG_FILE_NAME)).getPath();
    private static final String CHILD_CONFIG_PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", CONFIG_FILE_NAME).toString();
    private static ConfigManager instance;
    private final Map<String, String> configCache = new HashMap<>();

    private ConfigManager() {
        loadProperties(CHILD_CONFIG_PATH, "CHILD_CONFIG_PATH");
        loadProperties(CORE_CONFIG_PATH, "CORE_CONFIG_PATH");
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties(String filePath, String source) {
        File file = new File(filePath);
        if (!file.exists()) {
            LOGGER.log(Level.WARNING, "Config file not found: {0} ({1})", new Object[]{filePath, source});
            return;
        }
        try (InputStream input = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(input);
            for (String key : properties.stringPropertyNames()) {
                configCache.putIfAbsent(key, properties.getProperty(key));
                LOGGER.log(Level.INFO, "Loaded property: {0} from {1} ({2})", new Object[]{key, filePath, source});
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading properties from {0} ({1})", new Object[]{filePath, source});
            throw new RuntimeException("Error loading properties from " + filePath, e);
        }
    }

    public String get(String key) {
        String value = System.getenv(key);
        if (value != null) return value;
        value = System.getProperty(key);
        if (value != null) return value;
        value = configCache.get(key);
        if (value != null) return value;
        return configCache.getOrDefault(key, "");
    }

    public void clearCache() {
        configCache.clear();
        LOGGER.info("Configuration cache cleared.");
    }
}

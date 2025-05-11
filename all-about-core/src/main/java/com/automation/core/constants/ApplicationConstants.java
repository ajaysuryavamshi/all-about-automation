package com.automation.core.constants;

import java.nio.file.Paths;

public class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static final String CONFIG_FILE_PATH = Paths.get("src", "test", "resources", "config.properties").toString();
}
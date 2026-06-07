package com.orangehrm.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Singleton utility to load and expose config.properties values.
 * Supports system property overrides for CI/CD pipeline usage.
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    private ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            log.info("Configuration loaded from: {}", CONFIG_PATH);
        } catch (IOException e) {
            log.error("Failed to load config.properties from path: {}", CONFIG_PATH, e);
            throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
        }
    }

    /** Returns singleton instance (thread-safe lazy init). */
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    /**
     * Get a property value. System property overrides config file (useful for CI).
     */
    public String getProperty(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp.trim();
        }
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in config.properties", key);
            return "";
        }
        return value.trim();
    }

    // ─── Typed convenience methods ───────────────────────────────────────────

    public String getBaseUrl()         { return getProperty("base.url"); }
    public String getAdminUsername()   { return getProperty("admin.username"); }
    public String getAdminPassword()   { return getProperty("admin.password"); }
    public String getBrowser()         { return getProperty("browser"); }
    public boolean isHeadless()        { return Boolean.parseBoolean(getProperty("headless")); }
    public int getImplicitWait()       { return Integer.parseInt(getProperty("implicit.wait")); }
    public int getExplicitWait()       { return Integer.parseInt(getProperty("explicit.wait")); }
    public int getPageLoadTimeout()    { return Integer.parseInt(getProperty("page.load.timeout")); }
    public boolean screenshotOnFail()  { return Boolean.parseBoolean(getProperty("screenshot.on.failure")); }
    public String getReportsPath()     { return getProperty("reports.path"); }
    public String getScreenshotPath()  { return getProperty("screenshot.path"); }
    public String getTestDataPath()    { return getProperty("testdata.path"); }
    public int getRetryCount()         { return Integer.parseInt(getProperty("retry.count")); }
}

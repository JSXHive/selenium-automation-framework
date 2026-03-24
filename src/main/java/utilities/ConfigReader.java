package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
    private Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);

    public ConfigReader() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            properties.load(fis);
            fis.close();
            
            // Debug: Print all loaded properties
            logger.info("=== CONFIG PROPERTIES LOADED ===");
            properties.forEach((key, value) -> 
                logger.info(key + " = " + value));
            
            // Override with system properties if provided
            String browser = System.getProperty("browser");
            if (browser != null && !browser.isEmpty()) {
                properties.setProperty("browser", browser);
                logger.info("Overriding browser from system property: " + browser);
            }
            
            String headless = System.getProperty("headless");
            if (headless != null && !headless.isEmpty()) {
                properties.setProperty("headless", headless);
                logger.info("Overriding headless from system property: " + headless);
            }
            
        } catch (IOException e) {
            logger.error("Failed to load config.properties from: " + CONFIG_FILE);
            logger.error("Current working directory: " + System.getProperty("user.dir"));
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl", "https://www.saucedemo.com");
    }

    public String getBrowser() {
        String browser = properties.getProperty("browser");
        if (browser == null || browser.trim().isEmpty()) {
            logger.error("Browser property is NULL or EMPTY! Check config.properties");
            return "chrome"; // Default fallback
        }
        return browser.trim().toLowerCase();
    }

    /**
 * Get website URL by key (for multi-website testing)
 * @param websiteKey The website key (e.g., "website1", "website2")
 * @return The URL for the specified website
 */
public String getWebsiteUrl(String websiteKey) {
    String url = properties.getProperty(websiteKey + ".url");
    if (url == null || url.isEmpty()) {
        logger.warn("Website key '{}' not found in config. Using default baseUrl: {}", 
                   websiteKey, getBaseUrl());
        return getBaseUrl();
    }
    return url;
}

    public boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicitWait", "30"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("pageLoadTimeout", "30"));
    }

    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(properties.getProperty("screenshotOnFailure", "true"));
    }

    public String getScreenshotPath() {
        return properties.getProperty("screenshotPath", "./screenshots/");
    }

    public String getReportPath() {
        return properties.getProperty("reportPath", "./reports/");
    }

    public String getReportName() {
        return properties.getProperty("reportName", "TestAutomationReport");
    }

    public String getEnvironment() {
        return properties.getProperty("environment", "qa");
    }
}
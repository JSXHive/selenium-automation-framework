package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    
    public static WebDriver getDriver(String browser, boolean headless) {
        logger.info("Attempting to create driver for browser: '" + browser + "'");
        
        if (browser == null || browser.trim().isEmpty()) {
            logger.error("Browser value is null or empty! Using default: chrome");
            browser = "chrome";
        }
        
        browser = browser.trim().toLowerCase();
        WebDriver driver;
        
        try {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--window-size=1920,1080");
                    }
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(chromeOptions);
                    logger.info("Chrome driver initialized successfully");
                    break;
                    
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                        firefoxOptions.addArguments("--window-size=1920,1080");
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    logger.info("Firefox driver initialized successfully");
                    break;
                    
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless=new");
                        edgeOptions.addArguments("--window-size=1920,1080");
                    }
                    driver = new EdgeDriver(edgeOptions);
                    logger.info("Edge driver initialized successfully");
                    break;
                    
                default:
                    logger.error("Unsupported browser: " + browser + ". Using chrome as fallback");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
            }
        } catch (Exception e) {
            logger.error("Failed to create driver for browser: " + browser);
            logger.error("Exception: " + e.getMessage());
            throw e;
        }
        
        return driver;
    }
}
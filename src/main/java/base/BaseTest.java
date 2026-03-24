package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.ConfigReader;
import utilities.DriverFactory;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtil;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected ConfigReader configReader;
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    protected static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUpSuite() {
        extent = ExtentReportManager.getInstance();
        logger.info("ExtentReports initialized");
    }

    @BeforeMethod
@Parameters({"browser", "headless", "website"})
public void setUp(Method method, @Optional String browser, 
                  @Optional String headless, @Optional String website) {
    try {
        configReader = new ConfigReader();
        
        // ===== BROWSER CONFIGURATION =====
        // Get browser from parameters or config
        String browserType = browser;
        if (browserType == null || browserType.isEmpty()) {
            browserType = configReader.getBrowser();
            logger.info("Using browser from config.properties: " + browserType);
        } else {
            logger.info("Using browser from testng.xml parameter: " + browserType);
        }
        
        // ===== HEADLESS CONFIGURATION =====
        // Get headless mode
        boolean isHeadless = false;
        if (headless != null && !headless.isEmpty()) {
            isHeadless = Boolean.parseBoolean(headless);
            logger.info("Using headless from parameter: " + isHeadless);
        } else {
            isHeadless = configReader.isHeadless();
            logger.info("Using headless from config: " + isHeadless);
        }
        
        // ===== WEBSITE CONFIGURATION =====
        // Get website URL (new multi-website support)
        String baseUrl;
        if (website != null && !website.isEmpty()) {
            // Website parameter provided (e.g., website1, website2, etc.)
            baseUrl = configReader.getWebsiteUrl(website);
            logger.info("Testing website: {} at URL: {}", website, baseUrl);
        } else {
            // Use default baseUrl from config
            baseUrl = configReader.getBaseUrl();
            logger.info("Using default website from config: " + baseUrl);
        }
        
        logger.info("Final configuration - Browser: {}, Headless: {}, URL: {}", 
                   browserType, isHeadless, baseUrl);
        
        // ===== DRIVER INITIALIZATION =====
        // Initialize driver with browser and headless settings
        driver = DriverFactory.getDriver(browserType, isHeadless);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(configReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(configReader.getPageLoadTimeout()));
        
        driverThreadLocal.set(driver);
        
        // ===== NAVIGATE TO WEBSITE =====
        // Navigate to the selected website
        driver.get(baseUrl);
        logger.info("Successfully navigated to: {}", baseUrl);
        
        // ===== EXTENT REPORT =====
        // Create test in ExtentReports with website info
        String testName = method.getName();
        if (website != null && !website.isEmpty()) {
            testName = testName + " [" + website + "]";
        }
        ExtentTest test = extent.createTest(testName);
        test.assignCategory(website != null ? website : "default");
        extentTest.set(test);
        
    } catch (Exception e) {
        logger.error("Failed to initialize WebDriver: " + e.getMessage());
        logger.error("Stack trace: ", e);
        throw new RuntimeException("Failed to initialize WebDriver", e);
    }
}

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE && configReader.isScreenshotOnFailure()) {
                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
                extentTest.get().fail("Test Failed").addScreenCaptureFromPath(screenshotPath);
                logger.error("Test failed: {}", result.getName());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                extentTest.get().pass("Test passed");
                logger.info("Test passed: {}", result.getName());
            } else if (result.getStatus() == ITestResult.SKIP) {
                extentTest.get().skip("Test skipped");
                logger.warn("Test skipped: {}", result.getName());
            }
        } catch (Exception e) {
            logger.error("Error in tearDown: {}", e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                driverThreadLocal.remove();
                logger.info("WebDriver closed");
            }
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed");
        }
    }

    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    protected ExtentTest getExtentTest() {
        return extentTest.get();
    }
}
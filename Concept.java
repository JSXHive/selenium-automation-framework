// This file contains detailed notes on the functionality, features, and execution of 
//  the Selenium automation framework.



/*
===============================================================================
Selenium Automation Framework – Functionality, Features & Execution Notes
===============================================================================

These notes explain the core functionality of the Selenium automation framework,
its features, and how to execute tests using Maven commands.

-------------------------------------------------------------------------------
1. CORE FUNCTIONALITY
-------------------------------------------------------------------------------

The framework is config-driven and parameterized. This means tests can run on
different websites, browsers, environments, and execution modes without
changing the code.

Supported capabilities:

- Reads configuration from config.properties
- Allows overriding values using Maven command-line parameters
- Supports multi-website testing
- Supports cross-browser execution
- Supports parallel execution
- Generates logs, screenshots, and reports automatically


-------------------------------------------------------------------------------
2. DEFAULT EXECUTION
-------------------------------------------------------------------------------

Command:

    mvn clean test

Behavior:

- Reads browser=chrome from config.properties
- Reads baseUrl=https://www.saucedemo.com
- Launches one Chrome browser
- Executes all test classes
- Generates ExtentReport
- Logs saved in /logs directory

Result:

- Tests run on SauceDemo website
- Execution mode (parallel or sequential) depends on testng.xml


-------------------------------------------------------------------------------
3. RUN TESTS ON ANOTHER WEBSITE
-------------------------------------------------------------------------------

Command:

    mvn test -Dwebsite=website2

Behavior:

- Overrides default website configuration
- Uses:

    website2.url = https://automationexercise.com

- Browser remains Chrome (default from config)
- Tests execute on AutomationExercise website

Result:

Same test cases run on another website without modifying the code.


-------------------------------------------------------------------------------
4. CROSS-BROWSER TESTING
-------------------------------------------------------------------------------

Command:

    mvn test -Dwebsite=website3 -Dbrowser=firefox

Behavior:

- Uses website3 URL
- Browser overridden to Firefox
- Firefox browser launches
- Full test suite runs

Result:

Tests run on a different browser and website.


-------------------------------------------------------------------------------
5. PARALLEL MULTI-WEBSITE TESTING
-------------------------------------------------------------------------------

Command:

    mvn test -DsuiteXmlFile=WebsiteTest.xml

Behavior Example:

    Browser 1 → website1 → saucedemo.com
    Browser 2 → website2 → automationexercise.com
    Browser 3 → website3 → shop.demoqa.com
    Browser 4 → website4 → magento.com

All tests run simultaneously.

Result:

Multiple websites tested in parallel.

Time comparison example:

    Sequential Execution : ~30 minutes
    Parallel Execution   : ~7 minutes


-------------------------------------------------------------------------------
6. TEST EXECUTION FLOW
-------------------------------------------------------------------------------

Each browser instance performs the following steps:

1. Launch browser
2. Open configured website
3. Execute test classes:

   - LoginTest
   - PurchaseFlowTest
   - DataDrivenLoginTest

4. Capture screenshots if failures occur
5. Log execution details
6. Record results in ExtentReport


-------------------------------------------------------------------------------
7. CONSOLE OUTPUT EXAMPLE
-------------------------------------------------------------------------------

Example console output during execution:

    CONFIG PROPERTIES LOADED

    Testing website1 → saucedemo.com
    Testing website2 → automationexercise.com
    Testing website3 → shop.demoqa.com
    Testing website4 → magento.com

Each thread executes tests independently.


-------------------------------------------------------------------------------
8. REPORTING
-------------------------------------------------------------------------------

ExtentReport includes:

- Test name
- Website tested
- Browser used
- Pass / Fail status
- Screenshot on failure
- Execution time

Example report output:

    Website 1: saucedemo.com
        ✓ LoginTest – PASS
        ✓ PurchaseFlowTest – PASS

    Website 2: automationexercise.com
        ✓ LoginTest – PASS
        ✗ PurchaseFlowTest – FAIL

Report location:

    /reports/ExtentReport.html


-------------------------------------------------------------------------------
9. LOGGING
-------------------------------------------------------------------------------

Logs are stored in:

    /logs/automation.log

Example log entries:

    [Thread-1] Login started on website1
    [Thread-2] Login started on website2
    [Thread-3] Login successful

Thread identifiers help track parallel execution.


-------------------------------------------------------------------------------
10. SCREENSHOT CAPTURE
-------------------------------------------------------------------------------

If a test fails:

- Screenshot automatically captured
- Saved inside:

    /screenshots

Example file:

    LoginTest_website2_20240101.png

Screenshot is also attached in the report.


-------------------------------------------------------------------------------
11. RETRY MECHANISM
-------------------------------------------------------------------------------

If a test fails:

- RetryAnalyzer automatically retries the test

This helps handle failures caused by:

- network delays
- page loading issues
- unstable elements (flaky tests)


-------------------------------------------------------------------------------
12. KEY FRAMEWORK FEATURES
-------------------------------------------------------------------------------

Feature                     Description
-------------------------------------------------------------------
Config Driven               No hardcoded URLs or browsers
Cross Browser               Chrome, Firefox, Edge
Parallel Execution          Multiple tests run simultaneously
Multi-Website Testing       Same tests run on multiple websites
Data Driven Testing         Excel / JSON test data
Logging                     Log4j logging framework
Reporting                   ExtentReports HTML reports
Screenshot Capture          Screenshots on test failure
Retry Mechanism             Automatic retry of failed tests
CI Friendly                 Maven CLI parameter support


-------------------------------------------------------------------------------
13. COMMANDS SUMMARY
-------------------------------------------------------------------------------

Run default tests:

    mvn clean test


Run tests on specific website:

    mvn test -Dwebsite=website2


Run tests on different browser:

    mvn test -Dbrowser=firefox


Run tests on different browser and website:

    mvn test -Dwebsite=website3 -Dbrowser=edge


Run parallel multi-website suite:

    mvn test -DsuiteXmlFile=WebsiteTest.xml


-------------------------------------------------------------------------------
14. REAL ENTERPRISE USE CASE
-------------------------------------------------------------------------------

Scenario:

Validate login and checkout functionality on 4 e-commerce websites
across 3 browsers.

Execution:

    4 websites × 3 browsers = 12 parallel executions

All tests complete within minutes instead of hours.


-------------------------------------------------------------------------------
15. SIMPLE CONCEPT
-------------------------------------------------------------------------------

The framework behaves like multiple automated testers working together.

Example:

    Robot 1 → tests website1
    Robot 2 → tests website2
    Robot 3 → tests website3
    Robot 4 → tests website4

All robots report results into one unified report.

-------------------------------------------------------------------------------

This architecture reflects how enterprise-grade Selenium automation
frameworks used by professional SDET teams operate.

===============================================================================
*/
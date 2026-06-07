package com.orangehrm.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.orangehrm.config.ConfigReader;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.DriverManager;
import com.orangehrm.utils.ExtentReportManager;
import com.orangehrm.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * BaseTest - Abstract parent class for all test classes.
 * Manages WebDriver lifecycle, ExtentReports test nodes, and login.
 */
public abstract class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected static final ConfigReader config = ConfigReader.getInstance();
    protected LoginPage loginPage;

    // ─── Suite-level Report Setup ─────────────────────────────────────────────

    @BeforeSuite(alwaysRun = true)
    public void initReportSuite() {
        log.info("========== SUITE STARTED ==========");
        ExtentReportManager.initReports();
    }

    @AfterSuite(alwaysRun = true)
    public void flushReportSuite() {
        ExtentReportManager.flushReports();
        log.info("========== SUITE FINISHED ==========");
    }

    // ─── Test-level Driver & Report Setup ─────────────────────────────────────

    @BeforeMethod(alwaysRun = true)
    public void setUp(java.lang.reflect.Method method) {
        log.info("---------- Test Started: {} ----------", method.getName());
        DriverManager.initDriver();

        // Create ExtentTest node
        String testDescription = method.isAnnotationPresent(Test.class)
            ? method.getAnnotation(Test.class).description()
            : "";
        ExtentReportManager.createTest(method.getName(), testDescription);

        // Navigate to base URL
        DriverManager.getDriver().get(config.getBaseUrl());
        loginPage = new LoginPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        ExtentTest extentTest = ExtentReportManager.getTest();

        switch (result.getStatus()) {
            case ITestResult.SUCCESS -> {
                log.info("Test PASSED: {}", result.getName());
                if (extentTest != null) extentTest.log(Status.PASS, "Test Passed");
            }
            case ITestResult.FAILURE -> {
                log.error("Test FAILED: {} - {}", result.getName(),
                    result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown");
                if (extentTest != null) {
                    if (config.screenshotOnFail()) {
                        String base64 = ScreenshotUtil.captureScreenshotAsBase64();
                        extentTest.addScreenCaptureFromBase64String(base64, "Failure Screenshot");
                        ScreenshotUtil.captureScreenshot(result.getName());
                    }
                    extentTest.log(Status.FAIL, result.getThrowable());
                }
            }
            case ITestResult.SKIP -> {
                log.warn("Test SKIPPED: {}", result.getName());
                if (extentTest != null) extentTest.log(Status.SKIP, "Test Skipped");
            }
        }

        DriverManager.quitDriver();
        ExtentReportManager.removeTest();
        log.info("---------- Test Finished: {} ----------", result.getName());
    }

    // ─── Shared Helper ────────────────────────────────────────────────────────

    /**
     * Convenience: login with default admin credentials and return DashboardPage.
     */
    protected com.orangehrm.pages.DashboardPage loginAsAdmin() {
        return loginPage.loginAs(config.getAdminUsername(), config.getAdminPassword());
    }

    /**
     * Log an info step to ExtentReports.
     */
    protected void logStep(String message) {
        log.info(message);
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) test.log(Status.INFO, message);
    }
}

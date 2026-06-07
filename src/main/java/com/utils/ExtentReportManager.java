package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.orangehrm.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager - Singleton manager for ExtentReports HTML reporting.
 * Generates a timestamped HTML report in the reports/ directory.
 */
public class ExtentReportManager {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static final ConfigReader config = ConfigReader.getInstance();

    private ExtentReportManager() {}

    /** Initialize ExtentReports (called once in the Suite listener). */
    public static synchronized ExtentReports initReports() {
        if (extentReports == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = config.getReportsPath() + "OrangeHRM_Report_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");
            sparkReporter.config().setReportName("OrangeHRM Web Portal Test Execution Report");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Project", "OrangeHRM-Web-Portal-Selenium-Automation-Framework");
            extentReports.setSystemInfo("Application URL", config.getBaseUrl());
            extentReports.setSystemInfo("Browser", config.getBrowser());
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("Author", "Soumyaranjan Sahoo");

            log.info("ExtentReports initialized at: {}", reportPath);
        }
        return extentReports;
    }

    /** Create a test node for the current thread. */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = extentReports.createTest(testName, description);
        testThread.set(test);
        return test;
    }

    /** Create a test node without description. */
    public static ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }

    /** Get the ExtentTest for the current thread. */
    public static ExtentTest getTest() {
        return testThread.get();
    }

    /** Flush all reports to disk (called once in suite finish). */
    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
            log.info("ExtentReports flushed successfully.");
        }
    }

    /** Remove the test from ThreadLocal. */
    public static void removeTest() {
        testThread.remove();
    }
}

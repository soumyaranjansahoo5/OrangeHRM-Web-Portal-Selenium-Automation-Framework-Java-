package com.orangehrm.listeners;

import com.aventstack.extentreports.Status;
import com.orangehrm.utils.ExtentReportManager;
import com.orangehrm.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListener - TestNG ITestListener for ExtentReports integration.
 * Automatically logs pass/fail/skip status and attaches screenshots on failure.
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info(">>> Test Started: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info(">>> Test PASSED: {}", result.getName());
        var test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test passed successfully");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error(">>> Test FAILED: {} | Reason: {}", result.getName(),
            result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown");

        var test = ExtentReportManager.getTest();
        if (test != null) {
            // Attach screenshot as Base64
            String base64 = ScreenshotUtil.captureScreenshotAsBase64();
            if (!base64.isEmpty()) {
                test.addScreenCaptureFromBase64String(base64, "Failure Screenshot");
            }

            // Save screenshot to file too
            ScreenshotUtil.captureScreenshot(result.getName());

            // Log failure with throwable
            if (result.getThrowable() != null) {
                test.log(Status.FAIL, result.getThrowable());
            } else {
                test.log(Status.FAIL, "Test failed without exception details");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn(">>> Test SKIPPED: {}", result.getName());
        var test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test was skipped");
            if (result.getThrowable() != null) {
                test.log(Status.SKIP, result.getThrowable());
            }
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn(">>> Test FAILED within success percentage: {}", result.getName());
    }
}

package com.orangehrm.utils;

import com.orangehrm.config.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil - Captures screenshots and saves them to the configured path.
 * Used by TestNG listeners on test failure.
 */
public class ScreenshotUtil {

    private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);
    private static final ConfigReader config = ConfigReader.getInstance();

    private ScreenshotUtil() {}

    /**
     * Captures a screenshot and saves it to the screenshots directory.
     *
     * @param testName  Name of the test (used in file name)
     * @return          Absolute path of the saved screenshot, or empty string on failure
     */
    public static String captureScreenshot(String testName) {
        WebDriver driver;
        try {
            driver = DriverManager.getDriver();
        } catch (IllegalStateException e) {
            log.warn("Driver not available for screenshot capture.");
            return "";
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String sanitizedName = testName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
        String fileName = sanitizedName + "_" + timestamp + ".png";

        String screenshotDir = config.getScreenshotPath();
        File destDir = new File(screenshotDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File destFile = new File(destDir, fileName);

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, destFile);
            log.info("Screenshot saved: {}", destFile.getAbsolutePath());
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            log.error("Failed to save screenshot for test '{}': {}", testName, e.getMessage());
            return "";
        }
    }

    /**
     * Captures a screenshot and returns it as a base64 string (for embedding in reports).
     */
    public static String captureScreenshotAsBase64() {
        try {
            WebDriver driver = DriverManager.getDriver();
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            log.warn("Failed to capture base64 screenshot: {}", e.getMessage());
            return "";
        }
    }
}

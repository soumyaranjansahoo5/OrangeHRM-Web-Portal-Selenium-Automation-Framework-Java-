package com.orangehrm.utils;

import com.orangehrm.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

/**
 * WaitUtil - Custom wait helpers using Fluent and Explicit waits.
 */
public class WaitUtil {

    private static final Logger log = LogManager.getLogger(WaitUtil.class);
    private static final ConfigReader config = ConfigReader.getInstance();

    private WaitUtil() {}

    /** Standard explicit wait for element visibility. */
    public static WebElement waitForVisibility(By locator, int timeoutSeconds) {
        WebDriver driver = DriverManager.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Standard explicit wait using default config timeout. */
    public static WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, config.getExplicitWait());
    }

    /** Fluent wait for visibility — polls every 500ms, ignores NoSuchElement. */
    public static WebElement fluentWaitForVisibility(By locator, int timeoutSeconds) {
        WebDriver driver = DriverManager.getDriver();
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(timeoutSeconds))
            .pollingEvery(Duration.ofMillis(500))
            .ignoring(NoSuchElementException.class)
            .ignoring(org.openqa.selenium.StaleElementReferenceException.class);

        return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait for the page spinner / loader to disappear. */
    public static void waitForLoaderToDisappear() {
        WebDriver driver = DriverManager.getDriver();
        By loaderLocator = By.xpath("//div[contains(@class,'oxd-loading-spinner')]");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()))
                .until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
            log.debug("Page loader disappeared");
        } catch (Exception e) {
            log.debug("No loader detected or already gone");
        }
    }

    /** Wait for URL to contain a specific fragment. */
    public static void waitForUrl(String urlFragment, int timeoutSeconds) {
        WebDriver driver = DriverManager.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            .until(ExpectedConditions.urlContains(urlFragment));
        log.debug("URL now contains: {}", urlFragment);
    }

    public static void waitForUrl(String urlFragment) {
        waitForUrl(urlFragment, config.getExplicitWait());
    }

    /** Wait for an element to be clickable. */
    public static WebElement waitForClickability(By locator, int timeoutSeconds) {
        WebDriver driver = DriverManager.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Wait for element to be clickable using default timeout. */
    public static WebElement waitForClickability(By locator) {
        return waitForClickability(locator, config.getExplicitWait());
    }

    /** Wait for element to be invisible. */
    public static boolean waitForInvisibility(By locator, int timeoutSeconds) {
        WebDriver driver = DriverManager.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /** Simple thread sleep wrapper. */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Sleep interrupted: {}", e.getMessage());
        }
    }
}

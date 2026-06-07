package com.orangehrm.pages;

import com.orangehrm.config.ConfigReader;
import com.orangehrm.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * BasePage - Abstract parent class for all Page Objects.
 * Encapsulates all reusable WebDriver interactions with explicit waits.
 */
public abstract class BasePage {

    private static final Logger log = LogManager.getLogger(BasePage.class);
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final ConfigReader config = ConfigReader.getInstance();

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ─── Navigation ──────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        log.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // ─── Wait Utilities ───────────────────────────────────────────────────────

    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitForInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    // ─── Element Actions ──────────────────────────────────────────────────────

    protected void click(WebElement element) {
        waitForClickability(element).click();
        log.debug("Clicked element: {}", element);
    }

    protected void click(By locator) {
        waitForClickability(locator).click();
        log.debug("Clicked locator: {}", locator);
    }

    protected void jsClick(WebElement element) {
        log.debug("JS Click on element: {}", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(WebElement element, String text) {
        WebElement el = waitForVisibility(element);
        el.clear();
        el.sendKeys(text);
        log.debug("Typed '{}' into element", text);
    }

    protected void typeWithClear(WebElement element, String text) {
        WebElement el = waitForVisibility(element);
        el.click();
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.DELETE);
        el.sendKeys(text);
        log.debug("Cleared and typed '{}' into element", text);
    }

    protected String getText(WebElement element) {
        String text = waitForVisibility(element).getText().trim();
        log.debug("Got text: '{}'", text);
        return text;
    }

    protected String getAttributeValue(WebElement element, String attribute) {
        return waitForVisibility(element).getAttribute(attribute);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ─── Dropdown Utilities ───────────────────────────────────────────────────

    protected void selectByVisibleText(WebElement element, String text) {
        new Select(waitForVisibility(element)).selectByVisibleText(text);
        log.debug("Selected '{}' from dropdown", text);
    }

    protected void selectByValue(WebElement element, String value) {
        new Select(waitForVisibility(element)).selectByValue(value);
        log.debug("Selected value '{}' from dropdown", value);
    }

    protected String getSelectedOption(WebElement element) {
        return new Select(waitForVisibility(element)).getFirstSelectedOption().getText();
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    protected void hoverOver(WebElement element) {
        new Actions(driver).moveToElement(waitForVisibility(element)).perform();
        log.debug("Hovered over element");
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ─── List Utilities ───────────────────────────────────────────────────────

    protected List<WebElement> findElements(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

    // ─── Alert Handling ───────────────────────────────────────────────────────

    protected String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    // ─── Frame Handling ───────────────────────────────────────────────────────

    protected void switchToFrame(WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ─── Window Handling ─────────────────────────────────────────────────────

    protected void switchToNewWindow() {
        String mainHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    // ─── Utility ─────────────────────────────────────────────────────────────

    protected void pause(long millis) {
        try { Thread.sleep(millis); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    protected boolean elementExists(By locator) {
        return !driver.findElements(locator).isEmpty();
    }
}

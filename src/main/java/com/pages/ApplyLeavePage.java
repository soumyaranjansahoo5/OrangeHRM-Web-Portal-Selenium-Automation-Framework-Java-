package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * ApplyLeavePage - Leave Module Apply Leave Form.
 * URL: /web/index.php/leave/applyLeave
 */
public class ApplyLeavePage extends BasePage {

    private static final Logger log = LogManager.getLogger(ApplyLeavePage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h6[text()='Apply Leave']")
    private WebElement pageHeader;

    @FindBy(xpath = "//label[text()='Leave Type']/../..//div[@class='oxd-select-text-input']")
    private WebElement leaveTypeDropdown;

    @FindBy(xpath = "//label[text()='From Date']/../..//input")
    private WebElement fromDateInput;

    @FindBy(xpath = "//label[text()='To Date']/../..//input")
    private WebElement toDateInput;

    @FindBy(xpath = "//textarea[@placeholder='Type here']")
    private WebElement commentTextarea;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement applyButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//span[contains(@class,'oxd-input-field-error-message')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@class='oxd-toast-content']//p[contains(@class,'oxd-text--toast-message')]")
    private WebElement successToast;

    // ─── Actions ──────────────────────────────────────────────────────────────

    public ApplyLeavePage selectLeaveType(String type) {
        log.info("Selecting Leave Type: {}", type);
        click(leaveTypeDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + type + "']");
        click(option);
        return this;
    }

    public ApplyLeavePage enterFromDate(String date) {
        log.info("Entering From Date: {}", date);
        typeWithClear(fromDateInput, date);
        pause(300);
        return this;
    }

    public ApplyLeavePage enterToDate(String date) {
        log.info("Entering To Date: {}", date);
        typeWithClear(toDateInput, date);
        pause(300);
        return this;
    }

    public ApplyLeavePage enterComment(String comment) {
        log.info("Entering comment: {}", comment);
        type(commentTextarea, comment);
        return this;
    }

    public ApplyLeavePage clickApply() {
        log.info("Clicking Apply button");
        click(applyButton);
        pause(1500);
        return this;
    }

    public ApplyLeavePage clickReset() {
        log.info("Clicking Reset button");
        click(resetButton);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    public boolean isPageDisplayed() {
        return isDisplayed(pageHeader);
    }

    public boolean isSuccessToastDisplayed() {
        try {
            return isDisplayed(successToast);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}

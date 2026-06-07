package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LeavePage - Leave Module Page Object.
 * URL: /web/index.php/leave/viewLeaveList
 */
public class LeavePage extends BasePage {

    private static final Logger log = LogManager.getLogger(LeavePage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h5[text()='Leave List']")
    private WebElement leaveListHeader;

    @FindBy(xpath = "//a[text()='Apply']")
    private WebElement applyLeaveLink;

    @FindBy(xpath = "//a[text()='My Leave']")
    private WebElement myLeaveLink;

    @FindBy(xpath = "//a[text()='Leave List']")
    private WebElement leaveListLink;

    @FindBy(xpath = "//a[text()='Entitlements ']")
    private WebElement entitlementsMenu;

    @FindBy(xpath = "//label[text()='Date From']/../..//input")
    private WebElement dateFromInput;

    @FindBy(xpath = "//label[text()='Date To']/../..//input")
    private WebElement dateToInput;

    @FindBy(xpath = "//label[text()='Leave Type']/../..//div[@class='oxd-select-text-input']")
    private WebElement leaveTypeDropdown;

    @FindBy(xpath = "//label[text()='Leave Status']/../..//div[@class='oxd-select-text-input']")
    private WebElement leaveStatusDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//div[@class='oxd-table-body']//div[@class='oxd-table-row oxd-table-row--with-border']")
    private List<WebElement> leaveTableRows;

    @FindBy(xpath = "//span[contains(@class,'oxd-text--span')]")
    private WebElement recordCountLabel;

    // ─── Actions ──────────────────────────────────────────────────────────────

    public ApplyLeavePage clickApplyLeave() {
        log.info("Navigating to Apply Leave");
        click(applyLeaveLink);
        waitForUrlContains("applyLeave");
        return new ApplyLeavePage();
    }

    public LeavePage clickLeaveList() {
        log.info("Navigating to Leave List");
        click(leaveListLink);
        waitForUrlContains("viewLeaveList");
        return this;
    }

    public LeavePage enterDateFrom(String date) {
        log.info("Entering Date From: {}", date);
        typeWithClear(dateFromInput, date);
        return this;
    }

    public LeavePage enterDateTo(String date) {
        log.info("Entering Date To: {}", date);
        typeWithClear(dateToInput, date);
        return this;
    }

    public LeavePage selectLeaveType(String type) {
        log.info("Selecting Leave Type: {}", type);
        click(leaveTypeDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + type + "']");
        click(option);
        return this;
    }

    public LeavePage selectLeaveStatus(String status) {
        log.info("Selecting Leave Status: {}", status);
        click(leaveStatusDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(option);
        return this;
    }

    public LeavePage clickSearch() {
        log.info("Clicking Search");
        click(searchButton);
        pause(1500);
        return this;
    }

    public LeavePage clickReset() {
        log.info("Clicking Reset");
        click(resetButton);
        pause(1000);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    public boolean isLeaveListPageDisplayed() {
        return isDisplayed(leaveListHeader);
    }

    public int getLeaveTableRowCount() {
        return leaveTableRows.size();
    }

    public String getRecordCountText() {
        return getText(recordCountLabel);
    }

    public boolean isNoRecordsFound() {
        return !driver.findElements(By.xpath("//span[text()='No Records Found']")).isEmpty();
    }

    public List<String> getLeaveStatusesFromTable() {
        return leaveTableRows.stream()
            .map(row -> {
                List<WebElement> cells = row.findElements(
                    By.xpath(".//div[@class='oxd-table-cell oxd-padding-cell']"));
                return cells.size() > 4 ? cells.get(4).getText().trim() : "";
            })
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }
}

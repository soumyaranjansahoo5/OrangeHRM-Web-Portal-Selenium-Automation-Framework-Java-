package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TimePage - Page Object for OrangeHRM Time module.
 * URL: /web/index.php/time/viewTimeModule
 * Covers: Timesheets, Attendance, Reports sub-modules.
 */
public class TimePage extends BasePage {

    private static final Logger log = LogManager.getLogger(TimePage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h6[contains(text(),'Timesheet') or contains(text(),'My Timesheet')]")
    private WebElement timesheetHeader;

    @FindBy(xpath = "//h5[contains(text(),'Attendance')]")
    private WebElement attendanceHeader;

    @FindBy(xpath = "//h5[text()='My Attendance Records']")
    private WebElement myAttendanceHeader;

    // Top navigation tabs
    @FindBy(xpath = "//a[contains(@class,'oxd-topbar-body-nav-tab-link') and normalize-space()='Timesheets']")
    private WebElement timesheetsNavTab;

    @FindBy(xpath = "//a[contains(@class,'oxd-topbar-body-nav-tab-link') and normalize-space()='Attendance']")
    private WebElement attendanceNavTab;

    @FindBy(xpath = "//a[contains(@class,'oxd-topbar-body-nav-tab-link') and normalize-space()='Reports']")
    private WebElement reportsNavTab;

    // Timesheets sub-menu items
    @FindBy(xpath = "//a[normalize-space()='My Timesheets']")
    private WebElement myTimesheetsLink;

    @FindBy(xpath = "//a[normalize-space()='Employee Timesheets']")
    private WebElement employeeTimesheetsLink;

    // Attendance sub-menu items
    @FindBy(xpath = "//a[normalize-space()='My Records']")
    private WebElement myAttendanceRecordsLink;

    @FindBy(xpath = "//a[normalize-space()='Punch In/Out']")
    private WebElement punchInOutLink;

    @FindBy(xpath = "//a[normalize-space()='Employee Records']")
    private WebElement employeeAttendanceRecordsLink;

    // Punch In / Out form
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement punchSubmitButton;

    @FindBy(xpath = "//label[text()='Note']/../..//textarea")
    private WebElement punchNoteTextarea;

    @FindBy(xpath = "//h6[contains(text(),'Punch')]")
    private WebElement punchFormHeader;

    // Table rows (reused for Attendance Records etc.)
    @FindBy(xpath = "//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-row--with-border')]")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//span[contains(@class,'oxd-text--span') and contains(text(),'Record')]")
    private WebElement recordCountLabel;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//button[@type='submit' and not(contains(@class,'oxd-button--ghost'))]")
    private WebElement searchButton;

    // ─── Actions ──────────────────────────────────────────────────────────────

    /** Click the Timesheets top-nav tab */
    public TimePage clickTimesheetsTab() {
        log.info("Clicking Timesheets tab");
        click(timesheetsNavTab);
        pause(700);
        return this;
    }

    /** Click the Attendance top-nav tab */
    public TimePage clickAttendanceTab() {
        log.info("Clicking Attendance tab");
        click(attendanceNavTab);
        pause(700);
        return this;
    }

    /** Click the Reports top-nav tab */
    public TimePage clickReportsTab() {
        log.info("Clicking Reports tab");
        click(reportsNavTab);
        pause(700);
        return this;
    }

    /** Click My Timesheets sub-menu link */
    public TimePage clickMyTimesheets() {
        log.info("Clicking My Timesheets");
        click(timesheetsNavTab);
        pause(400);
        click(myTimesheetsLink);
        pause(1000);
        return this;
    }

    /** Click Punch In/Out sub-menu link */
    public TimePage clickPunchInOut() {
        log.info("Clicking Punch In/Out");
        click(attendanceNavTab);
        pause(400);
        click(punchInOutLink);
        pause(1000);
        return this;
    }

    /** Click My Attendance Records sub-menu link */
    public TimePage clickMyAttendanceRecords() {
        log.info("Clicking My Attendance Records");
        click(attendanceNavTab);
        pause(400);
        click(myAttendanceRecordsLink);
        pause(1000);
        return this;
    }

    /** Click Employee Timesheets sub-menu link */
    public TimePage clickEmployeeTimesheets() {
        log.info("Clicking Employee Timesheets");
        click(timesheetsNavTab);
        pause(400);
        click(employeeTimesheetsLink);
        pause(1000);
        return this;
    }

    /** Enter a note in the Punch In/Out note textarea */
    public TimePage enterPunchNote(String note) {
        log.info("Entering punch note: {}", note);
        type(punchNoteTextarea, note);
        return this;
    }

    /** Click the Punch In or Punch Out submit button */
    public TimePage clickPunchSubmit() {
        log.info("Clicking Punch submit button");
        click(punchSubmitButton);
        pause(1500);
        return this;
    }

    /** Click the Search button (on attendance/timesheet search forms) */
    public TimePage clickSearch() {
        log.info("Clicking Search");
        click(searchButton);
        pause(1500);
        return this;
    }

    /** Click the Reset button */
    public TimePage clickReset() {
        log.info("Clicking Reset");
        click(resetButton);
        pause(800);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    /** Check whether a timesheet page is currently displayed */
    public boolean isTimesheetPageDisplayed() {
        try {
            return isDisplayed(timesheetHeader);
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("time");
        }
    }

    /** Check whether the Punch In/Out form is displayed */
    public boolean isPunchFormDisplayed() {
        try {
            return isDisplayed(punchFormHeader);
        } catch (Exception e) {
            return false;
        }
    }

    /** Check whether My Attendance Records page is displayed */
    public boolean isAttendancePageDisplayed() {
        try {
            return isDisplayed(myAttendanceHeader);
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("attendance");
        }
    }

    /** Number of rows in the currently visible data table */
    public int getTableRowCount() {
        return tableRows.size();
    }

    /** Whether "No Records Found" message is shown */
    public boolean isNoRecordsFound() {
        return elementExists(By.xpath("//span[text()='No Records Found']"));
    }

    /** Collect all row text values from the first cell of each table row */
    public List<String> getFirstColumnValues() {
        return tableRows.stream()
            .map(row -> {
                List<WebElement> cells = row.findElements(
                    By.xpath(".//div[contains(@class,'oxd-table-cell oxd-padding-cell')]"));
                return cells.isEmpty() ? "" : cells.get(0).getText().trim();
            })
            .filter(v -> !v.isEmpty())
            .collect(Collectors.toList());
    }
}

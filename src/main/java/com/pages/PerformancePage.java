package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PerformancePage - Page Object for OrangeHRM Performance module.
 * URL: /web/index.php/performance/searchPerformanceReview
 * Covers: Manage Reviews, My Reviews, KPIs, Trackers sub-modules.
 */
public class PerformancePage extends BasePage {

    private static final Logger log = LogManager.getLogger(PerformancePage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h5[contains(text(),'Manage Performance Review') or contains(text(),'My Performance Review') or contains(text(),'KPI')]")
    private WebElement pageHeader;

    @FindBy(xpath = "//h5[text()='Manage Performance Review']")
    private WebElement manageReviewsHeader;

    @FindBy(xpath = "//h5[text()='My Performance Review']")
    private WebElement myReviewsHeader;

    // Top navigation tabs
    @FindBy(xpath = "//a[contains(@class,'oxd-topbar-body-nav-tab-link') and normalize-space()='Manage Reviews']")
    private WebElement manageReviewsTab;

    @FindBy(xpath = "//a[contains(@class,'oxd-topbar-body-nav-tab-link') and normalize-space()='My Reviews']")
    private WebElement myReviewsTab;

    // Sub-menu under Configure
    @FindBy(xpath = "//a[contains(@class,'oxd-topbar-body-nav-tab-link') and normalize-space()='Configure']")
    private WebElement configureTab;

    @FindBy(xpath = "//a[normalize-space()='KPIs']")
    private WebElement kpisLink;

    @FindBy(xpath = "//a[normalize-space()='Trackers']")
    private WebElement trackersLink;

    // Search form elements
    @FindBy(xpath = "//label[text()='Review Period']/../..//input[1]")
    private WebElement reviewPeriodFromInput;

    @FindBy(xpath = "//label[text()='Review Period']/../..//input[2]")
    private WebElement reviewPeriodToInput;

    @FindBy(xpath = "//label[text()='Job Title']/../..//div[@class='oxd-select-text-input']")
    private WebElement jobTitleDropdown;

    @FindBy(xpath = "//label[text()='Status']/../..//div[@class='oxd-select-text-input']")
    private WebElement statusDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//button[.//i[contains(@class,'bi-plus')]]")
    private WebElement addButton;

    // Table rows
    @FindBy(xpath = "//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-row--with-border')]")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//span[contains(@class,'oxd-text--span') and contains(text(),'Record')]")
    private WebElement recordCountLabel;

    // ─── Actions ──────────────────────────────────────────────────────────────

    /** Click the Manage Reviews top-nav tab */
    public PerformancePage clickManageReviewsTab() {
        log.info("Clicking Manage Reviews tab");
        click(manageReviewsTab);
        pause(800);
        return this;
    }

    /** Click the My Reviews top-nav tab */
    public PerformancePage clickMyReviewsTab() {
        log.info("Clicking My Reviews tab");
        click(myReviewsTab);
        pause(800);
        return this;
    }

    /** Click Configure tab, then KPIs link */
    public PerformancePage clickKPIs() {
        log.info("Navigating to KPIs under Configure");
        click(configureTab);
        pause(400);
        click(kpisLink);
        pause(1000);
        return this;
    }

    /** Click Configure tab, then Trackers link */
    public PerformancePage clickTrackers() {
        log.info("Navigating to Trackers under Configure");
        click(configureTab);
        pause(400);
        click(trackersLink);
        pause(1000);
        return this;
    }

    /** Select Job Title filter */
    public PerformancePage selectJobTitle(String jobTitle) {
        log.info("Selecting Job Title: {}", jobTitle);
        click(jobTitleDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + jobTitle + "']");
        click(option);
        return this;
    }

    /** Select Status filter */
    public PerformancePage selectStatus(String status) {
        log.info("Selecting Status: {}", status);
        click(statusDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(option);
        return this;
    }

    /** Click the Search button */
    public PerformancePage clickSearch() {
        log.info("Clicking Search");
        click(searchButton);
        pause(1500);
        return this;
    }

    /** Click the Reset button */
    public PerformancePage clickReset() {
        log.info("Clicking Reset");
        click(resetButton);
        pause(800);
        return this;
    }

    /** Click the Add button */
    public PerformancePage clickAdd() {
        log.info("Clicking Add button");
        click(addButton);
        pause(800);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    /** Whether any Performance page header is visible */
    public boolean isPageDisplayed() {
        try {
            return isDisplayed(pageHeader);
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("performance");
        }
    }

    /** Whether the Manage Reviews page is specifically displayed */
    public boolean isManageReviewsPageDisplayed() {
        try {
            return isDisplayed(manageReviewsHeader);
        } catch (Exception e) {
            return false;
        }
    }

    /** Whether the My Reviews page is specifically displayed */
    public boolean isMyReviewsPageDisplayed() {
        try {
            return isDisplayed(myReviewsHeader);
        } catch (Exception e) {
            return false;
        }
    }

    /** Number of rows in the currently visible table */
    public int getTableRowCount() {
        return tableRows.size();
    }

    /** Whether "No Records Found" is displayed */
    public boolean isNoRecordsFound() {
        return elementExists(By.xpath("//span[text()='No Records Found']"));
    }

    /** Record count label text */
    public String getRecordCountText() {
        try {
            return getText(recordCountLabel);
        } catch (Exception e) {
            return "";
        }
    }

    /** Collect first-column values from all table rows */
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

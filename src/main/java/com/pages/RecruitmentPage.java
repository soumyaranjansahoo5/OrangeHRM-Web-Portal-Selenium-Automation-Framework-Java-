package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RecruitmentPage - Page Object for the OrangeHRM Recruitment module.
 * URL: /web/index.php/recruitment/viewRecruitmentModule
 */
public class RecruitmentPage extends BasePage {

    private static final Logger log = LogManager.getLogger(RecruitmentPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h5[text()='Vacancies']")
    private WebElement vacanciesHeader;

    @FindBy(xpath = "//h5[text()='Candidates']")
    private WebElement candidatesHeader;

    @FindBy(xpath = "//a[text()='Candidates']")
    private WebElement candidatesTabLink;

    @FindBy(xpath = "//a[text()='Vacancies']")
    private WebElement vacanciesTabLink;

    @FindBy(xpath = "//button[.//i[contains(@class,'bi-plus')]]")
    private WebElement addButton;

    @FindBy(xpath = "//label[text()='Job Title']/../..//div[@class='oxd-select-text-input']")
    private WebElement jobTitleDropdown;

    @FindBy(xpath = "//label[text()='Vacancy']/../..//div[@class='oxd-select-text-input']")
    private WebElement vacancyDropdown;

    @FindBy(xpath = "//label[text()='Hiring Manager']/../..//input")
    private WebElement hiringManagerInput;

    @FindBy(xpath = "//label[text()='Status']/../..//div[@class='oxd-select-text-input']")
    private WebElement statusDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-row--with-border')]")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//span[contains(@class,'oxd-text--span') and contains(text(),'Record')]")
    private WebElement recordCountLabel;

    // ─── Actions ──────────────────────────────────────────────────────────────

    /** Navigate to the Candidates sub-tab */
    public RecruitmentPage clickCandidatesTab() {
        log.info("Clicking Candidates tab");
        click(candidatesTabLink);
        pause(1000);
        return this;
    }

    /** Navigate to the Vacancies sub-tab */
    public RecruitmentPage clickVacanciesTab() {
        log.info("Clicking Vacancies tab");
        click(vacanciesTabLink);
        pause(1000);
        return this;
    }

    /** Click the Add button (Add Candidate or Add Vacancy) */
    public RecruitmentPage clickAdd() {
        log.info("Clicking Add button in Recruitment module");
        click(addButton);
        pause(800);
        return this;
    }

    /** Select a Job Title from the dropdown */
    public RecruitmentPage selectJobTitle(String title) {
        log.info("Selecting Job Title: {}", title);
        click(jobTitleDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + title + "']");
        click(option);
        return this;
    }

    /** Select a Vacancy from the dropdown */
    public RecruitmentPage selectVacancy(String vacancy) {
        log.info("Selecting Vacancy: {}", vacancy);
        click(vacancyDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + vacancy + "']");
        click(option);
        return this;
    }

    /** Enter a Hiring Manager name (with autocomplete support) */
    public RecruitmentPage enterHiringManager(String managerName) {
        log.info("Entering Hiring Manager: {}", managerName);
        type(hiringManagerInput, managerName);
        pause(1000);
        By suggestion = By.xpath("//div[@role='option']//span[contains(text(),'" + managerName + "')]");
        if (!driver.findElements(suggestion).isEmpty()) {
            click(suggestion);
        }
        return this;
    }

    /** Select Status filter */
    public RecruitmentPage selectStatus(String status) {
        log.info("Selecting Status: {}", status);
        click(statusDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(option);
        return this;
    }

    /** Click Search button */
    public RecruitmentPage clickSearch() {
        log.info("Clicking Search");
        click(searchButton);
        pause(1500);
        return this;
    }

    /** Click Reset button */
    public RecruitmentPage clickReset() {
        log.info("Clicking Reset");
        click(resetButton);
        pause(800);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    /** Check whether the Vacancies list page is displayed */
    public boolean isVacanciesPageDisplayed() {
        try {
            return isDisplayed(vacanciesHeader);
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("recruitment");
        }
    }

    /** Check whether the Candidates list page is displayed */
    public boolean isCandidatesPageDisplayed() {
        try {
            return isDisplayed(candidatesHeader);
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("viewCandidates");
        }
    }

    /** Total row count in the currently visible table */
    public int getTableRowCount() {
        return tableRows.size();
    }

    /** Text of the record-count label (e.g. "(1) Record Found") */
    public String getRecordCountText() {
        return getText(recordCountLabel);
    }

    /** Whether "No Records Found" is shown */
    public boolean isNoRecordsFound() {
        return elementExists(By.xpath("//span[text()='No Records Found']"));
    }

    /** Collect visible text of the first column in every table row */
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

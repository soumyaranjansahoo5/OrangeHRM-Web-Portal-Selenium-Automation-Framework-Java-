package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DirectoryPage - Page Object for OrangeHRM Directory module.
 * URL: /web/index.php/directory/viewDirectory
 * Displays employee directory with name, job title, and location cards.
 */
public class DirectoryPage extends BasePage {

    private static final Logger log = LogManager.getLogger(DirectoryPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h5[text()='Directory']")
    private WebElement pageHeader;

    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement searchInput;

    @FindBy(xpath = "//label[text()='Job Title']/../..//div[@class='oxd-select-text-input']")
    private WebElement jobTitleDropdown;

    @FindBy(xpath = "//label[text()='Location']/../..//div[@class='oxd-select-text-input']")
    private WebElement locationDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    // Directory cards (each employee shown as a card)
    @FindBy(xpath = "//div[contains(@class,'orangehrm-directory-card')]")
    private List<WebElement> employeeCards;

    // Employee name inside each card
    @FindBy(xpath = "//div[contains(@class,'orangehrm-directory-card')]//p[contains(@class,'--card-header-title')]")
    private List<WebElement> employeeNameLabels;

    // Job title inside each card
    @FindBy(xpath = "//div[contains(@class,'orangehrm-directory-card')]//p[contains(@class,'--card-header-subtitle')]")
    private List<WebElement> employeeJobTitleLabels;

    @FindBy(xpath = "//span[contains(@class,'oxd-text--span') and contains(text(),'Record')]")
    private WebElement recordCountLabel;

    // ─── Actions ──────────────────────────────────────────────────────────────

    /** Type in the search input field */
    public DirectoryPage searchByName(String name) {
        log.info("Searching directory by name: {}", name);
        type(searchInput, name);
        return this;
    }

    /** Select a Job Title filter from dropdown */
    public DirectoryPage selectJobTitle(String jobTitle) {
        log.info("Selecting Job Title filter: {}", jobTitle);
        click(jobTitleDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + jobTitle + "']");
        click(option);
        return this;
    }

    /** Select a Location filter from dropdown */
    public DirectoryPage selectLocation(String location) {
        log.info("Selecting Location filter: {}", location);
        click(locationDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + location + "']");
        click(option);
        return this;
    }

    /** Click the Search button */
    public DirectoryPage clickSearch() {
        log.info("Clicking Search button");
        click(searchButton);
        pause(1500);
        return this;
    }

    /** Click the Reset button */
    public DirectoryPage clickReset() {
        log.info("Clicking Reset button");
        click(resetButton);
        pause(1000);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    /** Check whether the Directory page header is displayed */
    public boolean isPageDisplayed() {
        return isDisplayed(pageHeader);
    }

    /** Total number of employee cards shown in the directory */
    public int getEmployeeCardCount() {
        return employeeCards.size();
    }

    /** Collect all employee names from directory cards */
    public List<String> getEmployeeNames() {
        return employeeNameLabels.stream()
            .map(el -> el.getText().trim())
            .filter(name -> !name.isEmpty())
            .collect(Collectors.toList());
    }

    /** Collect all job titles shown in directory cards */
    public List<String> getEmployeeJobTitles() {
        return employeeJobTitleLabels.stream()
            .map(el -> el.getText().trim())
            .filter(title -> !title.isEmpty())
            .collect(Collectors.toList());
    }

    /** Check whether a given name appears in any directory card */
    public boolean isEmployeeCardPresent(String name) {
        return getEmployeeNames().stream()
            .anyMatch(n -> n.toLowerCase().contains(name.toLowerCase()));
    }

    /** Whether "No Records Found" is displayed */
    public boolean isNoRecordsFound() {
        return elementExists(By.xpath("//span[text()='No Records Found']"))
            || employeeCards.isEmpty();
    }

    /** Record count label text (e.g. "(5) Records Found") */
    public String getRecordCountText() {
        try {
            return getText(recordCountLabel);
        } catch (Exception e) {
            return "";
        }
    }
}

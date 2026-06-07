package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * MyInfoPage - Page Object for OrangeHRM My Info module.
 * URL: /web/index.php/pim/viewPersonalDetails/empNumber/{id}
 */
public class MyInfoPage extends BasePage {

    private static final Logger log = LogManager.getLogger(MyInfoPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h6[text()='Personal Details']")
    private WebElement personalDetailsHeader;

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[@name='middleName']")
    private WebElement middleNameField;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement lastNameField;

    @FindBy(xpath = "//label[text()='Employee Id']/../..//input")
    private WebElement employeeIdField;

    @FindBy(xpath = "//label[text()='Other Id']/../..//input")
    private WebElement otherIdField;

    @FindBy(xpath = "//label[text()='License Number']/../..//input")
    private WebElement licenseNumberField;

    @FindBy(xpath = "//label[text()='License Expiry Date']/../..//input")
    private WebElement licenseExpiryDateField;

    @FindBy(xpath = "//label[text()='Date of Birth']/../..//input")
    private WebElement dateOfBirthField;

    @FindBy(xpath = "//label[text()='Gender']/../..//div[@class='oxd-radio-wrapper'][1]//input")
    private WebElement genderMaleRadio;

    @FindBy(xpath = "//label[text()='Gender']/../..//div[@class='oxd-radio-wrapper'][2]//input")
    private WebElement genderFemaleRadio;

    @FindBy(xpath = "//label[text()='Nationality']/../..//div[@class='oxd-select-text-input']")
    private WebElement nationalityDropdown;

    @FindBy(xpath = "//label[text()='Marital Status']/../..//div[@class='oxd-select-text-input']")
    private WebElement maritalStatusDropdown;

    @FindBy(xpath = "(//button[@type='submit'])[1]")
    private WebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast--success')]//p[contains(@class,'oxd-toast-content-text')]")
    private WebElement successToast;

    // Sub-navigation tabs
    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Personal Details']")
    private WebElement personalDetailsTab;

    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Contact Details']")
    private WebElement contactDetailsTab;

    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Emergency Contacts']")
    private WebElement emergencyContactsTab;

    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Dependents']")
    private WebElement dependentsTab;

    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Immigration']")
    private WebElement immigrationTab;

    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Job']")
    private WebElement jobTab;

    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Salary']")
    private WebElement salaryTab;

    @FindBy(xpath = "//a[@class='orangehrm-tabs-item' and text()='Qualifications']")
    private WebElement qualificationsTab;

    // ─── Actions ──────────────────────────────────────────────────────────────

    /** Update first name field */
    public MyInfoPage updateFirstName(String firstName) {
        log.info("Updating first name to: {}", firstName);
        typeWithClear(firstNameField, firstName);
        return this;
    }

    /** Update middle name field */
    public MyInfoPage updateMiddleName(String middleName) {
        log.info("Updating middle name to: {}", middleName);
        typeWithClear(middleNameField, middleName);
        return this;
    }

    /** Update last name field */
    public MyInfoPage updateLastName(String lastName) {
        log.info("Updating last name to: {}", lastName);
        typeWithClear(lastNameField, lastName);
        return this;
    }

    /** Update Date of Birth field (format: yyyy-mm-dd) */
    public MyInfoPage updateDateOfBirth(String dob) {
        log.info("Updating Date of Birth to: {}", dob);
        typeWithClear(dateOfBirthField, dob);
        return this;
    }

    /** Select nationality from dropdown */
    public MyInfoPage selectNationality(String nationality) {
        log.info("Selecting Nationality: {}", nationality);
        click(nationalityDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + nationality + "']");
        click(option);
        return this;
    }

    /** Select marital status from dropdown */
    public MyInfoPage selectMaritalStatus(String status) {
        log.info("Selecting Marital Status: {}", status);
        click(maritalStatusDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(option);
        return this;
    }

    /** Click the Male gender radio button */
    public MyInfoPage selectGenderMale() {
        log.info("Selecting gender: Male");
        jsClick(genderMaleRadio);
        return this;
    }

    /** Click the Female gender radio button */
    public MyInfoPage selectGenderFemale() {
        log.info("Selecting gender: Female");
        jsClick(genderFemaleRadio);
        return this;
    }

    /** Click Save and wait for success toast */
    public MyInfoPage clickSave() {
        log.info("Clicking Save on Personal Details");
        click(saveButton);
        pause(1500);
        return this;
    }

    /** Navigate to Contact Details tab */
    public MyInfoPage clickContactDetailsTab() {
        log.info("Clicking Contact Details tab");
        click(contactDetailsTab);
        pause(800);
        return this;
    }

    /** Navigate to Qualifications tab */
    public MyInfoPage clickQualificationsTab() {
        log.info("Clicking Qualifications tab");
        click(qualificationsTab);
        pause(800);
        return this;
    }

    /** Navigate to Job tab */
    public MyInfoPage clickJobTab() {
        log.info("Clicking Job tab");
        click(jobTab);
        pause(800);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    /** Check Personal Details page is displayed */
    public boolean isPageDisplayed() {
        return isDisplayed(personalDetailsHeader);
    }

    /** Get current first name value from the form */
    public String getFirstNameValue() {
        return getAttributeValue(firstNameField, "value");
    }

    /** Get current last name value from the form */
    public String getLastNameValue() {
        return getAttributeValue(lastNameField, "value");
    }

    /** Get current Employee ID value */
    public String getEmployeeIdValue() {
        return getAttributeValue(employeeIdField, "value");
    }

    /** Check whether the success toast notification is displayed */
    public boolean isSuccessToastDisplayed() {
        try {
            return isDisplayed(successToast);
        } catch (Exception e) {
            return false;
        }
    }
}

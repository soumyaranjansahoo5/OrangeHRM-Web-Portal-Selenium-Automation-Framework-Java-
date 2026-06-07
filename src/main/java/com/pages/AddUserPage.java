package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * AddUserPage - Admin Module Add System User Form.
 * URL: /web/index.php/admin/saveSystemUser
 */
public class AddUserPage extends BasePage {

    private static final Logger log = LogManager.getLogger(AddUserPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h6[text()='Add User']")
    private WebElement pageHeader;

    @FindBy(xpath = "//label[text()='User Role']/../..//div[@class='oxd-select-text-input']")
    private WebElement userRoleDropdown;

    @FindBy(xpath = "//label[text()='Status']/../..//div[@class='oxd-select-text-input']")
    private WebElement statusDropdown;

    @FindBy(xpath = "//label[text()='Employee Name']/../..//input[@placeholder='Type for hints...']")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//label[text()='Username']/../..//input")
    private WebElement usernameInput;

    @FindBy(xpath = "//label[text()='Password']/../..//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//label[text()='Confirm Password']/../..//input[@type='password']")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@type='button' and .//i[contains(@class,'bi-x')]]")
    private WebElement cancelButton;

    @FindBy(xpath = "//span[contains(@class,'oxd-input-field-error-message')]")
    private WebElement errorMessage;

    // ─── Actions ──────────────────────────────────────────────────────────────

    public AddUserPage selectUserRole(String role) {
        log.info("Selecting User Role: {}", role);
        click(userRoleDropdown);
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + role + "']");
        click(optionLocator);
        return this;
    }

    public AddUserPage selectStatus(String status) {
        log.info("Selecting Status: {}", status);
        click(statusDropdown);
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(optionLocator);
        return this;
    }

    public AddUserPage typeEmployeeName(String name) {
        log.info("Typing employee name: {}", name);
        type(employeeNameInput, name);
        pause(1200);
        By suggestion = By.xpath("//div[@role='option']//span[contains(text(),'" + name + "')]");
        if (!driver.findElements(suggestion).isEmpty()) {
            click(suggestion);
        }
        return this;
    }

    public AddUserPage enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(usernameInput, username);
        return this;
    }

    public AddUserPage enterPassword(String password) {
        log.info("Entering password");
        type(passwordInput, password);
        return this;
    }

    public AddUserPage enterConfirmPassword(String password) {
        log.info("Entering confirm password");
        type(confirmPasswordInput, password);
        return this;
    }

    public AdminPage clickSave() {
        log.info("Clicking Save button");
        click(saveButton);
        pause(1500);
        return new AdminPage();
    }

    public AdminPage clickCancel() {
        log.info("Clicking Cancel button");
        click(cancelButton);
        return new AdminPage();
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    public boolean isPageDisplayed() {
        return isDisplayed(pageHeader);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}

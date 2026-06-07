package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage - Page Object for OrangeHRM Login Page.
 * URL: /web/index.php/auth/login
 */
public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(@class,'oxd-alert-content-text')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//p[contains(@class,'oxd-text') and contains(text(),'Invalid credentials')]")
    private WebElement invalidCredentialsMsg;

    @FindBy(xpath = "//h5[contains(@class,'oxd-text') and text()='Login']")
    private WebElement loginHeader;

    @FindBy(xpath = "//div[@class='orangehrm-login-logo']//img")
    private WebElement orangeHrmLogo;

    @FindBy(xpath = "//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//span[text()='Username']/../following-sibling::*//span[contains(@class,'oxd-input-field-error-message')]")
    private WebElement usernameRequiredError;

    @FindBy(xpath = "//span[text()='Password']/../following-sibling::*//span[contains(@class,'oxd-input-field-error-message')]")
    private WebElement passwordRequiredError;

    // ─── Actions ──────────────────────────────────────────────────────────────

    public LoginPage enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        type(passwordInput, password);
        return this;
    }

    public DashboardPage clickLogin() {
        log.info("Clicking Login button");
        click(loginButton);
        return new DashboardPage();
    }

    public LoginPage clickLoginExpectingFailure() {
        log.info("Clicking Login button (expecting failure)");
        click(loginButton);
        return this;
    }

    public DashboardPage loginAs(String username, String password) {
        enterUsername(username)
            .enterPassword(password)
            .click(loginButton);
        waitForUrlContains("dashboard");
        log.info("Login successful for user: {}", username);
        return new DashboardPage();
    }

    public void clickForgotPassword() {
        log.info("Clicking Forgot Password link");
        click(forgotPasswordLink);
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginHeader);
    }

    public boolean isLogoDisplayed() {
        return isDisplayed(orangeHrmLogo);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getUsernameFieldValue() {
        return getAttributeValue(usernameInput, "value");
    }

    public boolean isUsernameRequiredErrorShown() {
        return isDisplayed(usernameRequiredError);
    }

    public boolean isPasswordRequiredErrorShown() {
        return isDisplayed(passwordRequiredError);
    }

    public boolean isLoginButtonEnabled() {
        return isEnabled(loginButton);
    }
}

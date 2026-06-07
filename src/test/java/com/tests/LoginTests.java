package com.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LoginTests - Test suite for OrangeHRM Login module.
 * Covers: valid login, invalid credentials, blank fields, UI elements.
 */
public class LoginTests extends BaseTest {

    // ─── TC_LOGIN_001 ─────────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify successful login with valid admin credentials")
    public void tc_login_001_validAdminLogin() {
        logStep("TC_LOGIN_001: Verify login with valid credentials");

        DashboardPage dashboard = loginPage.loginAs(
            config.getAdminUsername(), config.getAdminPassword());

        Assert.assertTrue(dashboard.isDashboardDisplayed(),
            "Dashboard should be displayed after successful login");
        Assert.assertTrue(driver().getCurrentUrl().contains("dashboard"),
            "URL should contain 'dashboard' after login");
        logStep("PASS: User successfully logged in and redirected to dashboard");
    }

    // ─── TC_LOGIN_002 ─────────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify login fails with invalid username and valid password")
    public void tc_login_002_invalidUsernameValidPassword() {
        logStep("TC_LOGIN_002: Verify error shown for invalid username");

        loginPage.enterUsername("invalidUser123")
                 .enterPassword(config.getAdminPassword())
                 .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for invalid username");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials",
            "Error message text should be 'Invalid credentials'");
        logStep("PASS: Correct error shown for invalid username");
    }

    // ─── TC_LOGIN_003 ─────────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify login fails with valid username and invalid password")
    public void tc_login_003_validUsernameInvalidPassword() {
        logStep("TC_LOGIN_003: Verify error shown for invalid password");

        loginPage.enterUsername(config.getAdminUsername())
                 .enterPassword("wrongPassword@999")
                 .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for invalid password");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials",
            "Error message text should be 'Invalid credentials'");
        logStep("PASS: Correct error shown for invalid password");
    }

    // ─── TC_LOGIN_004 ─────────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify login fails when both username and password are blank")
    public void tc_login_004_emptyCredentials() {
        logStep("TC_LOGIN_004: Verify validation for empty credentials");

        loginPage.enterUsername("")
                 .enterPassword("")
                 .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed()
            || loginPage.isUsernameRequiredErrorShown()
            || loginPage.isPasswordRequiredErrorShown(),
            "Required field error or invalid credentials error should be displayed");
        logStep("PASS: Validation triggered for empty credentials");
    }

    // ─── TC_LOGIN_005 ─────────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify login fails with blank username only")
    public void tc_login_005_blankUsernameOnly() {
        logStep("TC_LOGIN_005: Verify validation for blank username");

        loginPage.enterUsername("")
                 .enterPassword(config.getAdminPassword())
                 .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed()
            || loginPage.isUsernameRequiredErrorShown(),
            "Error should appear for blank username");
        logStep("PASS: Error shown for blank username");
    }

    // ─── TC_LOGIN_006 ─────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify login fails with blank password only")
    public void tc_login_006_blankPasswordOnly() {
        logStep("TC_LOGIN_006: Verify validation for blank password");

        loginPage.enterUsername(config.getAdminUsername())
                 .enterPassword("")
                 .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed()
            || loginPage.isPasswordRequiredErrorShown(),
            "Error should appear for blank password");
        logStep("PASS: Error shown for blank password");
    }

    // ─── TC_LOGIN_007 ─────────────────────────────────────────────────────────

    @Test(priority = 7,
          description = "Verify OrangeHRM logo is visible on the login page")
    public void tc_login_007_logoVisibleOnLoginPage() {
        logStep("TC_LOGIN_007: Verify OrangeHRM logo is displayed on login page");

        Assert.assertTrue(loginPage.isLogoDisplayed(),
            "OrangeHRM logo should be visible on the login page");
        logStep("PASS: Logo is visible");
    }

    // ─── TC_LOGIN_008 ─────────────────────────────────────────────────────────

    @Test(priority = 8,
          description = "Verify login page is displayed correctly with all elements")
    public void tc_login_008_loginPageElementsVisible() {
        logStep("TC_LOGIN_008: Verify login page displays all required elements");

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page header should be displayed");
        Assert.assertTrue(loginPage.isLogoDisplayed(),
            "OrangeHRM logo should be displayed");
        Assert.assertTrue(loginPage.isLoginButtonEnabled(),
            "Login button should be enabled");
        logStep("PASS: All login page elements visible");
    }

    // ─── TC_LOGIN_009 ─────────────────────────────────────────────────────────

    @Test(priority = 9,
          description = "Verify login page title is 'OrangeHRM'")
    public void tc_login_009_verifyPageTitle() {
        logStep("TC_LOGIN_009: Verify page title on login page");

        String title = DriverManager.getDriver().getTitle();
        Assert.assertEquals(title, "OrangeHRM",
            "Page title should be 'OrangeHRM'");
        logStep("PASS: Page title is correct: " + title);
    }

    // ─── TC_LOGIN_010 ─────────────────────────────────────────────────────────

    @Test(priority = 10,
          description = "Verify successful logout after login")
    public void tc_login_010_successfulLogout() {
        logStep("TC_LOGIN_010: Verify logout functionality");

        DashboardPage dashboard = loginPage.loginAs(
            config.getAdminUsername(), config.getAdminPassword());

        Assert.assertTrue(dashboard.isDashboardDisplayed(),
            "Dashboard should be displayed after login");

        loginPage = dashboard.logout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page should be displayed after logout");
        Assert.assertTrue(driver().getCurrentUrl().contains("auth/login"),
            "URL should redirect back to login page after logout");
        logStep("PASS: Logout successful, redirected to login page");
    }

    // ─── Helper ───────────────────────────────────────────────────────────────

    private org.openqa.selenium.WebDriver driver() {
        return com.orangehrm.utils.DriverManager.getDriver();
    }
}

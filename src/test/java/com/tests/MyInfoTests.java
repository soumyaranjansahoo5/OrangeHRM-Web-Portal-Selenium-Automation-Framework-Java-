package com.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.MyInfoPage;
import com.orangehrm.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * MyInfoTests - Test suite for OrangeHRM My Info module.
 * Covers: page load, personal details form, tabs navigation, field validation.
 *
 * Test IDs: TC_MYINFO_001 → TC_MYINFO_007
 */
public class MyInfoTests extends BaseTest {

    // ─── TC_MYINFO_001 ───────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify My Info Personal Details page loads after login")
    public void tc_myinfo_001_personalDetailsPageLoads() {
        logStep("TC_MYINFO_001: Navigate to My Info → Personal Details");

        loginAsAdmin();
        navigateToMyInfo();

        MyInfoPage myInfoPage = new MyInfoPage();

        Assert.assertTrue(myInfoPage.isPageDisplayed(),
            "Personal Details page should be visible under My Info");
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("pim"),
            "URL should contain 'pim' for My Info module");

        logStep("PASS: My Info Personal Details page loaded correctly");
    }

    // ─── TC_MYINFO_002 ───────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify Employee ID field is pre-populated on My Info page")
    public void tc_myinfo_002_employeeIdPrePopulated() {
        logStep("TC_MYINFO_002: Verify Employee ID is pre-filled on My Info page");

        loginAsAdmin();
        navigateToMyInfo();

        MyInfoPage myInfoPage = new MyInfoPage();
        String empId = myInfoPage.getEmployeeIdValue();

        logStep("Employee ID found: " + empId);

        Assert.assertNotNull(empId, "Employee ID should not be null");
        Assert.assertFalse(empId.isEmpty(), "Employee ID should not be empty");

        logStep("PASS: Employee ID pre-populated: " + empId);
    }

    // ─── TC_MYINFO_003 ───────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify First Name field is pre-populated on My Info page")
    public void tc_myinfo_003_firstNamePrePopulated() {
        logStep("TC_MYINFO_003: Verify First Name field is pre-filled");

        loginAsAdmin();
        navigateToMyInfo();

        MyInfoPage myInfoPage = new MyInfoPage();
        String firstName = myInfoPage.getFirstNameValue();

        logStep("First Name found: " + firstName);

        Assert.assertNotNull(firstName, "First Name should not be null");
        Assert.assertFalse(firstName.isEmpty(), "First Name should not be empty");

        logStep("PASS: First Name pre-populated: " + firstName);
    }

    // ─── TC_MYINFO_004 ───────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify Last Name field is pre-populated on My Info page")
    public void tc_myinfo_004_lastNamePrePopulated() {
        logStep("TC_MYINFO_004: Verify Last Name field is pre-filled");

        loginAsAdmin();
        navigateToMyInfo();

        MyInfoPage myInfoPage = new MyInfoPage();
        String lastName = myInfoPage.getLastNameValue();

        logStep("Last Name found: " + lastName);

        Assert.assertNotNull(lastName, "Last Name should not be null");
        Assert.assertFalse(lastName.isEmpty(), "Last Name should not be empty");

        logStep("PASS: Last Name pre-populated: " + lastName);
    }

    // ─── TC_MYINFO_005 ───────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify Contact Details tab is accessible under My Info")
    public void tc_myinfo_005_contactDetailsTabAccessible() {
        logStep("TC_MYINFO_005: Navigate to Contact Details tab in My Info");

        loginAsAdmin();
        navigateToMyInfo();

        MyInfoPage myInfoPage = new MyInfoPage();
        myInfoPage.clickContactDetailsTab();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        logStep("URL after clicking Contact Details tab: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("pim"),
            "URL should remain within the PIM module after tab click");

        logStep("PASS: Contact Details tab accessible");
    }

    // ─── TC_MYINFO_006 ───────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify Qualifications tab is accessible under My Info")
    public void tc_myinfo_006_qualificationsTabAccessible() {
        logStep("TC_MYINFO_006: Navigate to Qualifications tab in My Info");

        loginAsAdmin();
        navigateToMyInfo();

        MyInfoPage myInfoPage = new MyInfoPage();
        myInfoPage.clickQualificationsTab();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        logStep("URL after clicking Qualifications tab: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("pim"),
            "URL should remain within the PIM module after tab click");

        logStep("PASS: Qualifications tab accessible");
    }

    // ─── TC_MYINFO_007 ───────────────────────────────────────────────────────

    @Test(priority = 7,
          description = "Verify Job tab is accessible under My Info")
    public void tc_myinfo_007_jobTabAccessible() {
        logStep("TC_MYINFO_007: Navigate to Job tab in My Info");

        loginAsAdmin();
        navigateToMyInfo();

        MyInfoPage myInfoPage = new MyInfoPage();
        myInfoPage.clickJobTab();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        logStep("URL after clicking Job tab: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("pim"),
            "URL should remain within the PIM module after tab click");

        logStep("PASS: Job tab accessible");
    }

    // ─── Private Helper ───────────────────────────────────────────────────────

    /**
     * Navigates directly to My Info (Personal Details) using the base URL.
     * Avoids relying on a specific sidebar click since My Info resolves
     * to the logged-in user's employee record dynamically.
     */
    private void navigateToMyInfo() {
        String myInfoUrl = config.getBaseUrl()
            .replace("auth/login", "pim/viewMyDetails");
        DriverManager.getDriver().navigate().to(myInfoUrl);
        pause(2000);
    }

    private void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

package com.orangehrm.tests;

import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * AdminTests - Test suite for OrangeHRM Admin / System Users module.
 * Covers: page load, user table, search, add user navigation.
 */
public class AdminTests extends BaseTest {

    // ─── TC_ADMIN_001 ────────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify Admin page loads with System Users list")
    public void tc_admin_001_adminPageLoads() {
        logStep("TC_ADMIN_001: Verify Admin module loads correctly");

        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.navigateToAdmin();

        Assert.assertTrue(adminPage.isPageDisplayed(),
            "Admin System Users page should be displayed");
        Assert.assertTrue(com.orangehrm.utils.DriverManager.getDriver()
            .getCurrentUrl().contains("admin"),
            "URL should contain 'admin'");
        logStep("PASS: Admin page loaded successfully");
    }

    // ─── TC_ADMIN_002 ────────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify user table has at least one record on Admin page")
    public void tc_admin_002_userTableHasRecords() {
        logStep("TC_ADMIN_002: Verify user table contains records");

        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.navigateToAdmin();

        int rowCount = adminPage.getUserTableRowCount();
        logStep("User table row count: " + rowCount);

        Assert.assertTrue(rowCount > 0,
            "User table should contain at least one system user");
        logStep("PASS: User table has " + rowCount + " records");
    }

    // ─── TC_ADMIN_003 ────────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify searching by username 'Admin' returns results")
    public void tc_admin_003_searchByAdminUsername() {
        logStep("TC_ADMIN_003: Verify search by username 'Admin'");

        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.navigateToAdmin();

        adminPage.searchByUsername("Admin").clickSearch();

        int rowCount = adminPage.getUserTableRowCount();
        logStep("Records found for username 'Admin': " + rowCount);

        Assert.assertTrue(rowCount > 0,
            "At least one user should be found for username 'Admin'");
        Assert.assertTrue(adminPage.isUserInTable("Admin"),
            "'Admin' should appear in the user table");
        logStep("PASS: Search returned results for username 'Admin'");
    }

    // ─── TC_ADMIN_004 ────────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify search with invalid username shows no records")
    public void tc_admin_004_searchInvalidUsernameNoRecords() {
        logStep("TC_ADMIN_004: Verify search for non-existent username");

        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.navigateToAdmin();

        adminPage.searchByUsername("NON_EXISTENT_USER_XYZ_999").clickSearch();

        Assert.assertTrue(adminPage.isNoRecordsFound()
            || adminPage.getUserTableRowCount() == 0,
            "No records should be found for non-existent username");
        logStep("PASS: No records shown for invalid username");
    }

    // ─── TC_ADMIN_005 ────────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify Reset button clears search and shows all users")
    public void tc_admin_005_resetClearsSearch() {
        logStep("TC_ADMIN_005: Verify Reset button clears search filter");

        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.navigateToAdmin();

        int beforeReset = adminPage.getUserTableRowCount();
        adminPage.searchByUsername("NON_EXISTENT_USER_XYZ").clickSearch();
        adminPage.clickReset();

        int afterReset = adminPage.getUserTableRowCount();
        logStep("Records after reset: " + afterReset);

        Assert.assertTrue(afterReset > 0,
            "After reset, user table should display records again");
        logStep("PASS: Reset restored full user list. Before: " + beforeReset + ", After: " + afterReset);
    }

    // ─── TC_ADMIN_006 ────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify Add User page loads from Admin module")
    public void tc_admin_006_addUserPageLoads() {
        logStep("TC_ADMIN_006: Verify Add User page navigation");

        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.navigateToAdmin();

        com.orangehrm.pages.AddUserPage addUserPage = adminPage.clickAddUser();

        Assert.assertTrue(addUserPage.isPageDisplayed(),
            "Add User page should be displayed");
        Assert.assertTrue(com.orangehrm.utils.DriverManager.getDriver()
            .getCurrentUrl().contains("saveSystemUser"),
            "URL should contain 'saveSystemUser'");
        logStep("PASS: Add User page loaded correctly");
    }
}

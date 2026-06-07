package com.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * DashboardTests - Test suite for OrangeHRM Dashboard module.
 * Covers: menu visibility, widget presence, navigation, URL validation.
 */
public class DashboardTests extends BaseTest {

    // ─── TC_DASH_001 ─────────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify dashboard is displayed after login")
    public void tc_dash_001_dashboardDisplayedAfterLogin() {
        logStep("TC_DASH_001: Verify dashboard displayed after login");

        DashboardPage dashboard = loginAsAdmin();

        Assert.assertTrue(dashboard.isDashboardDisplayed(),
            "Dashboard page should be displayed after successful login");
        Assert.assertTrue(com.orangehrm.utils.DriverManager.getDriver()
            .getCurrentUrl().contains("dashboard"),
            "URL should contain 'dashboard'");
        logStep("PASS: Dashboard displayed correctly");
    }

    // ─── TC_DASH_002 ─────────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify Admin menu item is visible on sidebar")
    public void tc_dash_002_adminMenuVisible() {
        logStep("TC_DASH_002: Verify Admin menu item visibility");

        DashboardPage dashboard = loginAsAdmin();

        Assert.assertTrue(dashboard.isAdminMenuVisible(),
            "Admin menu should be visible in the sidebar");
        logStep("PASS: Admin menu is visible");
    }

    // ─── TC_DASH_003 ─────────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify PIM menu item is visible on sidebar")
    public void tc_dash_003_pimMenuVisible() {
        logStep("TC_DASH_003: Verify PIM menu item visibility");

        DashboardPage dashboard = loginAsAdmin();

        Assert.assertTrue(dashboard.isPIMMenuVisible(),
            "PIM menu should be visible in the sidebar");
        logStep("PASS: PIM menu is visible");
    }

    // ─── TC_DASH_004 ─────────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify sidebar contains expected menu items")
    public void tc_dash_004_sidebarMenuItemsPresent() {
        logStep("TC_DASH_004: Verify expected sidebar menu items are present");

        DashboardPage dashboard = loginAsAdmin();
        List<String> menuItems = dashboard.getAllMenuItems();

        String[] expectedMenus = {"Admin", "PIM", "Leave", "Time",
            "Recruitment", "My Info", "Performance", "Dashboard", "Directory"};

        for (String expected : expectedMenus) {
            Assert.assertTrue(dashboard.isMenuItemPresent(expected),
                "Menu item '" + expected + "' should be present in sidebar");
            logStep("Verified menu: " + expected);
        }
        logStep("PASS: All expected menu items are present. Total: " + menuItems.size());
    }

    // ─── TC_DASH_005 ─────────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify navigation from Dashboard to PIM module")
    public void tc_dash_005_navigateToPIM() {
        logStep("TC_DASH_005: Verify navigation to PIM module");

        DashboardPage dashboard = loginAsAdmin();
        dashboard.navigateToPIM();

        String currentUrl = com.orangehrm.utils.DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("pim"),
            "URL should contain 'pim' after clicking PIM menu");
        logStep("PASS: Successfully navigated to PIM. URL: " + currentUrl);
    }

    // ─── TC_DASH_006 ─────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify navigation from Dashboard to Admin module")
    public void tc_dash_006_navigateToAdmin() {
        logStep("TC_DASH_006: Verify navigation to Admin module");

        DashboardPage dashboard = loginAsAdmin();
        dashboard.navigateToAdmin();

        String currentUrl = com.orangehrm.utils.DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("admin"),
            "URL should contain 'admin' after clicking Admin menu");
        logStep("PASS: Successfully navigated to Admin. URL: " + currentUrl);
    }

    // ─── TC_DASH_007 ─────────────────────────────────────────────────────────

    @Test(priority = 7,
          description = "Verify navigation from Dashboard to Leave module")
    public void tc_dash_007_navigateToLeave() {
        logStep("TC_DASH_007: Verify navigation to Leave module");

        DashboardPage dashboard = loginAsAdmin();
        dashboard.navigateToLeave();

        String currentUrl = com.orangehrm.utils.DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("leave"),
            "URL should contain 'leave' after clicking Leave menu");
        logStep("PASS: Successfully navigated to Leave. URL: " + currentUrl);
    }
}

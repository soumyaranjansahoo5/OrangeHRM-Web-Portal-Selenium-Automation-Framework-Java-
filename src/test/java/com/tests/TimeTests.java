package com.orangehrm.tests;

import com.orangehrm.pages.TimePage;
import com.orangehrm.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TimeTests - Test suite for OrangeHRM Time module.
 * Covers: module navigation, Timesheets, Attendance, Punch In/Out.
 *
 * Test IDs: TC_TIME_001 → TC_TIME_006
 */
public class TimeTests extends BaseTest {

    // ─── TC_TIME_001 ─────────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify Time module loads via direct URL navigation")
    public void tc_time_001_timeModuleLoads() {
        logStep("TC_TIME_001: Navigate to Time module");

        loginAsAdmin();
        navigateToTime("viewTimeModule");

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("time"),
            "URL should contain 'time' after navigating to Time module");

        logStep("PASS: Time module URL confirmed: " + currentUrl);
    }

    // ─── TC_TIME_002 ─────────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify My Timesheets page loads successfully")
    public void tc_time_002_myTimesheetsLoads() {
        logStep("TC_TIME_002: Navigate to My Timesheets");

        loginAsAdmin();
        navigateToTime("viewMyTimesheet");
        pause(1500);

        TimePage timePage = new TimePage();

        Assert.assertTrue(timePage.isTimesheetPageDisplayed(),
            "My Timesheets page should be visible");
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("time"),
            "URL should contain 'time'");

        logStep("PASS: My Timesheets page loaded correctly");
    }

    // ─── TC_TIME_003 ─────────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify Punch In/Out page loads from Attendance sub-menu")
    public void tc_time_003_punchInOutPageLoads() {
        logStep("TC_TIME_003: Navigate to Punch In/Out page");

        loginAsAdmin();
        navigateToTime("punchInOut");
        pause(1500);

        TimePage timePage = new TimePage();

        Assert.assertTrue(timePage.isPunchFormDisplayed(),
            "Punch In/Out form should be visible on the page");
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("time"),
            "URL should still be within the Time module");

        logStep("PASS: Punch In/Out page loaded correctly");
    }

    // ─── TC_TIME_004 ─────────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify My Attendance Records page loads successfully")
    public void tc_time_004_myAttendanceRecordsLoads() {
        logStep("TC_TIME_004: Navigate to My Attendance Records");

        loginAsAdmin();
        navigateToTime("viewMyAttendanceRecord");
        pause(1500);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("time") || currentUrl.contains("attendance"),
            "URL should contain 'time' or 'attendance' for the Attendance Records page");

        logStep("PASS: My Attendance Records page URL confirmed: " + currentUrl);
    }

    // ─── TC_TIME_005 ─────────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify Employee Timesheets page loads in Time module")
    public void tc_time_005_employeeTimesheetsLoads() {
        logStep("TC_TIME_005: Navigate to Employee Timesheets");

        loginAsAdmin();
        navigateToTime("viewEmployeeTimesheet");
        pause(1500);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("time"),
            "URL should contain 'time' for Employee Timesheets page");

        logStep("PASS: Employee Timesheets page URL confirmed: " + currentUrl);
    }

    // ─── TC_TIME_006 ─────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify Employee Attendance Records page loads in Time module")
    public void tc_time_006_employeeAttendanceRecordsLoads() {
        logStep("TC_TIME_006: Navigate to Employee Attendance Records");

        loginAsAdmin();
        navigateToTime("viewAttendanceRecord");
        pause(1500);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("time") || currentUrl.contains("attendance"),
            "URL should contain 'time' or 'attendance' for Employee Attendance Records");

        logStep("PASS: Employee Attendance Records page URL confirmed: " + currentUrl);
    }

    // ─── Private Helpers ──────────────────────────────────────────────────────

    /**
     * Navigate to a specific sub-page of the Time module.
     *
     * @param subPath  The sub-path segment, e.g. "viewMyTimesheet" or "punchInOut"
     */
    private void navigateToTime(String subPath) {
        String url = config.getBaseUrl().replace("auth/login", "time/" + subPath);
        DriverManager.getDriver().navigate().to(url);
        pause(1500);
    }

    private void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

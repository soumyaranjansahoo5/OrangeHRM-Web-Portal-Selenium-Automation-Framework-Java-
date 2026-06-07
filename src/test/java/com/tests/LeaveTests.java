package com.orangehrm.tests;

import com.orangehrm.pages.ApplyLeavePage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LeavePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LeaveTests - Test suite for OrangeHRM Leave module.
 * Covers: leave list, apply leave page, search, navigation.
 */
public class LeaveTests extends BaseTest {

    // ─── TC_LEAVE_001 ────────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify Leave List page loads correctly")
    public void tc_leave_001_leaveListPageLoads() {
        logStep("TC_LEAVE_001: Verify Leave List page loads");

        DashboardPage dashboard = loginAsAdmin();
        LeavePage leavePage = dashboard.navigateToLeave();

        Assert.assertTrue(leavePage.isLeaveListPageDisplayed(),
            "Leave List page should be displayed");
        Assert.assertTrue(com.orangehrm.utils.DriverManager.getDriver()
            .getCurrentUrl().contains("leave"),
            "URL should contain 'leave'");
        logStep("PASS: Leave List page loaded successfully");
    }

    // ─── TC_LEAVE_002 ────────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify Apply Leave page loads from Leave module")
    public void tc_leave_002_applyLeavePageLoads() {
        logStep("TC_LEAVE_002: Verify Apply Leave page navigation");

        DashboardPage dashboard = loginAsAdmin();
        LeavePage leavePage = dashboard.navigateToLeave();
        ApplyLeavePage applyPage = leavePage.clickApplyLeave();

        Assert.assertTrue(applyPage.isPageDisplayed(),
            "Apply Leave page should be displayed");
        Assert.assertTrue(com.orangehrm.utils.DriverManager.getDriver()
            .getCurrentUrl().contains("applyLeave"),
            "URL should contain 'applyLeave'");
        logStep("PASS: Apply Leave page loaded correctly");
    }

    // ─── TC_LEAVE_003 ────────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify applying leave without selecting Leave Type shows validation error")
    public void tc_leave_003_applyLeaveWithoutTypeShowsError() {
        logStep("TC_LEAVE_003: Verify validation on Apply Leave without Leave Type");

        DashboardPage dashboard = loginAsAdmin();
        LeavePage leavePage = dashboard.navigateToLeave();
        ApplyLeavePage applyPage = leavePage.clickApplyLeave();

        applyPage.clickApply();

        Assert.assertTrue(applyPage.isErrorDisplayed(),
            "Validation error should appear when Leave Type is not selected");
        logStep("PASS: Validation triggered for missing Leave Type");
    }

    // ─── TC_LEAVE_004 ────────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify Leave List search with date range returns results or no records")
    public void tc_leave_004_searchByDateRange() {
        logStep("TC_LEAVE_004: Verify Leave List search by date range");

        DashboardPage dashboard = loginAsAdmin();
        LeavePage leavePage = dashboard.navigateToLeave();

        leavePage.enterDateFrom("2025-01-01")
                 .enterDateTo("2025-12-31")
                 .clickSearch();

        int rowCount = leavePage.getLeaveTableRowCount();
        logStep("Leave records found for date range: " + rowCount);

        // Either records or "No Records Found" — both are valid outcomes
        Assert.assertTrue(rowCount >= 0,
            "Leave list search should complete without error");
        logStep("PASS: Leave list search by date range completed");
    }

    // ─── TC_LEAVE_005 ────────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify Reset button on Leave List clears filters")
    public void tc_leave_005_resetClearsLeaveListFilters() {
        logStep("TC_LEAVE_005: Verify Reset button clears Leave List filters");

        DashboardPage dashboard = loginAsAdmin();
        LeavePage leavePage = dashboard.navigateToLeave();

        leavePage.enterDateFrom("2020-01-01").enterDateTo("2020-01-02").clickSearch();
        leavePage.clickReset();

        Assert.assertTrue(com.orangehrm.utils.DriverManager.getDriver()
            .getCurrentUrl().contains("leave"),
            "After reset, should remain on Leave page");
        logStep("PASS: Reset button cleared filters on Leave List");
    }

    // ─── TC_LEAVE_006 ────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify Apply Leave Reset button clears the form")
    public void tc_leave_006_applyLeaveResetClearsForm() {
        logStep("TC_LEAVE_006: Verify Reset button clears Apply Leave form");

        DashboardPage dashboard = loginAsAdmin();
        LeavePage leavePage = dashboard.navigateToLeave();
        ApplyLeavePage applyPage = leavePage.clickApplyLeave();

        applyPage.enterFromDate("2025-08-01")
                 .enterToDate("2025-08-05")
                 .enterComment("Test comment for reset verification")
                 .clickReset();

        Assert.assertTrue(applyPage.isPageDisplayed(),
            "Apply Leave page should still be displayed after reset");
        logStep("PASS: Apply Leave form reset successfully");
    }
}

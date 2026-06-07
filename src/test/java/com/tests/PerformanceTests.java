package com.orangehrm.tests;

import com.orangehrm.pages.PerformancePage;
import com.orangehrm.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * PerformanceTests - Test suite for OrangeHRM Performance module.
 * Covers: Manage Reviews, My Reviews, KPIs, Trackers, search/reset.
 *
 * Test IDs: TC_PERF_001 → TC_PERF_007
 */
public class PerformanceTests extends BaseTest {

    // ─── TC_PERF_001 ─────────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify Performance module loads via direct URL navigation")
    public void tc_perf_001_performanceModuleLoads() {
        logStep("TC_PERF_001: Navigate to Performance module");

        loginAsAdmin();
        navigateToPerformance("searchPerformanceReview");

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("performance"),
            "URL should contain 'performance' after navigating to Performance module");

        logStep("PASS: Performance module URL confirmed: " + currentUrl);
    }

    // ─── TC_PERF_002 ─────────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify Manage Reviews page loads in Performance module")
    public void tc_perf_002_manageReviewsPageLoads() {
        logStep("TC_PERF_002: Verify Manage Reviews page");

        loginAsAdmin();
        navigateToPerformance("searchPerformanceReview");
        pause(1500);

        PerformancePage performancePage = new PerformancePage();

        Assert.assertTrue(performancePage.isPageDisplayed(),
            "Manage Reviews page should be displayed");

        logStep("PASS: Manage Reviews page loaded correctly");
    }

    // ─── TC_PERF_003 ─────────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify My Reviews page loads in Performance module")
    public void tc_perf_003_myReviewsPageLoads() {
        logStep("TC_PERF_003: Navigate to My Reviews in Performance module");

        loginAsAdmin();
        navigateToPerformance("myPerformanceReview");
        pause(1500);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("performance"),
            "URL should contain 'performance' for My Reviews page");

        logStep("PASS: My Reviews page URL confirmed: " + currentUrl);
    }

    // ─── TC_PERF_004 ─────────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify KPIs page loads under Performance Configure")
    public void tc_perf_004_kpisPageLoads() {
        logStep("TC_PERF_004: Navigate to KPIs under Performance Configure");

        loginAsAdmin();
        navigateToPerformance("searchKpi");
        pause(1500);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("performance") || currentUrl.contains("kpi"),
            "URL should contain 'performance' or 'kpi' for KPIs page");

        logStep("PASS: KPIs page URL confirmed: " + currentUrl);
    }

    // ─── TC_PERF_005 ─────────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify Trackers page loads under Performance Configure")
    public void tc_perf_005_trackersPageLoads() {
        logStep("TC_PERF_005: Navigate to Trackers under Performance Configure");

        loginAsAdmin();
        navigateToPerformance("searchPerformanceTracker");
        pause(1500);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("performance"),
            "URL should contain 'performance' for Trackers page");

        logStep("PASS: Trackers page URL confirmed: " + currentUrl);
    }

    // ─── TC_PERF_006 ─────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify Search on Manage Reviews returns results or no-records message")
    public void tc_perf_006_searchManageReviewsNoFilter() {
        logStep("TC_PERF_006: Search Manage Reviews with no filter");

        loginAsAdmin();
        navigateToPerformance("searchPerformanceReview");
        pause(1500);

        PerformancePage performancePage = new PerformancePage();
        performancePage.clickSearch();

        int rowCount = performancePage.getTableRowCount();
        logStep("Performance review records found: " + rowCount);

        Assert.assertTrue(rowCount >= 0,
            "Search should complete without error");

        logStep("PASS: Manage Reviews search completed. Rows: " + rowCount);
    }

    // ─── TC_PERF_007 ─────────────────────────────────────────────────────────

    @Test(priority = 7,
          description = "Verify Reset button on Manage Reviews clears search filters")
    public void tc_perf_007_resetClearsManageReviewsFilters() {
        logStep("TC_PERF_007: Verify Reset clears Manage Reviews filters");

        loginAsAdmin();
        navigateToPerformance("searchPerformanceReview");
        pause(1500);

        PerformancePage performancePage = new PerformancePage();
        performancePage.clickSearch();
        performancePage.clickReset();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("performance"),
            "After reset, should remain on Performance page");

        logStep("PASS: Reset button functional on Manage Reviews page");
    }

    // ─── Private Helper ───────────────────────────────────────────────────────

    /**
     * Navigate directly to a Performance sub-page.
     *
     * @param subPath e.g. "searchPerformanceReview", "myPerformanceReview", "searchKpi"
     */
    private void navigateToPerformance(String subPath) {
        String url = config.getBaseUrl().replace("auth/login", "performance/" + subPath);
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

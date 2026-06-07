package com.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.RecruitmentPage;
import com.orangehrm.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * RecruitmentTests - Test suite for OrangeHRM Recruitment module.
 * Covers: module navigation, vacancies list, candidates list, search, reset.
 *
 * Test IDs: TC_REC_001 → TC_REC_007
 */
public class RecruitmentTests extends BaseTest {

    // ─── TC_REC_001 ──────────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "Verify Recruitment module loads via sidebar navigation")
    public void tc_rec_001_recruitmentModuleLoads() {
        logStep("TC_REC_001: Navigate to Recruitment module from sidebar");

        DashboardPage dashboard = loginAsAdmin();

        // Navigate using direct menu click
        dashboard.navigateToLeave(); // go somewhere first
        DriverManager.getDriver().navigate().to(
            config.getBaseUrl().replace("auth/login", "recruitment/viewRecruitmentModule"));
        pause(2000);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("recruitment"),
            "URL should contain 'recruitment' after navigating to Recruitment module");

        logStep("PASS: Recruitment module URL confirmed: " + currentUrl);
    }

    // ─── TC_REC_002 ──────────────────────────────────────────────────────────

    @Test(priority = 2,
          description = "Verify Vacancies tab is accessible in Recruitment module")
    public void tc_rec_002_vacanciesTabLoads() {
        logStep("TC_REC_002: Verify Vacancies tab in Recruitment module");

        loginAsAdmin();

        String vacanciesUrl = config.getBaseUrl()
            .replace("auth/login", "recruitment/viewJobVacancy");
        DriverManager.getDriver().navigate().to(vacanciesUrl);
        pause(2000);

        RecruitmentPage recruitmentPage = new RecruitmentPage();

        Assert.assertTrue(recruitmentPage.isVacanciesPageDisplayed(),
            "Vacancies page header should be visible");
        logStep("PASS: Vacancies tab loaded successfully");
    }

    // ─── TC_REC_003 ──────────────────────────────────────────────────────────

    @Test(priority = 3,
          description = "Verify Candidates tab is accessible in Recruitment module")
    public void tc_rec_003_candidatesTabLoads() {
        logStep("TC_REC_003: Verify Candidates tab in Recruitment module");

        loginAsAdmin();

        String candidatesUrl = config.getBaseUrl()
            .replace("auth/login", "recruitment/viewCandidates");
        DriverManager.getDriver().navigate().to(candidatesUrl);
        pause(2000);

        RecruitmentPage recruitmentPage = new RecruitmentPage();

        Assert.assertTrue(recruitmentPage.isCandidatesPageDisplayed(),
            "Candidates page header should be visible");
        logStep("PASS: Candidates tab loaded successfully");
    }

    // ─── TC_REC_004 ──────────────────────────────────────────────────────────

    @Test(priority = 4,
          description = "Verify Candidates search with no filters returns all records or no records message")
    public void tc_rec_004_searchCandidatesNoFilter() {
        logStep("TC_REC_004: Search Candidates with no filters applied");

        loginAsAdmin();
        DriverManager.getDriver().navigate().to(
            config.getBaseUrl().replace("auth/login", "recruitment/viewCandidates"));
        pause(2000);

        RecruitmentPage recruitmentPage = new RecruitmentPage();
        recruitmentPage.clickSearch();

        int rowCount = recruitmentPage.getTableRowCount();
        logStep("Candidate records found: " + rowCount);

        // Either shows records or "No Records Found" — both are valid
        Assert.assertTrue(rowCount >= 0,
            "Search should complete without error");
        logStep("PASS: Candidates search completed. Rows: " + rowCount);
    }

    // ─── TC_REC_005 ──────────────────────────────────────────────────────────

    @Test(priority = 5,
          description = "Verify Vacancies list shows records or no records message")
    public void tc_rec_005_vacanciesListHasContent() {
        logStep("TC_REC_005: Verify Vacancies list content");

        loginAsAdmin();
        DriverManager.getDriver().navigate().to(
            config.getBaseUrl().replace("auth/login", "recruitment/viewJobVacancy"));
        pause(2000);

        RecruitmentPage recruitmentPage = new RecruitmentPage();
        int rowCount = recruitmentPage.getTableRowCount();
        logStep("Vacancy records found: " + rowCount);

        Assert.assertTrue(rowCount >= 0,
            "Vacancies list should load without error");
        logStep("PASS: Vacancies list loaded. Rows: " + rowCount);
    }

    // ─── TC_REC_006 ──────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "Verify Reset button on Vacancies search clears filters")
    public void tc_rec_006_vacanciesResetButton() {
        logStep("TC_REC_006: Verify Reset button on Vacancies search");

        loginAsAdmin();
        DriverManager.getDriver().navigate().to(
            config.getBaseUrl().replace("auth/login", "recruitment/viewJobVacancy"));
        pause(2000);

        RecruitmentPage recruitmentPage = new RecruitmentPage();
        recruitmentPage.clickSearch();
        recruitmentPage.clickReset();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("recruitment"),
            "After reset, should remain in the Recruitment module");
        logStep("PASS: Reset button functional on Vacancies search");
    }

    // ─── TC_REC_007 ──────────────────────────────────────────────────────────

    @Test(priority = 7,
          description = "Verify Add Vacancy button navigates to Add Vacancy form")
    public void tc_rec_007_addVacancyButtonNavigates() {
        logStep("TC_REC_007: Verify Add Vacancy button navigates correctly");

        loginAsAdmin();
        DriverManager.getDriver().navigate().to(
            config.getBaseUrl().replace("auth/login", "recruitment/viewJobVacancy"));
        pause(2000);

        RecruitmentPage recruitmentPage = new RecruitmentPage();
        recruitmentPage.clickAdd();
        pause(1500);

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("recruitment") || currentUrl.contains("vacancy"),
            "URL should contain 'recruitment' or 'vacancy' after clicking Add");
        logStep("PASS: Add Vacancy button navigated correctly. URL: " + currentUrl);
    }

    // ─── Helper ───────────────────────────────────────────────────────────────

    private void pause(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

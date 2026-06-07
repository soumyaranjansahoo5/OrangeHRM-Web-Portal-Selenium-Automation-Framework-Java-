package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DashboardPage - Page Object for OrangeHRM Dashboard.
 * URL: /web/index.php/dashboard/index
 */
public class DashboardPage extends BasePage {

    private static final Logger log = LogManager.getLogger(DashboardPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h6[contains(@class,'oxd-text') and contains(text(),'Dashboard')]")
    private WebElement dashboardHeader;

    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    private WebElement userDropdownButton;

    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutLink;

    @FindBy(xpath = "//ul[@class='oxd-main-menu']//li")
    private List<WebElement> menuItems;

    @FindBy(xpath = "//p[@class='oxd-userdropdown-name']")
    private WebElement loggedInUsername;

    @FindBy(xpath = "//div[@class='orangehrm-dashboard-widget-name-title']")
    private List<WebElement> dashboardWidgetTitles;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='PIM']]")
    private WebElement pimMenuLink;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='Leave']]")
    private WebElement leaveMenuLink;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='Time']]")
    private WebElement timeMenuLink;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='Recruitment']]")
    private WebElement recruitmentMenuLink;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='My Info']]")
    private WebElement myInfoMenuLink;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='Performance']]")
    private WebElement performanceMenuLink;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='Admin']]")
    private WebElement adminMenuLink;

    @FindBy(xpath = "//a[@class='oxd-main-menu-item' and .//span[text()='Directory']]")
    private WebElement directoryMenuLink;

    // ─── Actions ──────────────────────────────────────────────────────────────

    public LoginPage logout() {
        log.info("Logging out of application");
        click(userDropdownButton);
        waitForVisibility(logoutLink);
        click(logoutLink);
        waitForUrlContains("auth/login");
        log.info("Successfully logged out");
        return new LoginPage();
    }

    public EmployeeListPage navigateToPIM() {
        log.info("Navigating to PIM module");
        click(pimMenuLink);
        waitForUrlContains("pim");
        return new EmployeeListPage();
    }

    public LeavePage navigateToLeave() {
        log.info("Navigating to Leave module");
        click(leaveMenuLink);
        waitForUrlContains("leave");
        return new LeavePage();
    }

    public AdminPage navigateToAdmin() {
        log.info("Navigating to Admin module");
        click(adminMenuLink);
        waitForUrlContains("admin");
        return new AdminPage();
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    public boolean isDashboardDisplayed() {
        try {
            return isDisplayed(dashboardHeader);
        } catch (Exception e) {
            // Also check URL as fallback
            return driver.getCurrentUrl().contains("dashboard");
        }
    }

    public String getLoggedInUsername() {
        click(userDropdownButton);
        String name = getText(loggedInUsername);
        click(userDropdownButton); // close dropdown
        return name;
    }

    public List<String> getAllMenuItems() {
        return menuItems.stream()
            .map(el -> el.getText().trim())
            .filter(t -> !t.isEmpty())
            .collect(Collectors.toList());
    }

    public List<String> getDashboardWidgetTitles() {
        return dashboardWidgetTitles.stream()
            .map(el -> el.getText().trim())
            .collect(Collectors.toList());
    }

    public boolean isMenuItemPresent(String menuName) {
        return menuItems.stream()
            .anyMatch(el -> el.getText().trim().equalsIgnoreCase(menuName));
    }

    public int getMenuItemCount() {
        return menuItems.size();
    }

    public boolean isAdminMenuVisible() {
        return isDisplayed(adminMenuLink);
    }

    public boolean isPIMMenuVisible() {
        return isDisplayed(pimMenuLink);
    }
}

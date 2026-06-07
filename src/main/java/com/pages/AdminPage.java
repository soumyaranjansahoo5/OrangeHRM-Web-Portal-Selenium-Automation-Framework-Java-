package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AdminPage - Page Object for OrangeHRM Admin Module.
 * URL: /web/index.php/admin/viewSystemUsers
 */
public class AdminPage extends BasePage {

    private static final Logger log = LogManager.getLogger(AdminPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h5[text()='System Users']")
    private WebElement pageHeader;

    @FindBy(xpath = "//button[.//i[contains(@class,'bi-plus')]]")
    private WebElement addUserButton;

    @FindBy(xpath = "//label[text()='Username']/../..//input")
    private WebElement usernameSearchInput;

    @FindBy(xpath = "//label[text()='User Role']/../..//div[@class='oxd-select-text-input']")
    private WebElement userRoleDropdown;

    @FindBy(xpath = "//label[text()='Status']/../..//div[@class='oxd-select-text-input']")
    private WebElement statusDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//div[@class='oxd-table-body']//div[@class='oxd-table-row oxd-table-row--with-border']")
    private List<WebElement> userTableRows;

    @FindBy(xpath = "//span[contains(@class,'oxd-text--span')]")
    private WebElement recordCountLabel;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-cell-actions')]//button[1]")
    private List<WebElement> editButtons;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-cell-actions')]//button[2]")
    private List<WebElement> deleteButtons;

    // Top nav sub-menu
    @FindBy(xpath = "//a[text()='User Management ']")
    private WebElement userManagementMenu;

    @FindBy(xpath = "//a[text()='Job ']")
    private WebElement jobMenu;

    @FindBy(xpath = "//a[text()='Organization ']")
    private WebElement organizationMenu;

    @FindBy(xpath = "//a[text()='Qualifications ']")
    private WebElement qualificationsMenu;

    // ─── Actions ──────────────────────────────────────────────────────────────

    public AdminPage searchByUsername(String username) {
        log.info("Searching user by username: {}", username);
        type(usernameSearchInput, username);
        return this;
    }

    public AdminPage clickSearch() {
        log.info("Clicking Search button");
        click(searchButton);
        pause(1500);
        return this;
    }

    public AdminPage clickReset() {
        log.info("Clicking Reset button");
        click(resetButton);
        pause(1000);
        return this;
    }

    public AddUserPage clickAddUser() {
        log.info("Clicking Add User button");
        click(addUserButton);
        waitForUrlContains("saveSystemUser");
        return new AddUserPage();
    }

    public void clickEditForRow(int rowIndex) {
        log.info("Clicking Edit for row: {}", rowIndex);
        click(editButtons.get(rowIndex));
    }

    public void clickDeleteForRow(int rowIndex) {
        log.info("Clicking Delete for row: {}", rowIndex);
        click(deleteButtons.get(rowIndex));
    }

    public AdminPage selectUserRole(String role) {
        log.info("Selecting User Role: {}", role);
        click(userRoleDropdown);
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + role + "']");
        click(optionLocator);
        return this;
    }

    public AdminPage selectStatus(String status) {
        log.info("Selecting Status: {}", status);
        click(statusDropdown);
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(optionLocator);
        return this;
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    public boolean isPageDisplayed() {
        return isDisplayed(pageHeader);
    }

    public int getUserTableRowCount() {
        return userTableRows.size();
    }

    public String getRecordCountText() {
        return getText(recordCountLabel);
    }

    public List<String> getUsernamesFromTable() {
        return userTableRows.stream()
            .map(row -> {
                List<WebElement> cells = row.findElements(
                    By.xpath(".//div[@class='oxd-table-cell oxd-padding-cell']"));
                return cells.size() > 0 ? cells.get(0).getText().trim() : "";
            })
            .filter(t -> !t.isEmpty())
            .collect(Collectors.toList());
    }

    public boolean isUserInTable(String username) {
        return getUsernamesFromTable().stream()
            .anyMatch(u -> u.equalsIgnoreCase(username));
    }

    public boolean isNoRecordsFound() {
        return !driver.findElements(By.xpath("//span[text()='No Records Found']")).isEmpty();
    }
}

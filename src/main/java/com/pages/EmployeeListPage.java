package com.orangehrm.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EmployeeListPage - PIM module Employee List.
 * URL: /web/index.php/pim/viewEmployeeList
 */
public class EmployeeListPage extends BasePage {

    private static final Logger log = LogManager.getLogger(EmployeeListPage.class);

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(xpath = "//h5[text()='Employee Information']")
    private WebElement pageHeader;

    @FindBy(xpath = "//button[.//i[contains(@class,'bi-plus')]]")
    private WebElement addEmployeeButton;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameSearchInput;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameSearchInput;

    @FindBy(xpath = "//input[@placeholder='Employee Id']")
    private WebElement employeeIdSearchInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@type='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//div[@class='oxd-table-body']//div[@class='oxd-table-row oxd-table-row--with-border']")
    private List<WebElement> employeeTableRows;

    @FindBy(xpath = "//span[@class='oxd-text oxd-text--span']")
    private WebElement recordCountLabel;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-cell-actions')]//button[1]")
    private List<WebElement> editButtons;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-cell-actions')]//button[2]")
    private List<WebElement> deleteButtons;

    // ─── Actions ──────────────────────────────────────────────────────────────

    public AddEmployeePage clickAddEmployee() {
        log.info("Clicking Add Employee button");
        click(addEmployeeButton);
        waitForUrlContains("addEmployee");
        return new AddEmployeePage();
    }

    public EmployeeListPage searchByFirstName(String firstName) {
        log.info("Searching employee by first name: {}", firstName);
        type(firstNameSearchInput, firstName);
        return this;
    }

    public EmployeeListPage searchByLastName(String lastName) {
        log.info("Searching employee by last name: {}", lastName);
        type(lastNameSearchInput, lastName);
        return this;
    }

    public EmployeeListPage searchByEmployeeId(String empId) {
        log.info("Searching employee by ID: {}", empId);
        type(employeeIdSearchInput, empId);
        return this;
    }

    public EmployeeListPage clickSearch() {
        log.info("Clicking Search button");
        click(searchButton);
        pause(1500);
        return this;
    }

    public EmployeeListPage clickReset() {
        log.info("Clicking Reset button");
        click(resetButton);
        pause(1000);
        return this;
    }

    public void clickEditForRow(int rowIndex) {
        log.info("Clicking Edit for row index: {}", rowIndex);
        click(editButtons.get(rowIndex));
    }

    public void clickDeleteForRow(int rowIndex) {
        log.info("Clicking Delete for row index: {}", rowIndex);
        click(deleteButtons.get(rowIndex));
    }

    // ─── Assertions / Getters ─────────────────────────────────────────────────

    public boolean isPageDisplayed() {
        return isDisplayed(pageHeader);
    }

    public int getEmployeeTableRowCount() {
        return employeeTableRows.size();
    }

    public String getRecordCountText() {
        return getText(recordCountLabel);
    }

    public List<String> getEmployeeNamesFromTable() {
        return employeeTableRows.stream()
            .map(row -> {
                List<WebElement> cells = row.findElements(By.xpath(".//div[@class='oxd-table-cell oxd-padding-cell']"));
                return cells.size() > 1 ? cells.get(1).getText().trim() : "";
            })
            .filter(name -> !name.isEmpty())
            .collect(Collectors.toList());
    }

    public boolean isEmployeeInTable(String name) {
        return getEmployeeNamesFromTable().stream()
            .anyMatch(n -> n.toLowerCase().contains(name.toLowerCase()));
    }

    public boolean isNoRecordsFound() {
        return driver.findElements(By.xpath("//span[text()='No Records Found']")).size() > 0;
    }
}

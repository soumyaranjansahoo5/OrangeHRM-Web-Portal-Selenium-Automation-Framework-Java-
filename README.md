# OrangeHRM Web Portal – Selenium Automation Framework

[![CI](https://github.com/soumyaranjansahoo5/OrangeHRM-Web-Portal-Selenium-Automation-Framework/actions/workflows/ci.yml/badge.svg)](https://github.com/soumyaranjansahoo5/OrangeHRM-Web-Portal-Selenium-Automation-Framework/actions)
![Java](https://img.shields.io/badge/Java-17-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-orange)
![Maven](https://img.shields.io/badge/Maven-3.9+-red)

A **production-grade Selenium WebDriver + Java + TestNG** automation framework for the [OrangeHRM Demo Portal](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login).  
Built with the **Page Object Model (POM)** design pattern, ExtentReports HTML reporting, Log4j2 logging, Apache POI data-driven support, and full GitHub Actions CI integration.

---

## 📋 Table of Contents

- [Project Structure](#-project-structure)
- [Tech Stack](#-tech-stack)
- [Modules & Test Coverage](#-modules--test-coverage)
- [Prerequisites](#-prerequisites)
- [Setup & Installation](#-setup--installation)
- [Configuration](#-configuration)
- [Running Tests](#-running-tests)
- [Reports](#-reports)
- [CI/CD Pipeline](#-cicd-pipeline)
- [Framework Architecture](#-framework-architecture)
- [Author](#-author)

---

## 📁 Project Structure

```
OrangeHRM-Web-Portal-Selenium-Automation-Framework/
│
├── .github/
│   └── workflows/
│       └── ci.yml                        # GitHub Actions CI pipeline
│
├── src/
│   ├── main/java/com/orangehrm/
│   │   ├── config/
│   │   │   └── ConfigReader.java         # Singleton properties loader
│   │   ├── pages/
│   │   │   ├── BasePage.java             # Abstract parent – all reusable actions
│   │   │   ├── LoginPage.java            # Login module POM
│   │   │   ├── DashboardPage.java        # Dashboard / sidebar navigation POM
│   │   │   ├── EmployeeListPage.java     # PIM – Employee List POM
│   │   │   ├── AddEmployeePage.java      # PIM – Add Employee form POM
│   │   │   ├── AdminPage.java            # Admin – System Users POM
│   │   │   ├── AddUserPage.java          # Admin – Add User form POM
│   │   │   ├── LeavePage.java            # Leave List POM
│   │   │   ├── ApplyLeavePage.java       # Apply Leave form POM
│   │   │   ├── RecruitmentPage.java      # Recruitment module POM
│   │   │   ├── MyInfoPage.java           # My Info / Personal Details POM
│   │   │   ├── TimePage.java             # Time module POM
│   │   │   ├── DirectoryPage.java        # Directory module POM
│   │   │   └── PerformancePage.java      # Performance module POM
│   │   └── utils/
│   │       ├── DriverManager.java        # ThreadLocal WebDriver factory
│   │       ├── ExtentReportManager.java  # ExtentReports singleton
│   │       ├── ScreenshotUtil.java       # Screenshot capture utility
│   │       ├── WaitUtil.java             # Fluent / Explicit wait helpers
│   │       └── ExcelUtil.java            # Apache POI data-driven helper
│   │
│   └── test/
│       ├── java/com/orangehrm/
│       │   ├── listeners/
│       │   │   ├── TestListener.java     # ITestListener – auto log pass/fail/skip
│       │   │   └── RetryAnalyzer.java    # IRetryAnalyzer – configurable retry
│       │   └── tests/
│       │       ├── BaseTest.java         # Abstract parent – driver + report lifecycle
│       │       ├── LoginTests.java       # 10 Login test cases
│       │       ├── DashboardTests.java   # 7 Dashboard test cases
│       │       ├── EmployeeTests.java    # 9 PIM/Employee test cases
│       │       ├── AdminTests.java       # 6 Admin test cases
│       │       ├── LeaveTests.java       # 6 Leave test cases
│       │       ├── RecruitmentTests.java # 7 Recruitment test cases
│       │       ├── MyInfoTests.java      # 7 My Info test cases
│       │       ├── TimeTests.java        # 6 Time test cases
│       │       ├── DirectoryTests.java   # 6 Directory test cases
│       │       └── PerformanceTests.java # 7 Performance test cases
│       │
│       └── resources/
│           ├── config.properties         # All configurable settings
│           ├── log4j2.xml                # Logging configuration
│           ├── testng.xml                # Master TestNG suite definition
│           └── testdata/
│               └── LoginTestData.xlsx    # Excel data for data-driven tests
│
├── reports/                              # ExtentReports HTML output
├── logs/                                 # Log4j2 rolling log files
├── .gitignore
├── pom.xml                               # Maven dependencies & plugins
└── README.md
```

---

## 🛠 Tech Stack

| Tool / Library        | Version  | Purpose                              |
|-----------------------|----------|--------------------------------------|
| Java                  | 17       | Core language                        |
| Selenium WebDriver    | 4.18.1   | Browser automation                   |
| TestNG                | 7.9.0    | Test framework & assertions          |
| WebDriverManager      | 5.7.0    | Auto driver binary management        |
| ExtentReports         | 5.1.1    | HTML test execution reports          |
| Log4j2                | 2.23.0   | Structured logging                   |
| Apache POI            | 5.2.5    | Excel data-driven testing            |
| Java Faker            | 1.0.2    | Dynamic test data generation         |
| Commons IO            | 2.15.1   | Screenshot file operations           |
| Maven                 | 3.9+     | Build & dependency management        |
| GitHub Actions        | –        | CI/CD pipeline                       |

---

## ✅ Modules & Test Coverage

| Module         | Test Class           | Test Cases | Coverage Areas                                      |
|----------------|----------------------|------------|-----------------------------------------------------|
| Login          | `LoginTests`         | 10         | Valid login, invalid creds, blank fields, logout    |
| Dashboard      | `DashboardTests`     | 7          | Menu visibility, navigation, URL validation         |
| PIM/Employee   | `EmployeeTests`      | 9          | Employee list, add, search, cancel, reset           |
| Admin          | `AdminTests`         | 6          | User table, search, reset, add user navigation      |
| Leave          | `LeaveTests`         | 6          | Leave list, apply leave, search, reset, validation  |
| Recruitment    | `RecruitmentTests`   | 7          | Vacancies, candidates, search, reset, add vacancy   |
| My Info        | `MyInfoTests`        | 7          | Personal details, field pre-population, tabs        |
| Time           | `TimeTests`          | 6          | Timesheets, Punch In/Out, attendance records        |
| Directory      | `DirectoryTests`     | 6          | Cards, name search, reset, no-records               |
| Performance    | `PerformanceTests`   | 7          | Manage Reviews, My Reviews, KPIs, Trackers          |
| **Total**      |                      | **71**     |                                                     |

---

## 📦 Prerequisites

- **Java JDK 17+** – [Download](https://adoptium.net/)
- **Apache Maven 3.9+** – [Download](https://maven.apache.org/)
- **Google Chrome / Firefox / Edge** (latest stable)
- **Git**
- (Optional) **IntelliJ IDEA** or **Eclipse**

---

## 🚀 Setup & Installation

```bash
# 1. Clone the repository
git clone https://github.com/soumyaranjansahoo5/OrangeHRM-Web-Portal-Selenium-Automation-Framework.git

# 2. Navigate into the project
cd OrangeHRM-Web-Portal-Selenium-Automation-Framework

# 3. Install dependencies (downloads drivers automatically via WebDriverManager)
mvn clean install -DskipTests
```

---

## ⚙️ Configuration

All settings live in `src/test/resources/config.properties`:

```properties
# Application
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
admin.username=Admin
admin.password=admin123

# Browser: chrome | firefox | edge
browser=chrome

# Headless mode
headless=false

# Timeouts (seconds)
implicit.wait=10
explicit.wait=15
page.load.timeout=30

# Retry on failure
retry.count=1
```

> **CI Override**: Any property can be overridden at runtime via `-D` system properties:
> ```bash
> mvn test -Dbrowser=firefox -Dheadless=true
> ```

---

## ▶️ Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Headless (CI mode)
```bash
mvn clean test -Dheadless=true
```

### Run on a Specific Browser
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Run a Specific Test Class
```bash
mvn clean test -Dtest=LoginTests
mvn clean test -Dtest=EmployeeTests
mvn clean test -Dtest=AdminTests
```

### Run Multiple Classes
```bash
mvn clean test -Dtest="LoginTests,DashboardTests,EmployeeTests"
```

---

## 📊 Reports

After every run, an **ExtentReports HTML report** is auto-generated in the `reports/` directory:

```
reports/
├── OrangeHRM_Report_20260607_103045.html   ← Open in any browser
└── screenshots/
    └── tc_login_002_invalidUsername_20260607_103120.png
```

The report includes:
- Pass / Fail / Skip status per test
- Step-by-step log messages
- Embedded screenshots on failure
- System info (Browser, OS, Java version, App URL)

---

## 🔄 CI/CD Pipeline

The GitHub Actions workflow (`.github/workflows/ci.yml`) triggers on:
- Push to `main` or `develop`
- Pull requests to `main`
- Nightly schedule (02:00 UTC)
- Manual dispatch (choose browser)

Pipeline steps:
1. Checkout code
2. Set up JDK 17
3. Install Chrome
4. Cache Maven dependencies
5. Run tests in headless mode
6. Upload HTML report artifact
7. Upload failure screenshots
8. Upload logs
9. Publish TestNG XML summary

---

## 🏛 Framework Architecture

```
BaseTest (setup/teardown + ExtentReports)
    └── Test Classes (LoginTests, EmployeeTests, ...)
            └── Page Objects (LoginPage, EmployeeListPage, ...)
                    └── BasePage (all reusable WebDriver actions)
                            └── DriverManager (ThreadLocal WebDriver)
                                    └── ConfigReader (config.properties)
```

**Key design decisions:**
- **ThreadLocal WebDriver** – supports parallel test execution without thread conflicts
- **Singleton ConfigReader** – single load of properties, system-property override for CI
- **Fluent Page Object API** – method chaining for readable test steps
- **ExtentReports + TestListener** – screenshots and status logged automatically on failure
- **WebDriverManager** – zero manual driver binary management

---

## 👤 Author

**Soumyaranjan Sahoo**  
Automation Test Engineer | Qatasys, Hyderabad  
📧 soumyaranjansahoo6530@gmail.com  
🔗 [LinkedIn](https://www.linkedin.com/in/soumyaranjansahoo7321) | [GitHub](https://github.com/soumyaranjansahoo5)

---

> **Application Under Test:**  
> OrangeHRM Demo Portal — https://opensource-demo.orangehrmlive.com  
> Default credentials: `Admin` / `admin123`

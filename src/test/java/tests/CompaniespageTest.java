package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.Assert;

import pages.Companiespage;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utils.DriverManager;

import java.util.List;
import java.util.Map;

public class CompaniespageTest {

    WebDriver driver;
    Companiespage companiesPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-reports/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        driver = DriverManager.getDriver();
        companiesPage = new Companiespage(driver);
    }

    @BeforeMethod
    public void setUp() {
        try {
            driver = DriverManager.getDriver();

            // Init page object
            companiesPage = new Companiespage(driver);

            // Navigate to companies page
            driver.get("https://qa.atgtest.com/Operations/Companies");

            // If login page visible, login automatically
            //if (companiesPage.isLoginFormVisible()) {
            //    companiesPage.login("your_username", "your_password");
            //}

            // Re-initialize page object after login/navigation
            companiesPage = new Companiespage(driver);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Setup failed: " + e.getMessage());
        }
    }

    @Test
    public void testSearchAndAccessActiveCustomers() {
        test = extent.createTest("Search company and access Active Customers");

        String companyName = "America To Go";

        companiesPage.enterSearchTerm(companyName);
        companiesPage.waitForSearchResults(companyName);
        companiesPage.clickThreeDotMenu(companyName);
        companiesPage.selectActiveCustomers();

        String url = companiesPage.getCurrentUrl();
        Assert.assertTrue(url.contains("ActiveCustomers"), "Navigation to Active Customers failed");
        test.pass("Successfully landed on Active Customers page: " + url);
    }

    @Test
    public void testActiveCustomersTableContent() {
        test = extent.createTest("Validate Active Customers table for America To Go");

        String companyName = "America To Go";

        companiesPage.enterSearchTerm(companyName);
        companiesPage.waitForSearchResults(companyName);
        companiesPage.clickThreeDotMenu(companyName);
        companiesPage.selectActiveCustomers();

        List<String> customerNames = companiesPage.getActiveCustomerNames();
        Assert.assertTrue(customerNames.contains("Chow Town - American U"), "Expected customer not found");
        test.pass("Verified Active Customers table contents");
    }

    @Test
    public void testValidateCompanyRowData() {
        test = extent.createTest("Validate row data after navigating to Active Customers");

        String companyName = "William And Mary";

        companiesPage.enterSearchTerm(companyName);
        companiesPage.waitForSearchResults(companyName);
        companiesPage.clickThreeDotMenu(companyName);
        companiesPage.selectActiveCustomers();

        Map<String, String> rowData = companiesPage.getRowData(companyName);

        Assert.assertEquals(rowData.get("Ordering Dashboard"), "Default Ordering Dashboard");
        Assert.assertTrue(rowData.get("ATG Internal Note").startsWith("Revisions above 20%"), "Unexpected note content");
        Assert.assertEquals(rowData.get("Active"), "false");
        Assert.assertEquals(rowData.get("Is Online"), "false");

        test.pass("Row details verified for: " + companyName);
    }

    @AfterMethod
    public void tearDown() {
        // Optional: driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void flushExtentReport() {
        extent.flush();
    }
}
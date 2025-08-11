package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pages.OperationsPage;
import utils.DriverManager;

import java.lang.reflect.Method;

public class OperationspageTest {
    WebDriver driver;
    OperationsPage operationsPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-reports/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        driver = DriverManager.getDriver();
        operationsPage = new OperationsPage(driver);
    }

    @BeforeMethod
    public void resetToOperationsPage(Method method) {
        operationsPage.returnToOperationsMainPage();
    }
@Test(priority = 1)
    public void testPageHeaders() {
        test = extent.createTest("Verify H1 and H4 headers on Operations page");

        operationsPage.returnToOperationsMainPage();

        String h1Text = operationsPage.getH1Text();
        String h4Text = operationsPage.getH4Text();

        Assert.assertFalse(h1Text.isEmpty(), "H1 header is missing or empty");
        Assert.assertFalse(h4Text.isEmpty(), "H4 header is missing or empty");

        test.pass("H1 header found: " + h1Text);
        test.pass("H4 header found: " + h4Text);
    }
    @Test(priority = 2)
    public void testCardTextClickAndRedirect() {
        test = extent.createTest("Verify card label, click functionality, and redirect URL");

        // Common wait before starting
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[][] cardData = {
            {"ChatATG", "/Operations/ChatATG"},
            {"Companies", "/Operations/Companies"},
            {"Cuisines", "/Operations/Cuisines"},
            {"Customers", "/Operations/Customers"},
            {"Dashboards", "/Operations/Dashboards"},
            {"Designations", "/Operations/Designations"},
            {"Holidays", "/Operations/Holidays"},
            {"Initiatives", "/Operations/Initiatives"},
            {"Invoicing", "/Operations/Invoicing"},
            {"JwtBearerToken", "/Operations/JwtBearerToken"},
            {"Mgmt Dashboard", "/Operations/ManagementDashboard"},
            {"Onboarding", "/Operations/Onboarding"},
            {"Operator Groups", "/Operations/OperatorGroups"},
            {"Operators", "/Operations/Operators"},
            {"Order Tracker", "/Operations/OrderTracker"},
            {"Outgoing Mail", "/Operations/OutgoingMail"},
            {"Reporting", "/Operations/Reporting"},
            {"Settings", "/Operations/Settings"},
            {"Sites", "/Operations/Sites"},
            {"Special Charges", "/Operations/SpecialCharges"},
            {"Support Links", "/Operations/SupportLinks"},
            {"Support Q&A", "/Operations/SupportAnswers"},
            {"Themes", "/Operations/Themes"},
            {"Timesheet", "/Operations/OperatorLogins"},
            {"Users", "/Operations/Users"},
            {"Vendor Lists", "/Operations/VendorLists"},
            {"Vendors", "/Operations/Vendors"},
            {"Workflows", "/Operations/WorkflowItems"}
        };

        for (String[] card : cardData) {
            String cardName = card[0];
            String expectedUrlFragment = card[1];

            operationsPage.returnToOperationsMainPage();

            boolean labelVisible = operationsPage.verifyCardLabel(cardName);
            Assert.assertTrue(labelVisible, "Label not visible for card: " + cardName);

            boolean clickSuccess = operationsPage.clickCard(cardName);
            Assert.assertTrue(clickSuccess, "Click failed for card: " + cardName);

            String currentUrl = operationsPage.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains(expectedUrlFragment),
                    "Redirect failed for card: " + cardName + ". Expected URL to contain '" + expectedUrlFragment + "', but got '" + currentUrl + "'");

            test.pass("Card '" + cardName + "' clicked and redirected to: " + currentUrl);
        }
    }

    

    @AfterClass
    public void tearDown() {
        extent.flush();
        // DriverManager.quitDriver(); // Uncomment if needed
    }
}
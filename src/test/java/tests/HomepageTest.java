package tests;

import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import utils.DriverManager;
import pages.Homepage;
import pages.SignupPage;
import pages.PreSigninPage;
import pages.FooterPage;

public class HomepageTest {
    private ExtentReports extent;
    private ExtentTest test;
    private WebDriver driver;
    private WebDriverWait wait;
    private Homepage homepage;

    @BeforeClass
    public void setUp() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-reports/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testMenusAndLinks() {
        test = extent.createTest("Testing Pre-Signin and Post-Signin Menus and Links");

        PreSigninPage preSigninPage = new PreSigninPage(driver, wait);
        preSigninPage.openSite("https://qa.atgtest.com/");
        preSigninPage.dismissCookiePopupIfPresent();
        preSigninPage.verifyPreSigninElements();
        preSigninPage.clickSignupLink();

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("andres.correal@americatogo.com", "We the p3ople");

        homepage = new Homepage(driver, wait, test);
        homepage.verifyMenuNavigation();
        homepage.verifyCTAButtons();
        homepage.verifyACDropdownLinks();

        // ✅ Footer test BEFORE OPERATIONS
        FooterPage footerPage = new FooterPage(driver);
        footerPage.verifyFooterElements();

        // Final step: go to OPERATIONS
        homepage.verifySingleMenu("OPERATIONS", "https://qa.atgtest.com/Operations", false);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
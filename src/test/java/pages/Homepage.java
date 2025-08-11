package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;

public class Homepage {
    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;
    private Actions actions;

    private By acIcon = By.id("accountLink");

    public Homepage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
        this.actions = new Actions(driver);
    }

    public void verifyMenuNavigation() {
        String[][] menuLinks = {
            {"INSTITUTIONS", "https://qa.atgtest.com/Home/Institutions"},
            {"CATERERS", "https://qa.atgtest.com/Home/Caterers"},
            {"CONTACT US", "https://qa.atgtest.com/Home/Contact"},
            {"ORDER", "https://qa.atgtest.com/Ordering"},
            {"VENDOR PORTAL", "https://qa.atgtest.com/VendorPortal"},
            {"CLIENT PORTAL", "https://qa.atgtest.com/ClientPortal"}
        };

        for (String[] menu : menuLinks) {
            verifySingleMenu(menu[0], menu[1], true);
        }
    }

    public void verifySingleMenu(String menuText, String expectedUrl, boolean goBackAfter) {
        try {
            By locator = By.linkText(menuText);
            actions.moveToElement(driver.findElement(locator)).perform();
            driver.findElement(locator).click();
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
            test.pass(menuText + " navigated correctly");

            if (goBackAfter) {
                driver.navigate().to("https://qa.atgtest.com/");
                wait.until(ExpectedConditions.presenceOfElementLocated(acIcon));
            }
        } catch (Exception e) {
            test.fail("Menu navigation failed: " + menuText + " - " + e.getMessage());
            Assert.fail("Exception in menu: " + menuText, e);
        }
    }

    public void verifyCTAButtons() {
        String[][] ctas = {
            {"Solutions for Institutions", "https://qa.atgtest.com/Home/Institutions"},
            {"Solutions for Caterers and Restaurants", "https://qa.atgtest.com/Home/Caterers"},
            {"Contact Us", "https://qa.atgtest.com/Home/Contact"}
        };

        for (String[] cta : ctas) {
            try {
                By locator = By.linkText(cta[0]);
                actions.moveToElement(driver.findElement(locator)).perform();
                driver.findElement(locator).click();
                wait.until(ExpectedConditions.urlToBe(cta[1]));
                Assert.assertEquals(driver.getCurrentUrl(), cta[1]);
                test.pass(cta[0] + " CTA navigated correctly");

                driver.navigate().to("https://qa.atgtest.com/");
                wait.until(ExpectedConditions.presenceOfElementLocated(acIcon));
            } catch (Exception e) {
                test.fail("CTA failed: " + cta[0] + " - " + e.getMessage());
                Assert.fail("CTA exception: " + cta[0], e);
            }
        }
    }

    public void verifyACDropdownLinks() {
        String[][] modalLinks = {
            {"PROFILE", "https://qa.atgtest.com/Ordering/Profile"},
            {"MY ORDERS", "https://qa.atgtest.com/Ordering/MyOrders"},
            {"ORDER", "https://qa.atgtest.com/Ordering"},
            {"VENDOR PORTAL", "https://qa.atgtest.com/VendorPortal"},
            {"CLIENT PORTAL", "https://qa.atgtest.com/ClientPortal"},
            {"CHANGE PASSWORD", "https://qa.atgtest.com/Home/ChangePassword"}
        };

        try {
            WebElement avatar = wait.until(ExpectedConditions.elementToBeClickable(acIcon));
            avatar.click();

            for (String[] modal : modalLinks) {
                By linkLocator = By.linkText(modal[0]);
                WebElement modalLink = wait.until(ExpectedConditions.elementToBeClickable(linkLocator));
                modalLink.click();
                wait.until(ExpectedConditions.urlToBe(modal[1]));
                Assert.assertEquals(driver.getCurrentUrl(), modal[1]);
                test.pass(modal[0] + " modal link navigated correctly");

                driver.navigate().to("https://qa.atgtest.com/");
                wait.until(ExpectedConditions.presenceOfElementLocated(acIcon));
                avatar = wait.until(ExpectedConditions.elementToBeClickable(acIcon));
                avatar.click();
            }
        } catch (Exception e) {
            test.fail("AC dropdown failed: " + e.getMessage());
            Assert.fail("AC dropdown exception", e);
        }
    }
}


package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Duration;

public class FooterPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public FooterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void verifyFooterElements() {
        System.out.println("Starting footer verification...");
        scrollToFooter();
        verifyOfficeDetails();
        verifyResourceLinks();
        verifyPolicyLinks();
        verifyCopyright();
        System.out.println("Footer verification completed.");
    }

    private void scrollToFooter() {
        System.out.println("Scrolling to footer...");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
    }

    private void verifyOfficeDetails() {
        System.out.println("Verifying office details...");
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));

        //WebElement officeHeader = footer.findElement(By.cssSelector(".headerdescription"));
        WebElement addressLine1 = footer.findElement(By.xpath(".//div[text()='641 Lexington Avenue']"));
        WebElement addressLine2 = footer.findElement(By.xpath(".//div[text()='New York, NY 10022']"));

        System.out.println("Office Header: " + officeHeader.getText());
        //Assert.assertEquals(officeHeader.getText().trim(), "NEW YORK CITY", "Office header mismatch");
        Assert.assertTrue(addressLine1.isDisplayed(), "Address line 1 not visible");
        Assert.assertTrue(addressLine2.isDisplayed(), "Address line 2 not visible");
    }

    private void verifyResourceLinks() {
        System.out.println("Verifying resource links...");
        WebElement contactLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer//a[@href='/Home/Contact']")));
        WebElement emailLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer//a[contains(@href,'mailto:info@americatogo.com')]")));
        WebElement linkedinLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer//a[contains(@href,'linkedin.com')]")));

        System.out.println("Contact Link: " + contactLink.getText());
        System.out.println("Email Link: " + emailLink.getAttribute("href"));
        System.out.println("LinkedIn Link: " + linkedinLink.getAttribute("href"));

        Assert.assertTrue(contactLink.isDisplayed(), "Contact link not visible");
        Assert.assertTrue(emailLink.isDisplayed(), "Email link not visible");
        Assert.assertTrue(linkedinLink.isDisplayed(), "LinkedIn link not visible");
    }

    private void verifyPolicyLinks() {
        System.out.println("Verifying policy links...");
        WebElement privacyLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer//a[@href='/Home/PrivacyPolicy']")));
        WebElement cookiesLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer//a[@href='/Home/CookiesPolicy']")));

        System.out.println("Privacy Policy Link: " + privacyLink.getText());
        System.out.println("Cookies Policy Link: " + cookiesLink.getText());

        Assert.assertTrue(privacyLink.isDisplayed(), "Privacy policy link not visible");
        Assert.assertTrue(cookiesLink.isDisplayed(), "Cookies policy link not visible");
    }

    private void verifyCopyright() {
        System.out.println("© Verifying copyright...");
        WebElement copyright = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//footer//div[contains(text(),'Copyright © 2021 America To Go')]")
            )
        );
        System.out.println("Copyright Text: " + copyright.getText());
        Assert.assertTrue(copyright.isDisplayed(), "Copyright not visible");
    }
}

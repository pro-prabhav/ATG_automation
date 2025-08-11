package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public class PreSigninPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By logo = By.cssSelector("a.logo");
    private By header = By.tagName("h1");
    private By tagline = By.xpath("//headerdescription");
    private By contentBlock = By.tagName("h2");
    private By signupLink = By.linkText("SIGN IN");
    private By cookieButton = By.cssSelector("#cookieConsentContainer .cookieButton button");
    private By cookieContainer = By.id("cookieConsentContainer");

    public PreSigninPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openSite(String url) {
        driver.get(url);
    }

    public void dismissCookiePopupIfPresent() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            button.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieContainer));
        } catch (Exception ignored) {}
    }

    public void verifyPreSigninElements() {
        verifyLogo();
        verifyHeaderText();
        verifyTagline();
        verifyContentBelowHeader();
        verifyPreSignupPageLinks();
    }

    public void verifyLogo() {
        WebElement logoLink = wait.until(ExpectedConditions.visibilityOfElementLocated(logo));
        Assert.assertTrue(logoLink.isDisplayed(), "Logo is not visible");
        Assert.assertEquals(logoLink.getAttribute("aria-label"), "America To Go Home Page", "Logo aria-label mismatch");
    }

    public void verifyHeaderText() {
        WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        Assert.assertEquals(headerElement.getText().trim(), "CATERING MANAGEMENT", "Header text mismatch");
    }

   public void verifyTagline() {
    WebElement taglineElement = driver.findElement(tagline);
    Assert.assertTrue(
        taglineElement.getText().contains("HELPING UNIVERSITIES, COMPANIES AND OTHER INSTITUTIONS MANAGE THEIR CATERING SPEND"),
        "Tagline content mismatch"
    );
}


    public void verifyContentBelowHeader() {
        WebElement contentElement = driver.findElement(contentBlock);
        Assert.assertTrue(contentElement.getText().contains("America To Go is a leading catering management solution"), "Content block mismatch");
    }

    public void verifyPreSignupPageLinks() {
        Map<String, String> preSignupLinks = new LinkedHashMap<>();
        preSignupLinks.put("INSTITUTIONS", "https://qa.atgtest.com/Home/Institutions");
        preSignupLinks.put("CATERERS", "https://qa.atgtest.com/Home/Caterers");
        preSignupLinks.put("CONTACT US", "https://qa.atgtest.com/Home/Contact");
        preSignupLinks.put("SIGN IN", "https://qa.atgtest.com/Home/SignIn");

        for (Map.Entry<String, String> entry : preSignupLinks.entrySet()) {
            String linkText = entry.getKey();
            String expectedUrl = entry.getValue();

            try {
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
                link.click();
                wait.until(ExpectedConditions.urlToBe(expectedUrl));
                Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "URL mismatch for link: " + linkText);

                if (!linkText.equalsIgnoreCase("SIGN IN")) {
                    driver.navigate().back();
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
                }
            } catch (Exception e) {
                Assert.fail("Failed to verify link: " + linkText, e);
            }
        }
    }

    public void clickSignupLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(signupLink));
        link.click();
    }
}





package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class OperationsPage {
    WebDriver driver;
    private WebDriverWait wait;

    public OperationsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void returnToOperationsMainPage() {
        driver.get("https://qa.atgtest.com/Operations");
        wait.until(ExpectedConditions.urlContains("/Operations"));
    }

    public boolean verifyCardLabel(String cardName) {
        try {
            String xpath = "//div[contains(@class,'sectionCard')]//div[@class='title' and normalize-space()='" + cardName + "']";
            WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return label.isDisplayed();
        } catch (Exception e) {
            System.out.println("Label not visible for card: " + cardName + " - " + e.getMessage());
            return false;
        }
    }

    public boolean clickCard(String cardName) {
    try {
        // Locate the card title element directly
        String titleXpath = "//div[contains(@class,'sectionCard')]//div[@class='title' and normalize-space()='" + cardName + "']";
        WebElement titleElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(titleXpath)));
        titleElement.click();
        return true;
    } catch (Exception e) {
        System.out.println("Click failed for card: " + cardName + " - " + e.getMessage());
        return false;
    }
}


    public String getH1Text() {
        try {
            WebElement h1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            return h1.getText().trim();
        } catch (Exception e) {
            System.out.println("H1 not found: " + e.getMessage());
            return "";
        }
    }

    public String getH4Text() {
        try {
            WebElement h4 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h4")));
            return h4.getText().trim();
        } catch (Exception e) {
            System.out.println("H4 not found: " + e.getMessage());
            return "";
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
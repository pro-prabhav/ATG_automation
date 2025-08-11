package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage {
    private WebDriver driver;

    private By emailField = By.id("Email");
    private By passwordField = By.id("Password");
    private By signupButton = By.cssSelector("button[type='submit']");

    public SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    public void signup(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(signupButton).click();
    }
}






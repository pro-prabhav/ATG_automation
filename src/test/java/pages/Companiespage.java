package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
//import org.openqa.selenium.NoSuchElementException; 

import java.time.Duration;
import java.util.*;

public class Companiespage {

    WebDriver driver;
    WebDriverWait wait;

    public Companiespage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#dataGridContainer > div > div:nth-child(1) > div.dx-widget.full-screen.dx-visibility-change-handler > div > div.dx-datagrid-header-panel > div > div > div.dx-toolbar-center > div:nth-child(2)")
        ));
        return titleElement.getText().trim();
    }

    @FindBy(xpath = "//input[contains(@placeholder, ' Search')]")
    WebElement searchBar;

    public void waitForSearchInput() {
        wait.until(ExpectedConditions.visibilityOf(searchBar));
    }

    public void enterSearchTerm(String searchText) {
        waitForSearchInput();
        searchBar.clear();
        searchBar.sendKeys(searchText);
    }

    public void waitForSearchResults(String companyName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//td[text()='" + companyName + "']")));
    }

    public void clickThreeDotMenu(String companyName) {
        WebElement menuBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//td[text()='" + companyName + "']/following-sibling::td//button[contains(@class,'menu-button')]")));
        menuBtn.click();
    }

    public void selectActiveCustomers() {
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[text()='Active Customers']")));
        option.click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public List<String> getActiveCustomerNames() {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.xpath("//table//tr/td[1]")));

        List<String> names = new ArrayList<>();
        for (WebElement row : rows) {
            names.add(row.getText().trim());
        }
        return names;
    }

    public Map<String, String> getRowData(String companyName) {
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//td[text()='" + companyName + "']/parent::tr")));

        Map<String, String> rowData = new HashMap<>();
        rowData.put("Ordering Dashboard", row.findElement(By.xpath("./td[3]")).getText().trim());
        rowData.put("ATG Internal Note", row.findElement(By.xpath("./td[4]")).getText().trim());
        rowData.put("Active", row.findElement(By.xpath("./td[5]//input")).isSelected() ? "true" : "false");
        rowData.put("Is Online", row.findElement(By.xpath("./td[6]//input")).isSelected() ? "true" : "false");

        return rowData;
    }

    // ------------- New: Login form locators ----------------
    @FindBy(id = "username")
    WebElement usernameInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "loginButton")
    WebElement loginButton;

 /*    // Check if the login form is visible (exists & displayed)
    public boolean isLoginFormVisible() {
        try {
            return usernameInput.isDisplayed() && passwordInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    } */

    // Perform login
    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.clear();
        usernameInput.sendKeys(username);

        passwordInput.clear();
        passwordInput.sendKeys(password);

        loginButton.click();

        // Wait until redirected or form disappears
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("/Operations/Companies"),
            ExpectedConditions.invisibilityOf(loginButton)
        ));
    }
}
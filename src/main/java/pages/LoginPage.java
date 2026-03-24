package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        enterText(usernameInput, username, "Username");
        logger.info("Entered username: {}", username);
    }

    public void enterPassword(String password) {
        enterText(passwordInput, password, "Password");
        logger.info("Entered password");
    }

    public void clickLoginButton() {
        clickElement(loginButton, "Login Button");
        logger.info("Clicked login button");
    }

    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logger.info("Login attempt with username: {}", username);
        return new ProductsPage(driver);
    }

    public String getErrorMessage() {
        String error = getElementText(errorMessage, "Error Message");
        logger.info("Error message displayed: {}", error);
        return error;
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage, "Error Message");
    }
}
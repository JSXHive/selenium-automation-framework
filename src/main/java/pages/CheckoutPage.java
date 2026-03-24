package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckoutPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CheckoutPage.class);

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(className = "title")
    private WebElement pageTitle;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getElementText(pageTitle, "Checkout Page Title");
    }

    public boolean isCheckoutPageDisplayed() {
        return isElementDisplayed(pageTitle, "Checkout Page");
    }

    public void enterFirstName(String firstName) {
        enterText(firstNameInput, firstName, "First Name");
        logger.info("Entered first name: {}", firstName);
    }

    public void enterLastName(String lastName) {
        enterText(lastNameInput, lastName, "Last Name");
        logger.info("Entered last name: {}", lastName);
    }

    public void enterPostalCode(String postalCode) {
        enterText(postalCodeInput, postalCode, "Postal Code");
        logger.info("Entered postal code: {}", postalCode);
    }

    public CheckoutOverviewPage clickContinue() {
        clickElement(continueButton, "Continue Button");
        logger.info("Clicked continue button");
        return new CheckoutOverviewPage(driver);
    }

    public CartPage clickCancel() {
        clickElement(cancelButton, "Cancel Button");
        logger.info("Clicked cancel button");
        return new CartPage(driver);
    }

    public CheckoutOverviewPage enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return clickContinue();
    }
}
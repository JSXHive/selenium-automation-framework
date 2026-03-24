package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckoutCompletePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CheckoutCompletePage.class);

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "complete-text")
    private WebElement completeText;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getElementText(pageTitle, "Complete Page Title");
    }

    public String getCompleteHeader() {
        return getElementText(completeHeader, "Complete Header");
    }

    public String getCompleteText() {
        return getElementText(completeText, "Complete Text");
    }

    public boolean isOrderComplete() {
        return isElementDisplayed(completeHeader, "Order Complete");
    }

    public ProductsPage backToHome() {
        clickElement(backHomeButton, "Back Home Button");
        logger.info("Navigated back to products page");
        return new ProductsPage(driver);
    }
}
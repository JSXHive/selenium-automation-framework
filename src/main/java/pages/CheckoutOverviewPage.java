package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckoutOverviewPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CheckoutOverviewPage.class);

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getElementText(pageTitle, "Overview Page Title");
    }

    public boolean isOverviewPageDisplayed() {
        return isElementDisplayed(pageTitle, "Overview Page");
    }

    public CheckoutCompletePage clickFinish() {
        clickElement(finishButton, "Finish Button");
        logger.info("Clicked finish button");
        return new CheckoutCompletePage(driver);
    }

    public ProductsPage clickCancel() {
        clickElement(cancelButton, "Cancel Button");
        logger.info("Clicked cancel button");
        return new ProductsPage(driver);
    }

    public String getTotalAmount() {
        return getElementText(totalLabel, "Total Amount");
    }

    public String getSubtotal() {
        return getElementText(subtotalLabel, "Subtotal");
    }

    public String getTax() {
        return getElementText(taxLabel, "Tax");
    }
}
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CartPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CartPage.class);

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_button")
    private List<WebElement> removeButtons;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getElementText(pageTitle, "Cart Page Title");
    }

    public boolean isCartPageDisplayed() {
        return isElementDisplayed(pageTitle, "Cart Page");
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public void removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            clickElement(removeButtons.get(0), "Remove Button");
            logger.info("Removed first item from cart");
        }
    }

    public void removeItem(String productName) {
        for (WebElement item : cartItems) {
            if (item.getText().contains(productName)) {
                WebElement removeButton = item.findElement(org.openqa.selenium.By.cssSelector(".cart_button"));
                clickElement(removeButton, "Remove Button for " + productName);
                logger.info("Removed item from cart: {}", productName);
                break;
            }
        }
    }

    public CheckoutPage clickCheckout() {
        clickElement(checkoutButton, "Checkout Button");
        logger.info("Clicked checkout button");
        return new CheckoutPage(driver);
    }

    public ProductsPage continueShopping() {
        clickElement(continueShoppingButton, "Continue Shopping Button");
        logger.info("Clicked continue shopping button");
        return new ProductsPage(driver);
    }
}
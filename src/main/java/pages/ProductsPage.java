package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProductsPage.class);

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(className = "inventory_item")
    private List<WebElement> products;

    @FindBy(css = ".btn_inventory")
    private List<WebElement> addToCartButtons;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getElementText(pageTitle, "Page Title");
    }

    public boolean isProductsPageDisplayed() {
        return isElementDisplayed(pageTitle, "Products Page");
    }

    public void addProductToCart(String productName) {
        for (WebElement product : products) {
            if (product.getText().contains(productName)) {
                WebElement addButton = product.findElement(org.openqa.selenium.By.cssSelector(".btn_inventory"));
                clickElement(addButton, "Add to Cart Button for " + productName);
                logger.info("Added product to cart: {}", productName);
                break;
            }
        }
    }

    public void addFirstProductToCart() {
        if (!addToCartButtons.isEmpty()) {
            clickElement(addToCartButtons.get(0), "First Add to Cart Button");
            logger.info("Added first product to cart");
        }
    }

    public int getCartItemCount() {
        if (isElementDisplayed(cartBadge, "Cart Badge")) {
            int count = Integer.parseInt(getElementText(cartBadge, "Cart Count"));
            logger.info("Cart item count: {}", count);
            return count;
        }
        return 0;
    }

    public CartPage goToCart() {
        clickElement(cartIcon, "Cart Icon");
        logger.info("Navigated to cart");
        return new CartPage(driver);
    }

    public void logout() {
        clickElement(menuButton, "Menu Button");
        clickElement(logoutLink, "Logout Link");
        logger.info("Logged out successfully");
    }
}
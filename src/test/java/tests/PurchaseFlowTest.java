package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utilities.RetryAnalyzer;

public class PurchaseFlowTest extends BaseTest {

    @Test(description = "Complete end-to-end purchase flow", retryAnalyzer = RetryAnalyzer.class)
    public void testCompletePurchaseFlow() {
        // Login
        LoginPage loginPage = new LoginPage(getDriver());
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Login successful");
        
        // Add product to cart
        productsPage.addFirstProductToCart();
        Assert.assertEquals(productsPage.getCartItemCount(), 1, 
                           "Cart should have 1 item");
        
        // Go to cart
        CartPage cartPage = productsPage.goToCart();
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page displayed");
        Assert.assertEquals(cartPage.getCartItemCount(), 1, 
                           "Cart should have 1 item");
        
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.clickCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), 
                         "Checkout page displayed");
        
        // Enter checkout information
        CheckoutOverviewPage overviewPage = checkoutPage.enterCheckoutInformation(
            "John", "Doe", "12345");
        Assert.assertTrue(overviewPage.isOverviewPageDisplayed(), 
                         "Overview page displayed");
        
        // Complete purchase
        CheckoutCompletePage completePage = overviewPage.clickFinish();
        Assert.assertTrue(completePage.isOrderComplete(), 
                         "Order should be completed");
        Assert.assertEquals(completePage.getCompleteHeader(), 
                           "Thank you for your order!",
                           "Thank you message should be displayed");
    }

    @Test(description = "Verify product removal from cart")
    public void testRemoveFromCart() {
        // Login
        LoginPage loginPage = new LoginPage(getDriver());
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        
        // Add and remove product
        productsPage.addFirstProductToCart();
        Assert.assertEquals(productsPage.getCartItemCount(), 1, 
                           "Cart should have 1 item");
        
        CartPage cartPage = productsPage.goToCart();
        cartPage.removeFirstItem();
        
        Assert.assertEquals(cartPage.getCartItemCount(), 0, 
                           "Cart should be empty after removal");
    }
}
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.RetryAnalyzer;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login with valid credentials", retryAnalyzer = RetryAnalyzer.class)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), 
                         "Products page should be displayed after successful login");
        Assert.assertEquals(productsPage.getPageTitle(), "Products", 
                           "Page title should be 'Products'");
    }

    @Test(description = "Verify login with locked out user")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("locked_out_user", "secret_sauce");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
                         "Error message should be displayed for locked out user");
        Assert.assertEquals(loginPage.getErrorMessage(), 
                           "Epic sadface: Sorry, this user has been locked out.",
                           "Error message should indicate user is locked out");
    }

    @Test(description = "Verify login with invalid credentials")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("invalid_user", "wrong_password");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
                         "Error message should be displayed for invalid credentials");
        Assert.assertEquals(loginPage.getErrorMessage(), 
                           "Epic sadface: Username and password do not match any user in this service",
                           "Error message should indicate invalid credentials");
    }
}
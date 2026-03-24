package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.ExcelUtil;

import java.util.List;
import java.util.Map;

public class DataDrivenLoginTest extends BaseTest {

    @DataProvider(name = "loginDataFromExcel")
    public Object[][] getLoginDataFromExcel() {
        List<Map<String, String>> testData = ExcelUtil.getTestData(
            "src/test/resources/testdata/test-data.xlsx", "LoginData");
        
        Object[][] data = new Object[testData.size()][2];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i).get("username");
            data[i][1] = testData.get(i).get("password");
        }
        return data;
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"standard_user", "secret_sauce", true},
            {"locked_out_user", "secret_sauce", false},
            {"problem_user", "secret_sauce", true},
            {"performance_glitch_user", "secret_sauce", true}
        };
    }

    @Test(dataProvider = "loginData", description = "Data driven login test")
    public void testMultipleLogins(String username, String password, boolean shouldSucceed) {
        LoginPage loginPage = new LoginPage(getDriver());
        ProductsPage productsPage = loginPage.login(username, password);
        
        if (shouldSucceed) {
            Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                             "Login should succeed for: " + username);
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                             "Login should fail for: " + username);
        }
    }
}
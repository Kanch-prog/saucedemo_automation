package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SauceDemoTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Kanchana/untitled/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @Test(priority = 1)
    public void verifyLoginPageAndLogo() {
        WebElement logo = driver.findElement(By.cssSelector("div[class=\"login_logo\"]"));
        Assert.assertTrue(logo.isDisplayed(), "Swag Labs logo is not displayed on the login page.");
    }

    @Test(priority = 2)
    public void loginWithValidCredentials() {
        driver.findElement(By.cssSelector("input[id=\"user-name\"]")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("input[id=\"password\"]")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("input[id=\"login-button\"]")).click();

        WebElement inventoryPageTitle = driver.findElement(By.className("title"));
        Assert.assertEquals(inventoryPageTitle.getText(), "Products", "Inventory page is not displayed.");
    }

    @Test(priority = 3)
    public void verifyProductLinkIsClickable() {
        // Wait until the product link is clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("item_5_title_link")));

        // Assert that the product link is clickable
        Assert.assertTrue(productLink.isDisplayed() && productLink.isEnabled(), "Product link is not clickable.");
    }

    @Test(priority = 4)
    public void navigateToProductDetailPage() {
        // Click on the product with a specific ID
        WebElement productLink = driver.findElement(By.id("item_5_title_link"));
        productLink.click();

        // Wait for the product detail name to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productDetailName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-test='inventory-item-name']")));

        // Verify the product name
        Assert.assertEquals(productDetailName.getText(), "Sauce Labs Fleece Jacket", "Product name does not match.");
    }

    @Test(priority = 5)
    public void navigateBackToInventory() {
        // Navigate back to the inventory page
        driver.navigate().back();

        // Wait for the inventory page title to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inventoryPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));

        // Verify the inventory page title
        Assert.assertEquals(inventoryPageTitle.getText(), "Products", "Inventory page is not displayed.");

        // Verify that inventory items are displayed
        //Assert.assertTrue(driver.findElements(By.cssSelector("div[data-test='inventory_item']")).size() > 0, "Inventory items are not displayed.");
    }


    @Test(priority = 6)
    public void addItemToCart() {
        // Find and add the item to the cart
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='add-to-cart-sauce-labs-fleece-jacket']")));

        // Click the add to cart button
        addToCartButton.click();

        // Wait for the button to be updated to 'Remove'
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("button[data-test='remove-sauce-labs-fleece-jacket']"),
                "Remove"
        ));

        // Locate the button again after the DOM update
        WebElement removeButton = driver.findElement(By.cssSelector("button[data-test='remove-sauce-labs-fleece-jacket']"));

        // Verify the button has changed to 'Remove'
        Assert.assertEquals(removeButton.getText(), "Remove", "Add to cart button did not change to 'Remove'.");
    }


    @Test(priority = 7)
    public void verifyItemInCart() {
        // Navigate to the shopping cart page
        driver.findElement(By.className("shopping_cart_link")).click();

        // Wait for the item to be visible in the cart
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));

        // Verify the item in the cart
        Assert.assertEquals(cartItem.getText(), "Sauce Labs Fleece Jacket", "Item in the cart does not match.");
    }

    @Test(priority = 8)
    public void proceedToCheckout() {
        // Proceed to checkout
        driver.findElement(By.cssSelector("button[id=\"checkout\"]")).click();

        // Verify the checkout page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkoutPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        Assert.assertEquals(checkoutPageTitle.getText(), "Checkout: Your Information", "Checkout page is not displayed.");
    }

    @Test(priority = 9)
    public void enterShippingInformation() {
        // Enter shipping information
        driver.findElement(By.cssSelector("input[id=\"first-name\"]")).sendKeys("John");
        driver.findElement(By.cssSelector("input[id=\"last-name\"]")).sendKeys("Doe");
        driver.findElement(By.cssSelector("input[id=\"postal-code\"]")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[id=\"continue\"]")).click();

        // Verify the overview page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement overviewPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        Assert.assertEquals(overviewPageTitle.getText(), "Checkout: Overview", "Checkout overview page is not displayed.");
    }


    @Test(priority = 10)
    public void verifyItemInCheckoutOverview() {
        // Verify the item in the overview
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement overviewItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        Assert.assertEquals(overviewItem.getText(), "Sauce Labs Fleece Jacket", "Item in the overview does not match.");
    }

    @Test(priority = 11)
    public void completeCheckoutProcess() {
        // Finish the checkout process
        driver.findElement(By.cssSelector("button[id=\"finish\"]")).click();

        // Verify the confirmation page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        Assert.assertEquals(confirmationMessage.getText(), "Thank you for your order!", "Order confirmation message is not displayed.");
    }



    @Test(priority = 12)
    public void logoutFromWebsite() {
        // Open the menu
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[id='react-burger-menu-btn']")));
        menuButton.click();

        // Wait for the logout link to be clickable
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-test='logout-sidebar-link']")));
        logoutLink.click();

        // Verify that the login page is displayed
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id='login-button']")));
        Assert.assertTrue(loginButton.isDisplayed(), "Login page is not displayed after logout.");
    }


@AfterClass
public void tearDown() {
    // Close the browser and quit the driver
    if (driver != null) {
        driver.quit();
    }
}
}

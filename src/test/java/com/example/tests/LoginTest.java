package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set the path of the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:/Users/Kanchana/untitled/chromedriver.exe");

        // Initialize the ChromeDriver and assign it to the class-level driver
        driver = new ChromeDriver();

        // Navigate to the login page
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testLogin() {
        // Locate the username field and enter the username
        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.sendKeys("standard_user");

        // Locate the password field and enter the password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        // Locate the login button and click it
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Verify that the user is redirected to the inventory page
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "The page title does not match the expected title after login.");
    }

    @Test
    public void testInvalidLogin() {
        // Locate the username field and enter an invalid username
        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.sendKeys("invalid_user");

        // Locate the password field and enter an invalid password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrong_password");

        // Locate the login button and click it
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Verify that an error message is displayed
        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for invalid login.");
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}

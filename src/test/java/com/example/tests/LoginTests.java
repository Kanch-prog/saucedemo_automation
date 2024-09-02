package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class LoginTests {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Kanchana/untitled/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testValidLogin() {
        login("standard_user", "secret_sauce");
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "The page title does not match the expected title after login.");
    }

    @Test
    public void testInvalidLogin() {
        login("wrong_user", "wrong_password");
        String errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service", "Error message does not match.");
    }

    @Test
    public void testEmptyUsername() {
        login("", "secret_sauce");
        String errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        assertEquals(errorMessage, "Epic sadface: Username is required", "Error message does not match.");
    }

    @Test
    public void testEmptyPassword() {
        login("standard_user", "");
        String errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        assertEquals(errorMessage, "Epic sadface: Password is required", "Error message does not match.");
    }

    @Test
    public void testLogout() {
        // Login first
        login("standard_user", "secret_sauce");

        // Click the menu button to open the sidebar
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        menuButton.click();

        // Wait for the logout link to be visible and clickable
        Duration Duration = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

        // Click the logout button
        logoutButton.click();

        // Verify that the user is redirected to the login page
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "User is not redirected to the login page after logout.");
    }

    private void login(String username, String password) {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

package ru.gaza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.xpath("//input[@name='login']");
    private final By passwordInput = By.xpath("//input[@name='password']");
    private final By loginButton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage login(String username, String password) {
        waitForVisibility(usernameInput);
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        clickButton(loginButton);
        return new DashboardPage(driver);
    }
}

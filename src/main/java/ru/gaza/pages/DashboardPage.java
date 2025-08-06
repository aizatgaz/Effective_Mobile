package ru.gaza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    private final By dashboardListItem = By.xpath("//a[contains(@href, '#default_personal/dashboard')]");
    private final By allDashboardsText = By.xpath("//span[text()='All Dashboards']");
    private final By addDashboardButton = By.xpath("//span[text()='Add New Dashboard']");
    private final By nameField = By.xpath("//input[@placeholder='Enter dashboard name']");
    private final By addButton = By.xpath("//button[text()='Add']");
    private final By addNewWidgetButton = By.xpath("//span[text()='Add new widget']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void openDashboardPanel() {
        clickButton(dashboardListItem);
    }

    public void createDashboard(String dashboardName) {
        waitForVisibility(allDashboardsText);
        clickButton(addDashboardButton);
        waitForVisibility(nameField);
        driver.findElement(nameField).sendKeys(dashboardName);
        clickButton(addButton);
    }

    public WidgetFormPage openWidgetFormPage() {
        clickButton(addNewWidgetButton);
        return new WidgetFormPage(driver);

    }

    public void waitUntilAddNewWidgetButtonClickable() {
        waitForClickability(addNewWidgetButton);
    }
}
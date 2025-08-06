package ru.gaza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WidgetFormPage extends BasePage {
    private final By launchStatisticChartButton = By.xpath("//div[text()='Launch statistics chart']");
    private final By nextStepButton = By.xpath("//span[text()='Next step']");
    private final By demoFilterButton = By.xpath("//span[text()='DEMO_FILTER']");
    private final By widgetNameField = By.xpath("//input[@type='text' and @placeholder='Enter widget name']");
    private final By addButton = By.xpath("//button[text()='Add']");

    public WidgetFormPage(WebDriver driver) {
        super(driver);
    }

    public void launchStatisticChartButtonClick() {
        clickButton(launchStatisticChartButton);
    }

    public void demoFilterButtonClick() {
        clickButton(demoFilterButton);
    }

    public void fillWidgetName(String widgetName) {
        driver.findElement(widgetNameField).clear();
        driver.findElement(widgetNameField).sendKeys(widgetName);
    }

    public void nextStepButtonClick() {
        clickButton(nextStepButton);
    }

    public void addButtonClick() {
        clickButton(addButton);
    }
}

package ru.gaza.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.gaza.pages.DashboardPage;
import ru.gaza.pages.LoginPage;
import ru.gaza.utils.AllureConfig;
import ru.gaza.utils.ReportPortalConfig;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureConfig.class)
public class WidgetCreationTest {
    private static final String BASE_URL = ReportPortalConfig.get("rp.endpoint");
    private static final String USERNAME = ReportPortalConfig.get("rp.login");
    private static final String PASSWORD = ReportPortalConfig.get("rp.password");
    private static WebDriver driver;
    private static DashboardPage dashboardPage;

    @BeforeAll
    static void setUp() {
        var options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        WebDriverManager.chromedriver().setup();
        driver.get(BASE_URL);
        var loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(USERNAME, PASSWORD);
        dashboardPage.openDashboardPanel();
        String dashBoardName = "dashBoardName_" + UUID.randomUUID().toString().substring(0, 8);
        dashboardPage.createDashboard(dashBoardName);
    }

    @AfterAll
    static void tearDown() {
        driver.manage().deleteAllCookies();
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Создание нового Widget типа 'Task Progress'")
    void createTaskProgressWidgetTest() {
        var widgetModalPage = dashboardPage.openWidgetFormPage();
        widgetModalPage.launchStatisticChartButtonClick();
        widgetModalPage.nextStepButtonClick();
        widgetModalPage.demoFilterButtonClick();
        widgetModalPage.nextStepButtonClick();
        String widgetName = "widgetName_" + UUID.randomUUID().toString().substring(0, 8);
        widgetModalPage.fillWidgetName(widgetName);
        widgetModalPage.addButtonClick();
        dashboardPage.waitUntilAddNewWidgetButtonClickable();
        var widgetNameLocator = By.xpath("//*[text()='%s']".formatted(widgetName));
        widgetModalPage.waitForVisibility(widgetNameLocator);
        var element = driver.findElement(widgetNameLocator);
        assertTrue(element.isDisplayed(), "Отображение виджета с именем " + widgetName);
    }
}

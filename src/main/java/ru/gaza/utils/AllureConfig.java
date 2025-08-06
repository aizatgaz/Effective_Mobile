package ru.gaza.utils;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AllureConfig implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            WebDriver driver = getDriverFromStore(context);
            if (driver != null) {
                saveScreenshot(driver);
            }
        }
    }

    private WebDriver getDriverFromStore(ExtensionContext context) {
        return (WebDriver) context.getStore(ExtensionContext.Namespace.GLOBAL).get("driver");
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    private byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}

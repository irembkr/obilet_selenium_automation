package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HotelHomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By otelMenu = By.cssSelector("li.rentcar a[href='/otel']");
    private final By nereyeInput = By.cssSelector("ob-select#origin input#origin-input");
    private final By firstResult = By.cssSelector("ob-select#origin .results ul li.item");
    private final By otelAraBtn = By.cssSelector("button#search-button");

    public HotelHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public void goToHotelHome() {
        wait.until(ExpectedConditions.elementToBeClickable(otelMenu)).click();
    }

    public void setLocationAndPickFirstResult(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(nereyeInput)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(nereyeInput));
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(text);

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstResult));
        waitShort(400);

        wait.until(ExpectedConditions.elementToBeClickable(firstResult)).click();
    }

    public void search() {
        wait.until(ExpectedConditions.elementToBeClickable(otelAraBtn)).click();
    }

    private void waitShort(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
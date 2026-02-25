package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BusHomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By neredenInput = By.id("origin-input");
    private final By neredenFirstItem = By.cssSelector("#origin .results ul li.item");

    private final By nereyeInput = By.id("destination-input");
    private final By nereyeFirstItem = By.cssSelector("#destination .results ul li.item");

    private final By datepicker = By.cssSelector("ob-datepicker#departure");
    private final By nextMonthBtn = By.cssSelector("button.next"); // varsa
    private final By otobusAraBtn = By.id("search-button");

    public BusHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public void setNereden(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(neredenInput)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(neredenInput));
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(city);

        // küçük bekleme
        waitShort(400);

        wait.until(ExpectedConditions.elementToBeClickable(neredenFirstItem)).click();
    }

    public void setNereyeRizeBekleSec() {
        wait.until(ExpectedConditions.elementToBeClickable(nereyeInput)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(nereyeInput));
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys("Rize");

        wait.until(ExpectedConditions.visibilityOfElementLocated(nereyeFirstItem));
        waitShort(600);

        wait.until(ExpectedConditions.elementToBeClickable(nereyeFirstItem)).click();
    }

    // "2026-03-28" seçilecek
    public void selectDate(String yyyyMmDd) {
        wait.until(ExpectedConditions.elementToBeClickable(datepicker)).click();


        By dayBtn = By.cssSelector("button[data-date='" + yyyyMmDd + "']");
        wait.until(ExpectedConditions.elementToBeClickable(dayBtn)).click();
    }

    public void search() {
        wait.until(ExpectedConditions.elementToBeClickable(otobusAraBtn)).click();
    }

    private void waitShort(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
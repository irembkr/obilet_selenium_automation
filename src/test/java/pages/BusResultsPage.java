package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BusResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BusResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(35));
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    private final By firstJourney = By.cssSelector("li.journey:first-of-type");
    private final By firstJourneyPrice = By.cssSelector("li.journey:first-of-type .amount");
    private final By firstJourneyOpenButton = By.cssSelector("li.journey:first-of-type button.open");

    private final By insCloseButton = By.cssSelector("div[id^='close-button-']");
    private final By insOverlay = By.cssSelector("#ins-frameless-overlay");

    private void closeInsPopupIfPresent() {
        try {
            // popup bazen 3sn gecikmeli geliyor
            sleep(3500);

            if (driver.findElements(insCloseButton).size() > 0) {
                WebElement btn = driver.findElement(insCloseButton);
                if (btn.isDisplayed()) {
                    try {
                        btn.click();
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                    }
                    System.out.println("Bilgi: INS popup kapatıldı (close-button).");
                    sleep(800);
                }
            }


            if (driver.findElements(insOverlay).size() > 0) {
                ((JavascriptExecutor) driver).executeScript(
                        "var o=document.querySelector('#ins-frameless-overlay'); if(o){o.style.display='none'; o.remove();}"
                );
                System.out.println("Bilgi: INS overlay kaldırıldı.");
                sleep(500);
            }
        } catch (Exception ignored) {}
    }

    public String getFirstJourneyPrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstJourney));
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstJourneyPrice));
        return driver.findElement(firstJourneyPrice).getText().trim();
    }

    public void selectFirstJourney() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstJourney));
        sleep(1500);

        // Scroll
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
                driver.findElement(firstJourney));
        sleep(1200);

        // Scroll sonrası cookie çıkıyorsa kapat
        closeInsPopupIfPresent();

        // Koltuk Seç'e tıkla
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(firstJourneyOpenButton));
        try {
            btn.click();
        } catch (ElementClickInterceptedException e) {
            closeInsPopupIfPresent();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

        System.out.println("İlk sefer seçildi (Koltuk Seç).");
        sleep(2000);
    }
}
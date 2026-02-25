package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeatSelectionPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SeatSelectionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8)); // kısa
    }

    private final By maleBtn = By.cssSelector("button.male");
    private final By femaleBtn = By.cssSelector("button.female");
    private final By continueBtn = By.cssSelector("button.continue");

    public void selectSeat(int seatNo) {
        System.out.println("Koltuk aranıyor: " + seatNo);

        // Koltukların geldiğini kanıtlayan genel selector (koltuk attribute'u)
        By anySeat = By.cssSelector("a[obilet\\:seat]");
        wait.until(ExpectedConditions.presenceOfElementLocated(anySeat));

        String seatCss = "a[obilet\\:seat='" + seatNo + "']";

        WebElement seatEl = null;

        long end = System.currentTimeMillis() + 6000; // 6 sn içinde ara (uzun bekleme yok)
        while (System.currentTimeMillis() < end) {
            try {
                seatEl = driver.findElement(By.cssSelector(seatCss));
                if (seatEl.isDisplayed()) break;
            } catch (NoSuchElementException ignored) {
            }

            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        }

        if (seatEl == null) {
            // son bir deneme (DOM yeniden çizilmiş olabilir)
            try {
                seatEl = driver.findElement(By.cssSelector(seatCss));
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("Koltuk bulunamadı: " + seatNo + " (Seat map yüklenmemiş olabilir)");
            }
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", seatEl);
        try { Thread.sleep(250); } catch (InterruptedException ignored) {}

        try {
            seatEl.click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seatEl);
        }

        System.out.println("Koltuk seçildi: " + seatNo);
    }

    public void selectGender(String gender) {
        if (gender == null) gender = "";
        gender = gender.trim().toLowerCase();

        if (gender.contains("kad")) {
            wait.until(ExpectedConditions.elementToBeClickable(femaleBtn)).click();
            System.out.println("Cinsiyet seçildi: Kadın");
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(maleBtn)).click();
            System.out.println("Cinsiyet seçildi: Erkek");
        }
    }

    public void continueToPayment() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
        System.out.println("Ödeme adımına geçildi.");
    }
}
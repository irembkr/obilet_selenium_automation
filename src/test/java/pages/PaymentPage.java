package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    // Ödeme sayfası toplam tutar
    private final By totalAmount = By.cssSelector(".total .amount");

    public String getTotalAmount() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(totalAmount)
        ).getText();
    }
}
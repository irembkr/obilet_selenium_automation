package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HotelResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HotelResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12)); // kısa ve yeterli
    }

    private final By filterPanelBtn = By.cssSelector("button.head-filter__btn");
    private final By yarimPansiyonCheckbox = By.xpath("//label[contains(.,'Yarım Pansiyon')]//input");
    private final By applyFilterBtn = By.cssSelector("button.apply-btn");

    private final By sortDropdown = By.cssSelector("button.sort-btn");
    private final By sortLowToHigh = By.cssSelector("button[data-value='price-lowest']");

    private final By hotelCards = By.cssSelector(".hotel-card");
    private final By priceText = By.cssSelector(".hotel-card .price");

    public void filterYarimPansiyon() {
        waitForResults();

        safeClick(filterPanelBtn);
        safeClick(yarimPansiyonCheckbox);
        safeClick(applyFilterBtn);


        waitForResults();
        System.out.println("Yarım Pansiyon filtresi uygulandı.");
    }

    public boolean areResultsHalfBoard() {
        waitForResults();


        String pageText = driver.findElement(By.tagName("body")).getText();
        return pageText.contains("Yarım Pansiyon");
    }

    public void sortByLowestPrice() {
        waitForResults();

        safeClick(sortDropdown);
        safeClick(sortLowToHigh);

        // sıralama uygulansın diye kısa bekleme
        waitForResults();
        System.out.println("Sıralama uygulandı: Fiyat (Düşükten Yükseğe)");
    }

    public boolean arePricesSortedAscending() {
        waitForResults();

        List<WebElement> prices = driver.findElements(priceText);
        List<Double> nums = new ArrayList<>();

        for (WebElement p : prices) {
            String t = p.getText();
            Double val = parsePrice(t);
            if (val != null) nums.add(val);
        }

        if (nums.size() < 2) return true;

        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < nums.get(i - 1)) return false;
        }
        return true;
    }

    private void waitForResults() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(hotelCards));
        try { Thread.sleep(400); } catch (InterruptedException ignored) {} // çok kısa
    }

    private void safeClick(By by) {
        int attempt = 0;
        while (attempt < 2) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
                el.click();
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempt++;
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        }
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
        el.click();
    }

    private Double parsePrice(String raw) {
        if (raw == null) return null;
        String s = raw.replace("TL", "")
                .replaceAll("\\s+", "")
                .replace(".", "")
                .replace(",", ".")
                .replaceAll("[^0-9.]", "");
        if (s.isEmpty()) return null;
        try { return Double.parseDouble(s); } catch (Exception e) { return null; }
    }
}
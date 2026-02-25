package tests;

import base.BaseTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BusHomePage;
import pages.BusResultsPage;
import pages.PaymentPage;
import pages.SeatSelectionPage;

public class BookingAutomationTest extends BaseTest {

    @Test
    public void case1_otobusBiletiSatinAlma_fiyatEslestirme() {

        System.out.println("Case 1 - Otobüs bileti satın alma başladı.");
        driver.get("https://www.obilet.com/");

        BusHomePage home = new BusHomePage(driver);
        BusResultsPage results = new BusResultsPage(driver);
        SeatSelectionPage seat = new SeatSelectionPage(driver);
        PaymentPage payment = new PaymentPage(driver);

        System.out.println("Adım 1: Nereden İstanbul seçiliyor.");
        home.setNereden("İstanbul");

        System.out.println("Adım 2: Nereye Rize yazılıyor ve bekleniyor.");
        home.setNereyeRizeBekleSec();

        System.out.println("Adım 3: Tarih seçiliyor (2026-03-28).");
        home.selectDate("2026-03-28");

        System.out.println("Adım 4: Otobüs Ara butonuna basılıyor.");
        home.search();

        System.out.println("Adım 5: Sonuçlar bekleniyor ve ilk fiyat alınıyor.");
        String listPrice = results.getFirstJourneyPrice();
        System.out.println("Liste fiyatı: " + listPrice);

        Actions actions = new Actions(driver);
        for (int i = 0; i < 11; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
        }

        results.selectFirstJourney();

        System.out.println("Adım 6: Koltuk ve cinsiyet seçiliyor.");
        seat.selectSeat(28);
        seat.selectGender("erkek");
        seat.continueToPayment();

        System.out.println("Adım 7: Ödeme sayfası tutarı alınıyor.");
        String paymentPrice = payment.getTotalAmount();
        System.out.println("Ödeme fiyatı: " + paymentPrice);

        Assert.assertEquals(
                normalizePrice(paymentPrice),
                normalizePrice(listPrice),
                "Liste fiyatı ile ödeme fiyatı uyuşmuyor!"
        );

        System.out.println("Case 1 tamamlandı. Fiyat eşleşti.");
    }

    private String normalizePrice(String raw) {
        return raw.replace("TL", "")
                .replaceAll("\\s+", "")
                .replace(".", "")
                .replace(",", ".")
                .replaceAll("[^0-9.]", "");
    }
}
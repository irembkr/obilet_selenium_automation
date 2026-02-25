Obilet UI Test Automation

Bu proje, Obilet web sitesi üzerinde iki temel senaryonun Selenium + TestNG kullanılarak otomasyonunu içerir.

Kullanılan Teknolojiler

Java 17+

Selenium 4

TestNG

Maven

WebDriverManager

Test Senaryoları
Case 1 – Otobüs Bileti Satın Alma & Fiyat Doğrulama

Adımlar:

Nereden: İstanbul seçilir.

Nereye: Rize yazılır ve dropdown’dan seçilir.

Tarih: 28 Mart 2026 seçilir.

Otobüs Ara butonuna basılır.

Listede ilk seferin fiyatı alınır.

İlk sefer seçilir.

28 numaralı koltuk seçilir.

Cinsiyet seçilir.

Ödeme sayfasındaki toplam tutar alınır.

Liste fiyatı ile ödeme fiyatı karşılaştırılır.

Doğrulama:

Liste fiyatı ile ödeme sayfasındaki fiyat eşleşmelidir.

Case 2 – Otel Filtreleme & Sıralama Kontrolü

Adımlar:

Otel sayfasına geçilir.

Lokasyon olarak Rize aranır.

Yarım Pansiyon filtresi uygulanır.

Fiyatlar "Düşükten Yükseğe" sıralanır.

Doğrulamalar:

Yarım Pansiyon filtresi uygulanmış olmalıdır.

Fiyatlar artan sırada olmalıdır.

Çalıştırma
IntelliJ ile

BookingAutomationTest sınıfını çalıştır.

Maven ile
mvn clean test

Raporlar:

target/surefire-reports

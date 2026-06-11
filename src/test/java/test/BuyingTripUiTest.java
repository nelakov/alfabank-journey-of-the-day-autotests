package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import junit.extension.VideoAttachExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.DataGenerator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import page.CreditPage;
import page.PaymentPage;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith(VideoAttachExtension.class)
public class BuyingTripUiTest {

    @BeforeEach
    public void openPage() {
        String url = System.getProperty("sut.url");
        open(url);
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    // CSV rows mark month/year not under test with VALID so the fixture never
    // expires; a hardcoded year rots once the calendar passes it.
    private Card cardFrom(String number, String month, String year, String owner, String cvc) {
        Card valid = DataGenerator.getValidCard();
        return new Card(
                number,
                "VALID".equals(month) ? valid.month() : month,
                "VALID".equals(year) ? valid.year() : year,
                owner,
                cvc);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/incorrectValues.cvs", numLinesToSkip = 1)
    @DisplayName("Should show validation error for incorrect field values on payment page")
    @Description("CSV-driven: submit payment form with one invalid field, assert the expected error under that field. " +
            "See form layout: docs/ui-payment-form.png")
    void shouldShowWarningIfValueIsIncorrectForPayment(String number, String month, String year, String owner, String cvc, String field, String warning) {
        Card incorrectValuesCard = cardFrom(number, month, year, owner, cvc);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(incorrectValuesCard);
        paymentPage.shouldShowValidationError(field, warning);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/incorrectValues.cvs", numLinesToSkip = 1)
    @DisplayName("Should show validation error for incorrect field values on credit page")
    @Description("CSV-driven: submit credit form with one invalid field, assert the expected error under that field. " +
            "See form layout: docs/ui-credit-form.png")
    void shouldShowWarningIfValueIsIncorrectForCredit(String number, String month, String year, String owner, String cvc, String field, String warning) {
        Card incorrectValuesCard = cardFrom(number, month, year, owner, cvc);
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(incorrectValuesCard);
        creditPage.shouldShowValidationError(field, warning);
    }

    @Test
    @Disabled("SUT defect: holder field accepts digits, Cyrillic and special characters — no client-side validation error is shown")
    @DisplayName("Should show validation error for invalid holder name on payment page")
    @Description("Submit payment form with garbage holder name, assert validation error under Owner field. " +
            "Currently fails: the SUT renders no error for the holder field.")
    void shouldShowWarningIfHolderIsInvalidForPayment() {
        Card invalidHolderCard = DataGenerator.getInvalidHolderCard();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(invalidHolderCard);
        paymentPage.shouldShowValidationError("Владелец", "Неверный формат");
    }

    @Test
    @Disabled("SUT defect: holder field accepts digits, Cyrillic and special characters — no client-side validation error is shown")
    @DisplayName("Should show validation error for invalid holder name on credit page")
    @Description("Submit credit form with garbage holder name, assert validation error under Owner field. " +
            "Currently fails: the SUT renders no error for the holder field.")
    void shouldShowWarningIfHolderIsInvalidForCredit() {
        Card invalidHolderCard = DataGenerator.getInvalidHolderCard();
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(invalidHolderCard);
        creditPage.shouldShowValidationError("Владелец", "Неверный формат");
    }

    @Test
    @DisplayName("Should show validation error for expired card on payment page")
    @Description("Submit payment form with card expired 1 month ago, assert validation error under Month field.")
    void shouldShowWarningIfCardIsExpiredForPayment() {
        Card expiredCard = DataGenerator.getInvalidExpDateCard(-1);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        paymentPage.shouldShowValidationError("Месяц", "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should show validation error for expired card on credit page")
    @Description("Submit credit form with card expired 1 month ago, assert validation error under Month field.")
    void shouldShowWarningIfCardIsExpiredForCredit() {
        Card expiredCard = DataGenerator.getInvalidExpDateCard(-1);
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(expiredCard);
        creditPage.shouldShowValidationError("Месяц", "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should show validation error when expiration date exceeds 5 years on payment page")
    @Description("Submit payment form with expiry 72 months ahead (>5 years; SUT validates year-granularity), assert validation error under Year field.")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForPayment() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard(72);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(invalidExpDateCard);
        paymentPage.shouldShowValidationError("Год", "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should show validation error when expiration date exceeds 5 years on credit page")
    @Description("Submit credit form with expiry 72 months ahead (>5 years; SUT validates year-granularity), assert validation error under Year field.")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForCredit() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard(72);
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(invalidExpDateCard);
        creditPage.shouldShowValidationError("Год", "Неверно указан срок действия карты");
    }
}

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
import static org.junit.jupiter.api.Assertions.*;

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

    @ParameterizedTest
    @CsvFileSource(resources = "/incorrectValues.cvs", numLinesToSkip = 1)
    @DisplayName("Should show validation error for incorrect field values on payment page")
    @Description("CSV-driven: submit payment form with invalid field data, assert validation error is displayed. " +
            "See form layout: docs/ui-payment-form.png")
    void shouldShowWarningIfValueIsIncorrectForPayment(String number, String month, String year, String owner, String cvc, String message) {
        Card incorrectValuesCard = new Card(number, month, year, owner, cvc);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(incorrectValuesCard);
        assertTrue(paymentPage.isValidationErrorVisible(), message);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/incorrectValues.cvs", numLinesToSkip = 1)
    @DisplayName("Should show validation error for incorrect field values on credit page")
    @Description("CSV-driven: submit credit form with invalid field data, assert validation error is displayed. " +
            "See form layout: docs/ui-credit-form.png")
    void shouldShowWarningIfValueIsIncorrectForCredit(String number, String month, String year, String owner, String cvc, String warning, String message) {
        Card incorrectValues = new Card(number, month, year, owner, cvc);
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(incorrectValues);
        assertTrue(creditPage.isValidationErrorVisible(), message);
    }

    @Test
    @DisplayName("Should show validation error for expired card on payment page")
    @Description("Submit payment form with card expired 1 month ago, assert validation error under Month field.")
    void shouldShowWarningIfCardIsExpiredForPayment() {
        Card expiredCard = DataGenerator.getInvalidExpDateCard(-1);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(expiredCard);
        assertTrue(paymentPage.isValidationErrorVisible(), "Expected validation error for expired card on payment page");
    }

    @Test
    @DisplayName("Should show validation error for expired card on credit page")
    @Description("Submit credit form with card expired 1 month ago, assert validation error under Month field.")
    void shouldShowWarningIfCardIsExpiredForCredit() {
        Card expiredCard = DataGenerator.getInvalidExpDateCard(-1);
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(expiredCard);
        assertTrue(creditPage.isValidationErrorVisible(), "Expected validation error for expired card on credit page");
    }

    @Test
    @DisplayName("Should show validation error when expiration date exceeds 5 years on payment page")
    @Description("Submit payment form with expiry 61 months ahead (>5 years), assert validation error under Year field.")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForPayment() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard(61);
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(invalidExpDateCard);
        assertTrue(paymentPage.isValidationErrorVisible(), "Expected validation error for expiration date exceeding 5 years on payment page");
    }

    @Test
    @DisplayName("Should show validation error when expiration date exceeds 5 years on credit page")
    @Description("Submit credit form with expiry 61 months ahead (>5 years), assert validation error under Year field.")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForCredit() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard(61);
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(invalidExpDateCard);
        assertTrue(creditPage.isValidationErrorVisible(), "Expected validation error for expiration date exceeds 5 years on credit page");
    }
}

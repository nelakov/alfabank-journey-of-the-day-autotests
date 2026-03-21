package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import utils.DataGenerator;
import utils.DbClient;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.PaymentPage;
import page.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;


public class BuyingTripDbTest {
    Card validCard = DataGenerator.getValidCard();
    Card declinedCard = DataGenerator.getDeclinedCard();
    Card fakeCard = DataGenerator.getFakeCard();

    @BeforeEach
    public void openPage() throws SQLException {
        DbClient.clearTables();
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

    @Test
    @DisplayName("Should confirm payment with APPROVED card status")
    @Description("Fill payment form with APPROVED card (4441), submit, verify success notification and APPROVED status in payment_entity. " +
            "See expected UI: docs/ui-credit-approved.png")
    void shouldConfirmPaymentWithValidCard() throws SQLException {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(validCard);
        paymentPage.shouldShowSuccessNotification();
        assertEquals("APPROVED", DbClient.findPaymentStatus());
    }

    @Test
    @DisplayName("Should confirm credit with APPROVED card status")
    @Description("Fill credit form with APPROVED card (4441), submit, verify success notification and APPROVED status in credit_request_entity. " +
            "See expected UI: docs/ui-credit-approved.png")
    void shouldConfirmCreditWithValidCard() throws SQLException {
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(validCard);
        creditPage.shouldShowSuccessNotification();
        assertEquals("APPROVED", DbClient.findCreditStatus());
    }

    @Test
    @DisplayName("Should decline payment with DECLINED card status")
    @Description("Fill payment form with DECLINED card (4442), submit, verify error notification and DECLINED status in payment_entity. " +
            "See expected UI: docs/ui-credit-declined.png")
    void shouldNotConfirmPaymentWithDeclinedCard() throws SQLException {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(declinedCard);
        paymentPage.shouldShowErrorNotification();
        assertEquals("DECLINED", DbClient.findPaymentStatus());
    }

    @Test
    @DisplayName("Should decline credit with DECLINED card status")
    @Description("Fill credit form with DECLINED card (4442), submit, verify error notification and DECLINED status in credit_request_entity. " +
            "See expected UI: docs/ui-credit-declined.png")
    void shouldNotConfirmCreditWithDeclinedCard() throws SQLException {
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(declinedCard);
        creditPage.shouldShowErrorNotification();
        assertEquals("DECLINED", DbClient.findCreditStatus());
    }

    @Test
    @DisplayName("Should not create order for unknown card on payment page")
    @Description("Fill payment form with unknown card (4449), submit, verify error notification and zero records in order_entity.")
    void shouldNotConfirmPaymentWithFakeCard() throws SQLException {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(fakeCard);
        paymentPage.shouldShowErrorNotification();
        assertEquals("0", DbClient.countRecords());
    }

    @Test
    @DisplayName("Should not create order for unknown card on credit page")
    @Description("Fill credit form with unknown card (4449), submit, verify error notification and zero records in order_entity.")
    void shouldNotConfirmCreditWithFakeCard() throws SQLException {
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(fakeCard);
        creditPage.shouldShowErrorNotification();
        assertEquals("0", DbClient.countRecords());
    }

}

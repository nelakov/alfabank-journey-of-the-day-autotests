package test;

import data.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ApiClient;
import utils.DataGenerator;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BuyingTripApiTest {
    Card invalidHolderCard = DataGenerator.getInvalidHolderCard();

    @Test
    @DisplayName("Не должен отправлять запрос на оплату с некорректным именем владельца")
    void shouldNotSendPaymentRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/pay");
        assertNotEquals(200, statusCode);
    }

    @Test
    @DisplayName("Не должен отправлять запрос на кредит с некорректным именем владельца")
    void shouldNotSendCreditRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/credit");
        assertNotEquals(200, statusCode);
    }

}

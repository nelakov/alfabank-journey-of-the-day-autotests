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
    @DisplayName("Should reject payment request with invalid holder name")
    void shouldNotSendPaymentRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/pay");
        assertNotEquals(200, statusCode);
    }

    @Test
    @DisplayName("Should reject credit request with invalid holder name")
    void shouldNotSendCreditRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/credit");
        assertNotEquals(200, statusCode);
    }

}

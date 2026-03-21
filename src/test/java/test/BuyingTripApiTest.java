package test;

import data.Card;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ApiClient;
import utils.DataGenerator;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BuyingTripApiTest {
    Card invalidHolderCard = DataGenerator.getInvalidHolderCard();

    @Test
    @DisplayName("Should reject payment request with invalid holder name")
    @Description("POST /api/v1/pay with holder containing Cyrillic, special chars and digits — expect non-200 status code.")
    void shouldNotSendPaymentRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/pay");
        assertNotEquals(200, statusCode);
    }

    @Test
    @DisplayName("Should reject credit request with invalid holder name")
    @Description("POST /api/v1/credit with holder containing Cyrillic, special chars and digits — expect non-200 status code.")
    void shouldNotSendCreditRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/credit");
        assertNotEquals(200, statusCode);
    }

}

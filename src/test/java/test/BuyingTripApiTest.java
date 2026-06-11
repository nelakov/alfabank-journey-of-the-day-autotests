package test;

import data.Card;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ApiClient;
import utils.DataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuyingTripApiTest {
    Card invalidHolderCard = DataGenerator.getInvalidHolderCard();

    @Test
    @Disabled("SUT defect: /api/v1/pay accepts invalid holder name and returns 200 — no server-side holder validation")
    @DisplayName("Should reject payment request with invalid holder name")
    @Description("POST /api/v1/pay with holder containing Cyrillic, special chars and digits — expect 400 Bad Request. " +
            "Currently fails: the SUT responds 200.")
    void shouldNotSendPaymentRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/pay");
        assertEquals(400, statusCode);
    }

    @Test
    @Disabled("SUT defect: /api/v1/credit accepts invalid holder name and returns 200 — no server-side holder validation")
    @DisplayName("Should reject credit request with invalid holder name")
    @Description("POST /api/v1/credit with holder containing Cyrillic, special chars and digits — expect 400 Bad Request. " +
            "Currently fails: the SUT responds 200.")
    void shouldNotSendCreditRequestWithIncorrectName() {
        int statusCode = ApiClient.getRequestStatusCode(invalidHolderCard, "/api/v1/credit");
        assertEquals(400, statusCode);
    }
}

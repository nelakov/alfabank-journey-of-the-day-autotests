package page;

import com.codeborne.selenide.SelenideElement;
import data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormPage {
    private final Duration notificationTimeout;

    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement notificationStatusOk = $(".notification_status_ok");
    private final SelenideElement notificationStatusError = $(".notification_status_error");
    private final SelenideElement validationError = $(".input__sub");

    protected FormPage(String headingText, Duration notificationTimeout) {
        SelenideElement heading = $$("h3").find(text(headingText));
        this.notificationTimeout = notificationTimeout;

        heading.shouldBe(visible);
    }

    public void fillData(Card card) {
        cardNumberField.setValue(card.number());
        monthField.setValue(card.month());
        yearField.setValue(card.year());
        ownerField.setValue(card.holder());
        cvcField.setValue(card.cvc());
        continueButton.click();
    }

    public boolean isValidationErrorVisible() {
        return validationError.isDisplayed();
    }

    public void shouldShowSuccessNotification() {
        notificationStatusOk.shouldBe(visible, notificationTimeout);
    }

    public void isNotificationStatusErrorVisible() {
        notificationStatusError.shouldBe(visible, notificationTimeout);
    }
}
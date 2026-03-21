package utils;

import data.Card;

import java.time.YearMonth;


public class DataGenerator {

    private static Card createCard(String number, int months, String holder, String cvc) {
        YearMonth expiry = YearMonth.now().plusMonths(months);
        String month = String.format("%02d", expiry.getMonthValue());
        String year = String.valueOf(expiry.getYear() % 100);
        return new Card(number, month, year, holder, cvc);
    }


    public static Card getValidCard() {
        return createCard("4444 4444 4444 4441", 6, "Card Holder", "123");
    }


    public static Card getDeclinedCard() {
        return createCard("4444 4444 4444 4442", 6, "Card Holder", "123");
    }

    public static Card getFakeCard() {
        return createCard("4444 4444 4444 4449", 6, "Card Holder", "123");
    }

    public static Card getInvalidHolderCard() {
        return createCard("4444 4444 4444 4441", 6, "123456789Йцукенгшщзхъ!\"№;%:?*()123456789Йцукенгшщзхъ!\"№;%:?*()", "123");
    }

    public static Card getInvalidExpDateCard(int months) {
        return createCard("4444 4444 4444 4441", months, "Card Holder", "123");
    }

}

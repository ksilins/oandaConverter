package com.oanda.pages;

import com.oanda.models.ConverterValues;
import com.oanda.utils.ValueParser;
import org.openqa.selenium.By;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Converter {

    private final ConverterValues values = new ConverterValues();
    private final ValueParser parser = new ValueParser();

    private final By CURRENCY_OWNED = By.id("quote_currency_input");
    private final By CURRENCY_OWNED_AMOUNT = By.id("quote_amount_input");
    private final By CURRENCY_OWNED_CODE = By.id("quote_currency_code");
    private final By CURRENCY_CONVERTED = By.id("base_currency_input");
    private final By CURRENCY_CONVERTED_AMOUNT = By.id("base_amount_input");
    private final By CURRENCY_CONVERTED_CODE = By.id("base_currency_code");
    private final By FLIP_CURRENCIES = By.id("flipper");
    private final By RATE_INPUT_FILED = By.id("interbank_rates_input");
    private final By SET_RATE_VALUE = By.id("form_interbank_rates_hidden");
    private final By RATE_DROPDOWN_CARET = By.id("rate_caret");
    private final By RATE_DROPDOWN_LIST = By.id("interbank_rate_list_container");
    private final By END_DATE_INPUT = By.id("end_date_input");

    public void currencyOwnedSelect(String ownCurrency) {
        $(CURRENCY_OWNED)
                .val(ownCurrency)
                .pressEnter();
        values.setOwnCurrency($(CURRENCY_OWNED_CODE).getText());
    }

    public void currencyNeededSelect(String neededCurrency) {
        $(CURRENCY_CONVERTED)
                .val(neededCurrency)
                .pressEnter();
        values.setConvertCurrency($(CURRENCY_CONVERTED_CODE).getText());
    }

    public void currencyAmountInput(String currencyAmount) {
        values.setOwnAmount(currencyAmount);
        $(CURRENCY_CONVERTED_AMOUNT).clear();
        $(CURRENCY_OWNED_AMOUNT)
                .val(currencyAmount)
                .pressEnter();
        $(CURRENCY_CONVERTED_AMOUNT).shouldNotBe(empty);
        values.setConvertedAmount($(CURRENCY_CONVERTED_AMOUNT).getValue());
    }

    public void assertOwnCurrencyAmount(String currencyAmount) {
        assertThat(values.getOwnAmount()).isEqualTo(currencyAmount);
    }

    public void currencyFlip() {
        $(FLIP_CURRENCIES).click();
    }

    public void assertCurrencyFlip() {
        assertThat(values.getOwnCurrency()).isEqualTo($(CURRENCY_CONVERTED_CODE).getText());
        assertThat(values.getConvertCurrency()).isEqualTo($(CURRENCY_OWNED_CODE).getText());
    }

    public void setBankInterest(String interestRate) {
        $(RATE_INPUT_FILED)
                .val(interestRate)
                .pressEnter();
        values.setBankInterest($(SET_RATE_VALUE).getValue());
    }

    public void assertBankInterest(String interest) {
        float parseRate = Float.parseFloat(interest);
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.UP);
        assertThat(df.format(Float.parseFloat(values.getBankInterest()))).isEqualTo(df.format(parseRate));
    }

    public void getDateField() {
        LocalDate parseDate;
        String dateField = $(END_DATE_INPUT).getValue();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            assert dateField != null;
            parseDate = LocalDate.parse(dateField, formatter);
        } catch (Exception ignored) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
            assert dateField != null;
            parseDate = LocalDate.parse(dateField, formatter);
        }
        values.setDate(parseDate);
    }

    public void setDateField(String setDate) {
        $(END_DATE_INPUT).click();
        executeJavaScript("document.getElementById('end_date_input').value='" + setDate + "'");
        $(END_DATE_INPUT).pressEnter();
        getDateField();
    }

    public void assertDate(String date) {
        if ("today".equals(date)) {
            ZoneId id = ZoneId.of("GMT");
            assertThat(values.getDate()).isEqualTo(LocalDate.now(id));
        } else {
            assertThat(values.getDate()).isEqualTo(parser.parseDate(date));
        }
    }

    public void assertOwnCurrency(String currency) {
        assertThat(currency).isEqualTo(values.getOwnCurrency());
    }

    public void assertNeededCurrency(String currency) {
        assertThat(currency).isEqualTo(values.getConvertCurrency());
    }

    public void assertConvertedAmount(String condition) {
        double threshold;
        double interest = Double.parseDouble(values.getBankInterest());
        double afterInterest = Double.parseDouble($(CURRENCY_CONVERTED_AMOUNT).getValue().replaceAll(",", ""));

        String[] div = Double.toString(afterInterest).split("\\.");
        if ("0".equals(div[1])) {
            threshold = 1;
        } else {
            threshold = 0.1;
        }

        double beforeInterest;
        if ("converted".equals(condition)) {
            beforeInterest = Double.parseDouble(values.getConvertedAmount());
        } else {
            if (interest > 0) {
                setBankInterest("0");
                beforeInterest = Double.parseDouble($(CURRENCY_CONVERTED_AMOUNT).getValue().replaceAll(",", ""));
            } else {
                beforeInterest = afterInterest;
            }
        }

        double estimate = beforeInterest - (beforeInterest * (interest / 100));

        assertThat(Math.abs(estimate - afterInterest)).isLessThan(threshold);
    }

    public void setPredefinedRate(String rate) {
        $(RATE_DROPDOWN_CARET).click();
        $(RATE_DROPDOWN_LIST).shouldBe(visible);
        $("#interbank_rate_list_container div:nth-child(" + (Integer.parseInt(rate) + 1) + ")").click();
        values.setBankInterest($(SET_RATE_VALUE).getValue());
    }
}


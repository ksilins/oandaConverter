package com.oanda.stepDefs;

import com.codeborne.selenide.Configuration;
import com.oanda.pages.Converter;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class ConverterSteps {

    private final Converter converter = new Converter();

    @Before
    public void setUp() {
        closeWebDriver();

        Configuration.baseUrl = "https://www1.oanda.com/currency/converter/";
        Configuration.browser = "Chrome";
        Configuration.timeout = 5000;
        Configuration.browserSize = "1366x1000";

        open("");
    }

    @Given("^(.*) is converted to (.*)$")
    public void setCurrencies(String owned, String needed) {
        converter.currencyOwnedSelect(owned);
        converter.assertOwnCurrency(owned);
        converter.currencyNeededSelect(needed);
        converter.assertNeededCurrency(needed);
    }

    @When("^amount to convert is set to (.*)$")
    public void amountToConvertIsSetTo(String amount) {
        converter.currencyAmountInput(amount);
        converter.assertOwnCurrencyAmount(amount);
    }

    @And("^bank interest is (.*)%$")
    public void bankInterestIs(String interest) {
        converter.setBankInterest(interest);
        converter.assertBankInterest(interest);
    }

    @And("^date is set to (.*)$")
    public void dateIs(String dateSet) {
        converter.setDateField(dateSet);
    }

    @Then("^date should be set to (.*)$")
    public void dateIsShouldBeToday(String dateExpected) {
        converter.assertDate(dateExpected);
    }

    @Then("^(.*) amount is calculated correctly$")
    public void convertedAmountIsCalculatedCorrectly(String condition) {
        converter.assertConvertedAmount(condition);
    }

    @Then("currency is flipped")
    public void currencyIsFlipped() {
        converter.currencyFlip();
        converter.assertCurrencyFlip();
    }

    @And("^predefined interest is (.*)%$")
    public void predefinedInterestIs(String rate) {
        converter.setPredefinedRate(rate);
        converter.assertBankInterest(rate);
    }
}

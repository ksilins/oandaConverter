package com.oanda.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class ConverterValues {

    String ownCurrency;
    String convertCurrency;
    BigDecimal ownAmount;
    BigDecimal convertedAmount;
    BigDecimal bankInterest = new BigDecimal(0);
    LocalDate date;

    public BigDecimal getBankInterest() {
        return bankInterest;
    }

    public void setBankInterest(String bankInterest) {
        float parseRate = Float.parseFloat(bankInterest);
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.UP);
        this.bankInterest = new BigDecimal(df.format(parseRate));
    }

    public String getOwnCurrency() {
        return ownCurrency;
    }

    public void setOwnCurrency(String ownCurrency) {
        this.ownCurrency = ownCurrency;
    }

    public String getConvertCurrency() {
        return convertCurrency;
    }

    public void setConvertCurrency(String convertCurrency) {
        this.convertCurrency = convertCurrency;
    }

    public BigDecimal getOwnAmount() {
        return ownAmount;
    }

    public void setOwnAmount(BigDecimal ownAmount) {
        this.ownAmount = ownAmount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

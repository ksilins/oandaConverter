package com.oanda.models;

import java.time.LocalDate;

public class ConverterValues {

    String ownCurrency;
    String convertCurrency;
    String ownAmount;
    String convertedAmount;
    String bankInterest = "0";
    LocalDate date;

    public String getBankInterest() {
        return bankInterest;
    }

    public void setBankInterest(String bankInterest) {
        this.bankInterest = bankInterest;
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

    public String getOwnAmount() {
        return ownAmount;
    }

    public void setOwnAmount(String ownAmount) {
        this.ownAmount = ownAmount.replaceAll(",", "");
    }

    public String getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(String convertedAmount) {
        this.convertedAmount = convertedAmount.replaceAll(",", "");
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

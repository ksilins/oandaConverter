package com.oanda.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelpers {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public LocalDate parseDate(String date) {
        LocalDate parseDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        parseDate = LocalDate.parse(date, formatter);
        return parseDate;
    }

    public static BigDecimal estimatedConvertedAmount(BigDecimal base, BigDecimal pct) {
        return base.subtract(base.multiply(pct).divide(ONE_HUNDRED));
    }
}

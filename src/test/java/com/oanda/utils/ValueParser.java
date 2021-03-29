package com.oanda.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValueParser {

    public LocalDate parseDate(String date) {
        LocalDate parseDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        parseDate = LocalDate.parse(date, formatter);
        return parseDate;
    }
}

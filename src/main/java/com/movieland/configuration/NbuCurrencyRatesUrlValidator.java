package com.movieland.configuration;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.StringJoiner;

@Component
public class NbuCurrencyRatesUrlValidator {

    private final StringJoiner stringJoiner = new StringJoiner("");


    public String validateUrl(boolean nextDay, String urlToValidate) {

        String url = urlToValidate;
        String date = "?date=" + LocalDate.now().toString().replace("-", "") + "&json";
        if (nextDay) {
            date = "?date=" + LocalDate.now().plusDays(1).toString().replace("-", "") + "&json";
        }
        stringJoiner.add(url);
        stringJoiner.add(date);
        return stringJoiner.toString();
    }

}



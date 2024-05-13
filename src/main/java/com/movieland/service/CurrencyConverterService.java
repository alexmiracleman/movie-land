package com.movieland.service;

import com.movieland.common.Currency;

public interface CurrencyConverterService {

    default double convertFromUah(double price, Currency toCurrency) {
        return convert(price, toCurrency);
    }

    double convert(double price, Currency currency);
}

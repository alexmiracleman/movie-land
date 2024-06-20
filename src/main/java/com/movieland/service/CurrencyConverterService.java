package com.movieland.service;

import com.movieland.common.Currency;

public interface CurrencyConverterService {

    double convert(double priceInUah, Currency toCurrency);
}

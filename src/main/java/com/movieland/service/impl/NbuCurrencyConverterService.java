package com.movieland.service.impl;

import com.movieland.controller.validation.Currency;
import com.movieland.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class NbuCurrencyConverterService implements CurrencyConverterService {

    private final CurrencyRateCache currencyRateCache;


    @Override
    public double convert(double price, Currency currency) {
        double rate = currencyRateCache.getRate(currency);
        return new BigDecimal(price / rate).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}

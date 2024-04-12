package com.movieland.service.impl;

import com.movieland.configuration.NbuCurrencyRatesApi;
import com.movieland.controller.validation.Currency;
import com.movieland.dto.NbuRateDto;
import com.movieland.common.annotations.Cache;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Cache
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateCache {

    private final NbuCurrencyRatesApi nbuCurrencyRatesApi;

    private List<NbuRateDto> cachedRatesForToday = new ArrayList<>();
    private List<NbuRateDto> cachedRatesForTomorrow = new ArrayList<>();


    public double getRate(Currency currency) {
        for (NbuRateDto nbuRateDto : cachedRatesForToday) {
            if (nbuRateDto.getCc().equalsIgnoreCase(currency.toString())) {
                return nbuRateDto.getRate();
            }
        }
        return -1;
    }


    //Generating rates for the current day
    @PostConstruct
    private void generateCacheForToday() {
        log.info("Generating currency cache for the current day");
        cachedRatesForToday = nbuCurrencyRatesApi.getAllCurrencyRates(false);
        if (LocalTime.now().isAfter(LocalTime.of(16, 0, 0))) {
            generateCacheForTomorrow();
        }
    }

    //Generating rates for the next day
    @Scheduled(cron = "0 0 16 * * *")
    private void generateCacheForTomorrow() {
        log.info("Generating currency cache for the next day");
        cachedRatesForTomorrow = nbuCurrencyRatesApi.getAllCurrencyRates(true);
    }

    //Midnight rates update
    @Scheduled(cron = "0 0 0 * * ?")
    private void invalidateCache() {
        log.info("Updating currency cache for the current day");
        cachedRatesForToday = cachedRatesForTomorrow;
    }

}

package com.movieland.service.impl;

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

    private final NbuCurrencyRatesService nbuCurrencyRatesService;

    private List<NbuRateDto> cachedRatesForToday = new ArrayList<>();
    private List<NbuRateDto> cachedRatesForTomorrow = new ArrayList<>();


    public double getRate(Currency currency) {
        for (NbuRateDto nbuRateDto : cachedRatesForToday) {
            if (nbuRateDto.getCurrency().equalsIgnoreCase(currency.toString())) {
                return nbuRateDto.getRate();
            }
        }
        return -1;
    }


    //Generating rates for the current day
    @PostConstruct
    private void generateCacheForToday() {
        log.info("Generating currency cache for the current day");
        cachedRatesForToday = nbuCurrencyRatesService.getAllCurrencyRatesToday();
        if (LocalTime.now().isAfter(LocalTime.of(17, 0, 0))) {
            generateCacheForTomorrow();
        }
    }

    //Generating rates for the next day
    @Scheduled(cron = "${currency.cache.tomorrow}")
    private void generateCacheForTomorrow() {
        log.info("Generating currency cache for the next day");
        cachedRatesForTomorrow = nbuCurrencyRatesService.getAllCurrencyRatesTomorrow();
    }

    //Midnight rates update
    @Scheduled(cron = "${currency.cache.invalidate}")
    private void invalidateCache() {
        log.info("Updating currency cache for the current day");
        cachedRatesForToday = cachedRatesForTomorrow;
    }

}

package com.movieland.configuration;

import com.movieland.dto.NbuRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class NbuCurrencyRatesApi {

    private final NbuRestTemplate nbuRestTemplate;
    private final NbuCurrencyRatesUrlValidator nbuCurrencyRatesUrlValidator;

    @Value("${nbuRatesUrl}")
    private String nbuRatesUrl;

    public List<NbuRateDto> getAllCurrencyRates(boolean nextDay) {

        String validatedUrl = nbuCurrencyRatesUrlValidator.validateUrl(nextDay, nbuRatesUrl);

        return nbuRestTemplate.getListOfRates(validatedUrl);
    }

}



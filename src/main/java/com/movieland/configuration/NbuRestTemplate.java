package com.movieland.configuration;

import com.movieland.dto.NbuRateDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class NbuRestTemplate {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<NbuRateDto> getListOfRates(String url) {
        NbuRateDto[] NbuRates = restTemplate.getForObject(url, NbuRateDto[].class);
        return Arrays.asList(NbuRates);
    }

}

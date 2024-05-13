package com.movieland.configuration;

import com.movieland.service.impl.NbuCurrencyRatesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public NbuCurrencyRatesService currencyService(RestClient restClient) {
        return new NbuCurrencyRatesService(restClient);
    }


    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}

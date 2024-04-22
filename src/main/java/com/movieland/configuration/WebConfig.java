package com.movieland.configuration;

import com.movieland.controller.validation.StringToEnumSortOrderPriceConverter;
import com.movieland.controller.validation.StringToEnumSortOrderRatingConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumSortOrderPriceConverter());
        registry.addConverter(new StringToEnumSortOrderRatingConverter());
    }
}

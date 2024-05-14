package com.movieland.configuration;

import com.movieland.web.LogHandlerInterceptor;
import com.movieland.service.impl.JwtService;
import com.movieland.web.controller.validation.StringToEnumSortOrderPriceConverter;
import com.movieland.web.controller.validation.StringToEnumSortOrderRatingConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtService jwtService;


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumSortOrderPriceConverter());
        registry.addConverter(new StringToEnumSortOrderRatingConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogHandlerInterceptor(jwtService));

    }

}

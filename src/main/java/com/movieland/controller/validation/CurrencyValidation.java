package com.movieland.controller.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum CurrencyValidation {

    USD("USD"),
    EUR("EUR");
    private final String name;

    public static String validate(String type) {
        for (CurrencyValidation currencyValidation : values()) {
            if (currencyValidation.name.equalsIgnoreCase(type)) {
                return currencyValidation.name;
            }
        }
        return null;
    }
}

package com.movieland.web.controller.validation;

import org.springframework.core.convert.converter.Converter;

public class StringToEnumSortOrderPriceConverter implements Converter<String, SortOrderPrice> {
    @Override
    public SortOrderPrice convert(String source) {
        return SortOrderPrice.valueOf(source.toUpperCase());
    }
}

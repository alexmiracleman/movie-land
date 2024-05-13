package com.movieland.web.controller.validation;

import org.springframework.core.convert.converter.Converter;

public class StringToEnumSortOrderRatingConverter implements Converter<String, SortOrderRating> {
    @Override
    public SortOrderRating convert(String source) {
        return SortOrderRating.valueOf(source.toUpperCase());
    }
}

package com.movieland.mapper;

import com.movieland.dto.CountryDto;
import com.movieland.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CountryMapper {
    List<CountryDto> toCountryDto(List<Country> countries);
}

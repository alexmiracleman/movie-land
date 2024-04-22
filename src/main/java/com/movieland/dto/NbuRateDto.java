package com.movieland.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NbuRateDto {

    private double rate;

    @JsonProperty("cc")
    private String currency;

}
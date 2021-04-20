package org.PredictionRestApi.webclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter


public class PredictionDataDto {
    @JsonProperty("data")
    private List<PredictionDto> data;
}

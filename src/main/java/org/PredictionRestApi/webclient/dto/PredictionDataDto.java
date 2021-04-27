package org.PredictionRestApi.webclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PredictionDataDto {
    @JsonProperty("data")
    private List<PredictionDto> data;
}

package org.PredictionRestApi.webclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OddsDto {
    @JsonProperty("1")
    private Double won1;
    @JsonProperty("X")
    private Double draw;
    @JsonProperty("2")
    private Double won2;
    @JsonProperty("1X")
    private Double won1Draw;
    @JsonProperty("X2")
    private Double won2Draw;
    @JsonProperty("12")
    private Double won1Won2;
}

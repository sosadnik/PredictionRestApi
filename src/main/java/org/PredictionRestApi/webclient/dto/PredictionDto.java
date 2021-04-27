package org.PredictionRestApi.webclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionDto {
    private String home_team;
    private String away_team;
    private String competition_name;
    private String prediction;
    private String competition_cluster;
    private String status;
    private String result;
    private Date date;
    private OddsDto odds;

    public void setDate(Date date) {
        this.date = date;
    }
}

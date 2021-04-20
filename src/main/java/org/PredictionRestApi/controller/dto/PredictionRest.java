package org.PredictionRestApi.controller.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;


@Builder
@Data
public class PredictionRest {

    private String homeTeam;
    private String awayTeam;
    private String competitionName;
    private String prediction;
    private String competitionCluster;
    private String status;
    private String result;
    private Date date;
    private Double odds;


}


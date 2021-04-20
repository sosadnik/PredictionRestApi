package org.PredictionRestApi.controller;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.controller.dto.PredictionRest;
import org.PredictionRestApi.entity.AwayTeamEfficiency;
import org.PredictionRestApi.entity.CompetitionEfficiency;
import org.PredictionRestApi.entity.HomeTeamEfficiency;
import org.PredictionRestApi.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class PredictionController {

    private final PredictionService predictionService;
    private final CompetitionEfficiencyService competitionService;
    private final HomeTeamEfficiencyService homeTeamEfficiencyService;
    private final AwayTeamEfficiencyService awayTeamEfficiencyService;
    private final MainService mainService;

    @GetMapping("/getPrediction")
    public List<PredictionRest> getPrediction() throws ParseException {
        if (!predictionService.isUpdate()) {
            mainService.setup();
        }
        return predictionService.getPrediction();
    }

    @GetMapping("getHomeTeam/{name}")
    public HomeTeamEfficiency getEfficiencyForHomeTeam(@PathVariable String name) {
        return homeTeamEfficiencyService.getEfficiencyForTeam(name);
    }

    @GetMapping("getCompetition/{name}")
    public CompetitionEfficiency getEfficiencyForCompetition(@PathVariable String name) {
        return competitionService.getEfficiencyForCompetition(name);
    }

    @GetMapping("getAwayTeam/{name}")
    public AwayTeamEfficiency getEfficiencyForAwayTeam(@PathVariable String name){
        return awayTeamEfficiencyService.getEfficiencyForTeam(name);
    }
}

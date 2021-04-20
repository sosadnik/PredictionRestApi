package org.PredictionRestApi.service;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.repository.PredictionRepository;
import org.PredictionRestApi.service.component.FilterSet;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class MainService {
    private final PredictionService predictionService;
    private final PredictionRepository predictionRepository;
    private final CompetitionEfficiencyService competitionEfficiencyService;
    private final HomeTeamEfficiencyService homeTeamEfficiencyService;
    private final AwayTeamEfficiencyService awayTeamEfficiencyService;
    private final FilterSet filterSet;


    public void setup() throws ParseException {
        //get prediction from other rest api
        predictionService.dataToBaseRequest(filterSet.getDay(), filterSet.getDay(), filterSet.getMonth());

        //update Prediction with status pending
        predictionService.update();

        //update Competition
        for (String name : competitionEfficiencyService.getListCompetitionName(predictionRepository.findAll())) {
            competitionEfficiencyService.efficiencyBuild(filterSet.filterPredictionByCompetitionName(name));
        }

        //update HomeTeam
        for (String name : homeTeamEfficiencyService.getListTeamName(predictionRepository.findAll())) {
            homeTeamEfficiencyService.efficiencyBuild(filterSet.filterPredictionByHomeTeamName(name));
        }

        //update AwayTeam
        for (String name : awayTeamEfficiencyService.getListTeamName(predictionRepository.findAll())) {
            awayTeamEfficiencyService.efficiencyBuild(filterSet.filterPredictionByAwayTeamName(name));
        }
    }



}

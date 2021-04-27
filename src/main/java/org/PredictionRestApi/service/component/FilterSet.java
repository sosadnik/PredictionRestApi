package org.PredictionRestApi.service.component;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.AwayTeamEfficiencyRepository;
import org.PredictionRestApi.repository.CompetitionEfficiencyRepository;
import org.PredictionRestApi.repository.HomeTeamEfficiencyRepository;
import org.PredictionRestApi.repository.PredictionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilterSet {

    private final CompetitionEfficiencyRepository competitionEfficiencyRepository;
    private final HomeTeamEfficiencyRepository homeTeamEfficiencyRepository;
    private final AwayTeamEfficiencyRepository awayTeamEfficiencyRepository;
    private final PredictionRepository repository;
    private final static Double MINIMAL_EFFICIENCY_FOR_COMPETITION = 70.0;
    private final static Double MINIMAL_EFFICIENCY_FOR_TEAM = 60.0;


//awayTeam and homeTeam filters are useful with approximately 4000 records in the predictions table
    public List<Prediction> filteringForMinimumEfficiency() {
        return getTodayPredictions().stream()
                .filter(prediction -> competitionEfficiencyRepository.findByCompetitionName(prediction.getCompetitionName()).getEfficacy() > MINIMAL_EFFICIENCY_FOR_COMPETITION)
               // .filter(prediction -> homeTeamEfficiencyRepository.findByTeamName(prediction.getHomeTeam()).getEfficacy() > MINIMAL_EFFICIENCY_FOR_TEAM)
               // .filter(prediction -> awayTeamEfficiencyRepository.findByTeamName(prediction.getAwayTeam()).getEfficacy() > MINIMAL_EFFICIENCY_FOR_TEAM)
                .collect(Collectors.toList());
    }
    public List<Prediction> getTodayPredictions() {
        return repository.findAll().stream()
                .filter(prediction -> prediction.getDate().toInstant().isAfter(new Date().toInstant().plusSeconds(-86400)))
                .collect(Collectors.toList());
    }

    public List<Prediction> filterPredictionByCompetitionName(String name) {
        return repository.findAll().stream()
                .filter(prediction -> prediction.getCompetitionName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Prediction> filterPredictionByHomeTeamName(String name) {
        return repository.findAll().stream()
                .filter(prediction -> prediction.getHomeTeam().equals(name))
                .collect(Collectors.toList());
    }

    public List<Prediction> filterPredictionByAwayTeamName(String name) {
        return repository.findAll().stream()
                .filter(prediction -> prediction.getAwayTeam().equals(name))
                .collect(Collectors.toList());
    }

    public List<Date> getDatesWithoutRepeating(List<Prediction> predictionsList) {
        return predictionsList.stream()
                .map(Prediction::getDate)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Prediction> getPredictionsWithStatusPending() {
        return repository.findByStatus("pending").stream()
                .filter(prediction -> prediction.getDate().toInstant().isBefore(new Date().toInstant().plusSeconds(-172800)))
                .collect(Collectors.toList());
    }

    public int getDay() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfMonth();
    }

    public int getMonth() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue();
    }

    public int getYear(){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear();
    }
}

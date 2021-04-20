package org.PredictionRestApi.service;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.controller.dto.PredictionRest;
import org.PredictionRestApi.entity.Odds;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.*;
import org.PredictionRestApi.service.component.FilterSet;
import org.PredictionRestApi.webclient.PredictionsClient;
import org.PredictionRestApi.webclient.dto.PredictionDataDto;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionsClient predictionsClient;
    private final PredictionRepository repository;
    private final OddsService oddsService;
    private final FilterSet filterSet;


    public void update() {
        List<Prediction> predictionsWithStatusPending = filterSet.getPredictionsWithStatusPending();

        saveUpdate(predictionsWithStatusPending, filterSet.getDatesWithoutRepeating(predictionsWithStatusPending));
    }

    private void saveUpdate(List<Prediction> predictionsList, List<Date> dateList) {
        for (Date date : dateList) {
            PredictionDataDto dto = predictionsClient.getPrediction(date.toString().substring(0, 10));

            for (Prediction prediction : predictionsList) {
                for (int i = 0; i < dto.getData().size(); i++) {
                    if (prediction.getHomeTeam().equals(dto.getData().get(i).getHome_team()) &
                            prediction.getAwayTeam().equals(dto.getData().get(i).getAway_team())) {
                        prediction.setStatus(dto.getData().get(i).getStatus());
                        prediction.setResult(dto.getData().get(i).getResult());
                        repository.save(prediction);
                    }
                }
            }
        }
    }

    public List<PredictionRest> getPrediction() {
        List<PredictionRest> list = new ArrayList<>();
        for (Prediction prediction : filterSet.filteringForMinimumEfficiency()) {
            list.add(PredictionRest.builder()
                    .homeTeam(prediction.getHomeTeam())
                    .awayTeam(prediction.getAwayTeam())
                    .competitionName(prediction.getCompetitionName())
                    .prediction(prediction.getPrediction())
                    .competitionCluster(prediction.getCompetitionCluster())
                    .status(prediction.getStatus())
                    .date(prediction.getDate())
                    .odds(oddsService.getOdds(prediction.getId(), prediction.getPrediction()))
                    .build());
        }
        return list;
    }

    public void dataToBaseRequest(int dayFrom, int dayTo, int monthFrom) throws ParseException {
        String year = "2021";
        for (int day = dayFrom; day <= dayTo; day++) {
            String monthString, dayString;
            if (monthFrom > 9) monthString = String.valueOf(monthFrom);
            else monthString = "0" + String.valueOf(monthFrom);
            if (day > 9) dayString = String.valueOf(day);
            else dayString = "0" + String.valueOf(day);

            save(year + "-" + monthString + "-" + dayString);
        }
    }

    public void save(String dateForRequest) throws ParseException {
        PredictionDataDto predictionData = predictionsClient.getPrediction(dateForRequest);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateForRequest);

        for (int i = 0; predictionData.getData().size() > i; i++) {
            if (!repository.existsByHomeTeamAndDate(predictionData.getData().get(i).getHome_team(), date)) {
                Prediction prediction = Prediction.builder()
                        .awayTeam(predictionData.getData().get(i).getAway_team())
                        .homeTeam(predictionData.getData().get(i).getHome_team())
                        .competitionName(predictionData.getData().get(i).getCompetition_name())
                        .prediction(predictionData.getData().get(i).getPrediction())
                        .competitionCluster(predictionData.getData().get(i).getCompetition_cluster())
                        .status(predictionData.getData().get(i).getStatus())
                        .result(predictionData.getData().get(i).getResult())
                        .date(date)
                        .odds(Odds.builder()
                                .won1(predictionData.getData().get(i).getOdds().getWon1())
                                .draw(predictionData.getData().get(i).getOdds().getDraw())
                                .won2(predictionData.getData().get(i).getOdds().getWon2())
                                .won1Draw(predictionData.getData().get(i).getOdds().getWon1Draw())
                                .won2Draw(predictionData.getData().get(i).getOdds().getWon2Draw())
                                .won1Won2(predictionData.getData().get(i).getOdds().getWon1Won2())
                                .build()).build();

                repository.save(prediction);
            }
        }
    }

    public boolean isUpdate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = dateFormat.format(date);
        date = dateFormat.parse(format);
        return repository.existsByDate(date);
    }
}


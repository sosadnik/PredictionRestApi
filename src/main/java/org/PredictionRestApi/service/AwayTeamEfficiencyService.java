package org.PredictionRestApi.service;


import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.entity.AwayTeamEfficiency;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.AwayTeamEfficiencyRepository;
import org.PredictionRestApi.service.component.EfficiencyCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwayTeamEfficiencyService {

    private final AwayTeamEfficiencyRepository repository;
    private final EfficiencyCalculator efficiencyCalculator;

    public void efficiencyBuild(List<Prediction> list) {
        Map<String, Double> statistics = efficiencyCalculator.getEfficiency(list);

        save(AwayTeamEfficiency.builder()
                .teamName(list.stream()
                        .findFirst()
                        .get().getAwayTeam())
                .won(statistics.get("won").intValue())
                .lost(statistics.get("lost").intValue())
                .other(statistics.get("other").intValue())
                .efficacy(statistics.get("effectiveness"))
                .build());
    }


    public List<String> getListTeamName(List<Prediction> predictions) {
        return predictions.stream()
                .map(Prediction::getAwayTeam)
                .distinct()
                .collect(Collectors.toList());
    }

    public AwayTeamEfficiency getEfficiencyForTeam(String name) {
        return repository.findByTeamName(name);
    }

    public void save(AwayTeamEfficiency entity) {
        if (repository.existsByTeamName(entity.getTeamName())) {
            entity.setId(repository.findByTeamName(entity.getTeamName()).getId());
            repository.save(entity);
        } else if (!repository.existsByTeamName(entity.getTeamName())) {
            repository.save(entity);
        }
    }

}

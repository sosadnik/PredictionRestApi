package org.PredictionRestApi.service;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.entity.HomeTeamEfficiency;
import org.PredictionRestApi.repository.HomeTeamEfficiencyRepository;
import org.PredictionRestApi.service.component.EfficiencyCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeTeamEfficiencyService {

    private final HomeTeamEfficiencyRepository repository;
    private final EfficiencyCalculator efficiencyCalculator;


    public void efficiencyBuild(List<Prediction> list) {
        Map<String, Double> statistics = efficiencyCalculator.getEfficiency(list);

        save(HomeTeamEfficiency.builder()
                .teamName(list.stream()
                        .findFirst()
                        .get().getHomeTeam())
                .won(statistics.get("won").intValue())
                .lost(statistics.get("lost").intValue())
                .other(statistics.get("other").intValue())
                .efficacy(statistics.get("effectiveness"))
                .build());
    }

    public List<String> getListTeamName(List<Prediction> predictions) {
        return predictions.stream()
                .map(Prediction::getHomeTeam)
                .distinct()
                .collect(Collectors.toList());
    }

    public HomeTeamEfficiency getEfficiencyForTeam(String name) {
        return repository.findByTeamName(name);
    }

    public void save(HomeTeamEfficiency entity) {
        if (repository.existsByTeamName(entity.getTeamName())) {
            entity.setId(repository.findByTeamName(entity.getTeamName()).getId());
            repository.save(entity);
        } else if (!repository.existsByTeamName(entity.getTeamName())) {
            repository.save(entity);
        }
    }
}

package org.PredictionRestApi.service;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.entity.CompetitionEfficiency;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.CompetitionEfficiencyRepository;
import org.PredictionRestApi.service.component.EfficiencyCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionEfficiencyService {

    private final CompetitionEfficiencyRepository repository;
    private final EfficiencyCalculator efficiencyCalculator;


    public void efficiencyBuild(List<Prediction> list) {
        Map<String, Double> statistics = efficiencyCalculator.getEfficiency(list);

        save(CompetitionEfficiency.builder()
                .competitionName(list.stream()
                        .findFirst()
                        .get().getCompetitionName())
                .won(statistics.get("won").intValue())
                .lost(statistics.get("lost").intValue())
                .other(statistics.get("other").intValue())
                .efficacy(statistics.get("effectiveness"))
                .build());
    }

    public List<String> getListCompetitionName(List<Prediction> predictions){
        return predictions.stream()
                .map(Prediction::getCompetitionName)
                .distinct()
                .collect(Collectors.toList());
    }

    public void save(CompetitionEfficiency entity) {
        if (repository.existsByCompetitionName(entity.getCompetitionName())) {
            entity.setId(repository.findByCompetitionName(entity.getCompetitionName()).getId());
            repository.save(entity);
        } else if (!repository.existsByCompetitionName(entity.getCompetitionName())) {
            repository.save(entity);
        }
    }

    public CompetitionEfficiency getEfficiencyForCompetition(String name){
        return repository.findByCompetitionName(name);
    }

}

package org.PredictionRestApi.service.component;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.entity.Prediction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EfficiencyCalculator {

    public Map<String, Double> getEfficiency(List<Prediction> list) {
        double won = 0, lost = 0, other = 0, i = 0;
        for (Prediction prediction : list) {
            if (prediction.getStatus().equals("won")) won++;
            else if (prediction.getStatus().equals("lost")) lost++;
            else other++;
            i++;
        }

        Map<String, Double> statistics = new HashMap<>();
        statistics.put("won", won);
        statistics.put("lost", lost);
        statistics.put("other", other);
        statistics.put("effectiveness", getEffectiveness(won, other, i));

        return statistics;
    }

    public double getEffectiveness(double won, double other, double i) {
        if (i - other != 0) {
            return (won / (i - other)) * 100;
        } else {
            return 0.0;
        }
    }
}

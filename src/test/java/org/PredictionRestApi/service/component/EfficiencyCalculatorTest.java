package org.PredictionRestApi.service.component;

import org.PredictionRestApi.entity.Prediction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class EfficiencyCalculatorTest {

    private EfficiencyCalculator efficiencyCalculator = new EfficiencyCalculator();

    @Test
    public void getEfficiency_WhenInputList_ShouldReturnMapTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder()
                .status("won")
                .build());
        predictionList.add(Prediction.builder()
                .status("won")
                .build());
        predictionList.add(Prediction.builder()
                .status("pending")
                .build());
        predictionList.add(Prediction.builder()
                .status("lost")
                .build());
        predictionList.add(Prediction.builder()
                .status("lost")
                .build());
        Map<String, Double> expected = new HashMap<>();
        expected.put("won", 2.0);
        expected.put("lost", 2.0);
        expected.put("other", 1.0);
        expected.put("effectiveness", 50.0);

        Map<String, Double> actual = efficiencyCalculator.getEfficiency(predictionList);

        assertEquals(expected, actual);
    }

    @Test
    public void getEfficiency_WhenInputEmptyList_ShouldReturnMapTest() {
        List<Prediction> predictionList = new ArrayList<>();
        Map<String, Double> expected = new HashMap<>();
        expected.put("won", 0.0);
        expected.put("lost", 0.0);
        expected.put("other", 0.0);
        expected.put("effectiveness", 0.0);

        Map<String, Double> actual = efficiencyCalculator.getEfficiency(predictionList);

        assertEquals(expected, actual);
    }

    @Test
    public void getEffectiveness_WhenInputDoubleValue_ShouldReturnDoubleTest() {
        double won = 46, other = 10, i = 60, expected = 92;

        double actual = efficiencyCalculator.getEffectiveness(won, other, i);

        assertEquals(expected,actual,1);
    }
}

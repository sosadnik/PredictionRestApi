package org.PredictionRestApi.service;

import org.PredictionRestApi.entity.CompetitionEfficiency;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.CompetitionEfficiencyRepository;
import org.PredictionRestApi.service.component.EfficiencyCalculator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CompetitionEfficiencyServiceTest {

    private CompetitionEfficiencyRepository repository;
    private EfficiencyCalculator efficiencyCalculator;
    private CompetitionEfficiencyService competitionEfficiencyService;

    @Before
    public void setup() {
        repository = mock(CompetitionEfficiencyRepository.class);
        efficiencyCalculator = mock(EfficiencyCalculator.class);
        competitionEfficiencyService = new CompetitionEfficiencyService(repository, efficiencyCalculator);

    }

    @Test
    public void getListCompetitionName_WhenInputList_ShouldReturnListWithTwoItemsTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().competitionName("competition1").build());
        predictionList.add(Prediction.builder().competitionName("competition1").build());
        predictionList.add(Prediction.builder().competitionName("competition2").build());
        predictionList.add(Prediction.builder().competitionName("competition2").build());
        List<String> expected = new ArrayList<>();
        expected.add("competition1");
        expected.add("competition2");

        List<String> actual = competitionEfficiencyService.getListCompetitionName(predictionList);

        assertEquals(expected, actual);
    }

    @Test
    public void efficiencyBuild_WhenInputList_ShouldCallFunctionTest() {
        List<Prediction> list = mock(ArrayList.class);
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().awayTeam("name1").build());
        Map<String, Double> statistics = new HashMap<>();
        statistics.put("won", 2.);
        statistics.put("lost", 1.);
        statistics.put("other", 1.);
        statistics.put("effectiveness", 50.);

        when(efficiencyCalculator.getEfficiency(Mockito.anyList())).thenReturn(statistics);
        when(list.stream()).thenReturn(predictionList.stream());
        competitionEfficiencyService.efficiencyBuild(list);

        verify(list, times(1)).stream();


    }

    @Test
    public void save_WhenFunctionExistsReturnFalse_ShouldCallFunctionTest() {
        CompetitionEfficiency efficiency = new CompetitionEfficiency();
        CompetitionEfficiency.builder()
                .competitionName("name")
                .efficacy(50.)
                .lost(1)
                .won(2)
                .other(1)
                .build();

        when(repository.existsByCompetitionName(anyString())).thenReturn(false);
        competitionEfficiencyService.save(efficiency);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void save_WhenFunctionExistsReturnTrue_ShouldCallFunctionTest() {
        CompetitionEfficiency efficiency = new CompetitionEfficiency();
        CompetitionEfficiency.builder()
                .competitionName("name")
                .efficacy(50.)
                .lost(1)
                .won(2)
                .other(1)
                .build();

        when(repository.existsByCompetitionName(anyString())).thenReturn(true);
        competitionEfficiencyService.save(efficiency);

        verify(repository, times(1)).save(any());

    }

}

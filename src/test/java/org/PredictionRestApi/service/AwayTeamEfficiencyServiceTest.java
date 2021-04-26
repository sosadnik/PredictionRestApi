package org.PredictionRestApi.service;

import org.PredictionRestApi.entity.AwayTeamEfficiency;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.AwayTeamEfficiencyRepository;
import org.PredictionRestApi.service.component.EfficiencyCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AwayTeamEfficiencyServiceTest {
    private AwayTeamEfficiencyService awayTeamEfficiencyService;
    private AwayTeamEfficiencyRepository repository;
    private EfficiencyCalculator efficiencyCalculator;

    @Before
    public void setup() {
        repository = mock(AwayTeamEfficiencyRepository.class);
        efficiencyCalculator = mock(EfficiencyCalculator.class);
        awayTeamEfficiencyService = new AwayTeamEfficiencyService(repository, efficiencyCalculator);
    }

    @Test
    public void getListTeamName_WhenInputList_ShouldReturnListWithTwoItemsTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().awayTeam("team1").build());
        predictionList.add(Prediction.builder().awayTeam("team1").build());
        predictionList.add(Prediction.builder().awayTeam("team2").build());
        predictionList.add(Prediction.builder().awayTeam("team2").build());
        List<String> expected = new ArrayList<>();
        expected.add("team1");
        expected.add("team2");

        List<String> actual = awayTeamEfficiencyService.getListTeamName(predictionList);

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
        awayTeamEfficiencyService.efficiencyBuild(list);

        verify(list, times(1)).stream();


    }

    @Test
    public void save_WhenFunctionExistsReturnFalse_ShouldCallFunctionTest() {
        AwayTeamEfficiency efficiency = new AwayTeamEfficiency();
        AwayTeamEfficiency.builder()
                .teamName("name")
                .efficacy(50.)
                .lost(1)
                .won(2)
                .other(1)
                .build();

        when(repository.existsByTeamName(anyString())).thenReturn(false);
        awayTeamEfficiencyService.save(efficiency);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void save_WhenFunctionExistsReturnTrue_ShouldCallFunctionTest(){
        AwayTeamEfficiency efficiency = new AwayTeamEfficiency();
        AwayTeamEfficiency.builder()
                .teamName("name")
                .efficacy(50.)
                .lost(1)
                .won(2)
                .other(1)
                .build();

        when(repository.existsByTeamName(anyString())).thenReturn(true);
        awayTeamEfficiencyService.save(efficiency);

        verify(repository, times(1)).save(any());

    }
}

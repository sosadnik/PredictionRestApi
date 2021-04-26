package org.PredictionRestApi.service;

import org.PredictionRestApi.entity.HomeTeamEfficiency;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.HomeTeamEfficiencyRepository;
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

public class HomeTeamEfficiencyServiceTest {

    private HomeTeamEfficiencyRepository repository;
    private EfficiencyCalculator efficiencyCalculator;
    private HomeTeamEfficiencyService homeTeamEfficiencyService;

    @Before
    public void setup() {
        repository = mock(HomeTeamEfficiencyRepository.class);
        efficiencyCalculator = mock(EfficiencyCalculator.class);
        homeTeamEfficiencyService = new HomeTeamEfficiencyService(repository, efficiencyCalculator);
    }

    @Test
    public void getListTeamName_WhenInputList_ShouldReturnListWithTwoItemsTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().homeTeam("team1").build());
        predictionList.add(Prediction.builder().homeTeam("team1").build());
        predictionList.add(Prediction.builder().homeTeam("team2").build());
        predictionList.add(Prediction.builder().homeTeam("team2").build());
        List<String> expected = new ArrayList<>();
        expected.add("team1");
        expected.add("team2");

        List<String> actual = homeTeamEfficiencyService.getListTeamName(predictionList);

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
        homeTeamEfficiencyService.efficiencyBuild(list);

        verify(list, times(1)).stream();


    }

    @Test
    public void save_WhenFunctionExistsReturnFalse_ShouldCallFunctionTest() {
        HomeTeamEfficiency efficiency = new HomeTeamEfficiency();
        HomeTeamEfficiency.builder()
                .teamName("name")
                .efficacy(50.)
                .lost(1)
                .won(2)
                .other(1)
                .build();

        when(repository.existsByTeamName(anyString())).thenReturn(false);
        homeTeamEfficiencyService.save(efficiency);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void save_WhenFunctionExistsReturnTrue_ShouldCallFunctionTest(){
        HomeTeamEfficiency efficiency = new HomeTeamEfficiency();
        HomeTeamEfficiency.builder()
                .teamName("name")
                .efficacy(50.)
                .lost(1)
                .won(2)
                .other(1)
                .build();

        when(repository.existsByTeamName(anyString())).thenReturn(true);
        homeTeamEfficiencyService.save(efficiency);

        verify(repository, times(1)).save(any());

    }
}


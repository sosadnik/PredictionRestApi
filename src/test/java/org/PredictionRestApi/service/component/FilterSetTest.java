package org.PredictionRestApi.service.component;


import org.PredictionRestApi.entity.CompetitionEfficiency;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.AwayTeamEfficiencyRepository;
import org.PredictionRestApi.repository.CompetitionEfficiencyRepository;
import org.PredictionRestApi.repository.HomeTeamEfficiencyRepository;
import org.PredictionRestApi.repository.PredictionRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilterSetTest {

    private CompetitionEfficiencyRepository competitionEfficiencyRepository;
    private AwayTeamEfficiencyRepository awayTeamEfficiencyRepository;
    private PredictionRepository predictionRepository;
    private FilterSet filterSet;

    @Before
    public void setup() {
        competitionEfficiencyRepository = mock(CompetitionEfficiencyRepository.class);
        HomeTeamEfficiencyRepository homeTeamEfficiencyRepository = mock(HomeTeamEfficiencyRepository.class);
        awayTeamEfficiencyRepository = mock(AwayTeamEfficiencyRepository.class);
        predictionRepository = mock(PredictionRepository.class);
        filterSet = new FilterSet(competitionEfficiencyRepository, homeTeamEfficiencyRepository, awayTeamEfficiencyRepository, predictionRepository);
    }

    @Test
    public void getPredictionsWithStatusPending_WhenInputList_ShouldReturnListWithTwoItemsTest() {
        List<Prediction> predictionList = new ArrayList<>();
        Date date = new Date();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").date(date)
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending2").date(date)
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").date(date)
                .build());
        predictionList.add(Prediction.builder().status("lost").homeTeam("TeamLost1").date(date)
                .build());
        List<Prediction> expected = new ArrayList<>();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").date(date)
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending2").date(date)
                .build());

        when(predictionRepository.findByStatus("pending")).thenReturn(predictionList);
        List<Prediction> actual = filterSet.getPredictionsWithStatusPending();

        assertEquals(expected, actual);
    }

    @Test
    public void getDatesWithoutRepeating_WhenInputList_ShouldReturnListWithTwoItemsTest() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").date(format.parse("2021-04-05"))
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").date(format.parse("2021-04-20"))
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").date(format.parse("2021-04-05"))
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").date(format.parse("2021-04-20"))
                .build());
        List<Date> expected = new ArrayList<>();
        expected.add(format.parse("2021-04-05"));
        expected.add(format.parse("2021-04-20"));

        List<Date> actual = filterSet.getDatesWithoutRepeating(predictionList);

        assertEquals(expected, actual);
    }

    @Test
    public void filterPredictionByAwayTeamName_WhenInputList_ShouldReturnListWithTwoItemsTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").awayTeam("testName")
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending2").awayTeam("testName2")
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").awayTeam("testName")
                .build());
        predictionList.add(Prediction.builder().status("lost").homeTeam("TeamLost1").awayTeam("Name1")
                .build());
        List<Prediction> expected = new ArrayList<>();
        expected.add(Prediction.builder().status("pending").homeTeam("TeamPending1").awayTeam("testName")
                .build());
        expected.add(Prediction.builder().status("won").homeTeam("TeamWon1").awayTeam("testName")
                .build());

        when(predictionRepository.findAll()).thenReturn(predictionList);
        List<Prediction> actual = filterSet.filterPredictionByAwayTeamName("testName");

        assertEquals(expected, actual);
    }

    @Test
    public void filterPredictionByAwayTeamName_WhenInputListWithoutFindingName_ShouldReturnEmptyListTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").awayTeam("testName")
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending2").awayTeam("testName2")
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").awayTeam("testName")
                .build());
        List<Prediction> expected = new ArrayList<>();

        when(predictionRepository.findAll()).thenReturn(predictionList);
        List<Prediction> actual = filterSet.filterPredictionByAwayTeamName("*");

        assertEquals(expected, actual);
    }

    @Test
    public void filterPredictionByHomeTeamName_WhenInputList_ShouldReturnListWithOneItemsTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").awayTeam("testName")
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending2").awayTeam("testName2")
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").awayTeam("testName")
                .build());
        List<Prediction> expected = new ArrayList<>();
        expected.add(Prediction.builder().status("pending").homeTeam("TeamPending2").awayTeam("testName2")
                .build());

        when(predictionRepository.findAll()).thenReturn(predictionList);
        List<Prediction> actual = filterSet.filterPredictionByHomeTeamName("TeamPending2");

        assertEquals(expected, actual);
    }

    @Test
    public void filterPredictionByCompetitionName_WhenInputList_ShouldReturnOneItemsTest() {
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").awayTeam("testName").competitionName("Name1")
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending2").awayTeam("testName2").competitionName("name2")
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").awayTeam("testName").competitionName("findName")
                .build());
        List<Prediction> expected = new ArrayList<>();
        expected.add(Prediction.builder().status("won").homeTeam("TeamWon1").awayTeam("testName").competitionName("findName")
                .build());

        when(predictionRepository.findAll()).thenReturn(predictionList);
        List<Prediction> actual = filterSet.filterPredictionByCompetitionName("findName");

        assertEquals(expected, actual);
    }

    @Test
    public void getTodayPredictions_WhenInputList_ShouldReturnListWithTwoItemsTest() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").date(format.parse("2021-04-05"))
                .build());
        predictionList.add(Prediction.builder().status("pending").homeTeam("TeamPending1").date(today)
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").date(today)
                .build());
        predictionList.add(Prediction.builder().status("won").homeTeam("TeamWon1").date(format.parse("2021-04-20"))
                .build());
        List<Prediction> expected = new ArrayList<>();
        expected.add(Prediction.builder().status("pending").homeTeam("TeamPending1").date(today)
                .build());
        expected.add(Prediction.builder().status("won").homeTeam("TeamWon1").date(today)
                .build());

        when(predictionRepository.findAll()).thenReturn(predictionList);
        List<Prediction> actual = filterSet.getTodayPredictions();

        assertEquals(expected, actual);
    }


}

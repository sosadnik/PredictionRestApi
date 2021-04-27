package org.PredictionRestApi.service;

import org.PredictionRestApi.controller.dto.PredictionRest;
import org.PredictionRestApi.entity.Odds;
import org.PredictionRestApi.entity.Prediction;
import org.PredictionRestApi.repository.PredictionRepository;
import org.PredictionRestApi.service.component.FilterSet;
import org.PredictionRestApi.webclient.PredictionsClient;
import org.PredictionRestApi.webclient.dto.OddsDto;
import org.PredictionRestApi.webclient.dto.PredictionDataDto;
import org.PredictionRestApi.webclient.dto.PredictionDto;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PredictionServiceTest {

    private PredictionsClient predictionsClient;
    private PredictionRepository repository;
    private OddsService oddsService;
    private FilterSet filterSet;
    private PredictionService predictionService;

    @Before
    public void setup() {
        predictionsClient = mock(PredictionsClient.class);
        repository = mock(PredictionRepository.class);
        oddsService = mock(OddsService.class);
        filterSet = mock(FilterSet.class);
        predictionService = new PredictionService(predictionsClient, repository, oddsService, filterSet);
    }

    @Test
    public void saveUpdate_WhenInputCorrectData_ShouldCallFunctionTwiceTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<PredictionDto> predictionDtoList = new ArrayList<>();
        predictionDtoList.add(PredictionDto.builder().date(dateFormat.parse("2021-04-27")).away_team("away1").home_team("name1").build());
        predictionDtoList.add(PredictionDto.builder().date(dateFormat.parse("2021-04-27")).away_team("away2").home_team("name2").build());

        List<Date> dateList = new ArrayList<>();
        dateList.add(dateFormat.parse("2021-04-27"));

        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder().homeTeam("name1").awayTeam("away1").build());
        predictionList.add(Prediction.builder().homeTeam("name2").awayTeam("away2").build());


        when(predictionsClient.getPrediction(anyString())).thenReturn(PredictionDataDto.builder().data(predictionDtoList).build());
        predictionService.saveUpdate(predictionList, dateList);

        verify(repository, times(2)).save(any());
    }

    @Test
    public void getPrediction_InputListWithOneItem_ShouldReturnListWithOneItemTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Prediction> predictionList = new ArrayList<>();
        predictionList.add(Prediction.builder()
                .awayTeam("nameAway1")
                .homeTeam("nameHome1")
                .competitionName("competition1")
                .prediction("1X")
                .competitionCluster("cluster1")
                .status("pending")
                .date(dateFormat.parse("2021-04-20"))
                .odds(Odds.builder().build())
                .id(1L)
                .build());
        List<PredictionRest> expected = new ArrayList<>();
        expected.add(PredictionRest.builder()
                .awayTeam("nameAway1")
                .homeTeam("nameHome1")
                .competitionName("competition1")
                .prediction("1X")
                .competitionCluster("cluster1")
                .status("pending")
                .date(dateFormat.parse("2021-04-20"))
                .odds(2.2)
                .build());

        when(oddsService.getOdds(anyLong(), anyString())).thenReturn(2.2);
        when(filterSet.filteringForMinimumEfficiency()).thenReturn(predictionList);
        List<PredictionRest> actual = predictionService.getPrediction();

        assertEquals(expected, actual);
    }

    @Test
    public void dataToBaseRequest_WhenInputInts_ShouldCallFunctionFiveTimesTest() throws ParseException {
        List<PredictionDto> predictionDtoList = new ArrayList<>();
        predictionDtoList.add(PredictionDto.builder()
                .odds(OddsDto.builder()
                        .won1(1.2)
                        .build())
                .away_team("away1")
                .home_team("name1")
                .build());

        when(predictionsClient.getPrediction(any())).thenReturn(PredictionDataDto.builder().data(predictionDtoList).build());
        predictionService.downloadDataToDatabase(21, 25, 4);

        verify(predictionsClient, times(5)).getPrediction(any());
    }

    @Test
    public void save_WhenInputCorrectData_ShouldCallFunctionOneTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<PredictionDto> predictionDtoList = new ArrayList<>();
        predictionDtoList.add(PredictionDto.builder()
                .odds(OddsDto.builder()
                        .won1(1.2)
                        .build())
                .away_team("away1")
                .home_team("name1")
                .build());
        PredictionDataDto predictionDataDto = PredictionDataDto.builder().data(predictionDtoList).build();
        Date date = dateFormat.parse("2021-04-20");

        predictionService.save(predictionDataDto, date);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void isUpdate_WhenCallFunction_ShouldReturnFalseTest() throws ParseException {
        when(repository.existsByDate(any())).thenReturn(false);
        boolean actual = predictionService.isUpdate();

        assertFalse(actual);
    }

    @Test
    public void isUpdate_WhenCallFunction_ShouldReturnTrueTest() throws ParseException {
        when(repository.existsByDate(any())).thenReturn(true);
        boolean actual = predictionService.isUpdate();

        assertTrue(actual);
    }
}

package org.PredictionRestApi.service;


import org.PredictionRestApi.entity.Odds;
import org.PredictionRestApi.repository.OddsRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OddsServiceTest {
    private OddsRepository repository;
    private OddsService oddsService;

    @Before
    public void setup() {
        repository = mock(OddsRepository.class);
        oddsService = new OddsService(repository);

    }

    @Test
    public void getOdds_WhenInputIdAneName_ShouldReturnOddsValueTest() {
        Odds build = Odds.builder()
                .id(1L)
                .won1(1.6)
                .won1Draw(1.2)
                .won1Won2(1.6)
                .won2(2.3)
                .won2Draw(1.8)
                .draw(2.5)
                .build();
        Double expected = 1.2;

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(build));
        Double actual = oddsService.getOdds(1L, "1X");

        assertEquals(expected, actual);
    }

    @Test
    public void getOdds_WhenInputIdAneWrongName_ShouldReturnNullTest() {
        Odds build = Odds.builder()
                .id(1L)
                .won1(1.6)
                .won1Draw(1.2)
                .won1Won2(1.6)
                .won2(2.3)
                .won2Draw(1.8)
                .draw(2.5)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(build));
        Double actual = oddsService.getOdds(1L, "other");

        assertEquals(null, actual);
    }

    @Test
    public void getOdds_WhenInputWrongIdAneName_ShouldReturnNullTest() {
        Odds build = Odds.builder()
                .id(1L)
                .won1(1.6)
                .won1Draw(1.2)
                .won1Won2(1.6)
                .won2(2.3)
                .won2Draw(1.8)
                .draw(2.5)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(build));
        Double actual = oddsService.getOdds(2L, "X2");

        assertEquals(null, actual);
    }
}

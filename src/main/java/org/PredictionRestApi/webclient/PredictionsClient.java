package org.PredictionRestApi.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.PredictionRestApi.webclient.dto.PredictionDataDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PredictionsClient {

    private final RestTemplate restTemplate;
    @Autowired
    private Environment environment;

    public PredictionDataDto getPrediction(String date) {
        ResponseEntity<PredictionDataDto> response;
        response = restTemplate
                .exchange(
                        "https://football-prediction-api.p.rapidapi.com/api/v2/predictions?iso_date={date}&market=classic&federation=UEFA",
                        HttpMethod.GET,
                        new HttpEntity<>(getKey()),
                        new ParameterizedTypeReference<>() {
                        },
                        date
                );
        return response.getBody();
    }

    public HttpHeaders getKey() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", environment.getProperty("RAPID_API_KEY"));
        return headers;
    }


}


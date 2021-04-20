package org.PredictionRestApi.service;

import lombok.RequiredArgsConstructor;
import org.PredictionRestApi.entity.Odds;
import org.PredictionRestApi.repository.OddsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OddsService {

    public final OddsRepository repository;

    public Double getOdds(Long id, String name) {
        Optional<Odds> odds = repository.findById(id);
        return odds.map(value -> switch (name) {
            case "1" -> value.getWon1();
            case "1X" -> value.getWon1Draw();
            case "12" -> value.getWon1Won2();
            case "2" -> value.getWon2();
            case ("X2") -> value.getWon2Draw();
            case "X" -> value.getDraw();
            default -> null;
        }).orElse(null);
    }
}

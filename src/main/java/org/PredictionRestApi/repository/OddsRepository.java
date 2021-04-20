package org.PredictionRestApi.repository;

import org.PredictionRestApi.entity.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OddsRepository extends JpaRepository<Odds, Long> {

    List<Odds> findAll();

}

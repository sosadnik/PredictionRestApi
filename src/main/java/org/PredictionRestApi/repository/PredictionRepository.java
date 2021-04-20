package org.PredictionRestApi.repository;

import org.PredictionRestApi.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {


    @Override
    boolean existsById(Long aLong);

    boolean existsByHomeTeamAndDate(String homeTeam, Date date);

    boolean existsByDate(Date date);

    List<Prediction> findAll();

    List<Prediction> findByStatus(String status);

}


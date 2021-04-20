package org.PredictionRestApi.repository;

import org.PredictionRestApi.entity.AwayTeamEfficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwayTeamEfficiencyRepository extends JpaRepository<AwayTeamEfficiency, Long> {

    boolean existsByTeamName(String name);

    AwayTeamEfficiency findByTeamName(String name);
}

package org.PredictionRestApi.repository;

import org.PredictionRestApi.entity.CompetitionEfficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionEfficiencyRepository extends JpaRepository<CompetitionEfficiency, Long> {

    boolean existsByCompetitionName(String competitionName);

    CompetitionEfficiency findByCompetitionName(String name);
}

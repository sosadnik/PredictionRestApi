package org.PredictionRestApi.repository;

import org.PredictionRestApi.entity.HomeTeamEfficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeTeamEfficiencyRepository extends JpaRepository<HomeTeamEfficiency, Long> {

    boolean existsByTeamName(String name);

    HomeTeamEfficiency findByTeamName(String name);
}

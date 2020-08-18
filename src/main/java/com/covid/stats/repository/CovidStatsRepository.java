package com.covid.stats.repository;

import com.covid.stats.domain.CovidStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CovidStatsRepository extends JpaRepository<CovidStats, Integer> {
    Optional<CovidStats> findByStateAndCountry(String state, String country);

    List<CovidStats> findByCountry(String country);
}

package com.covid.stats.service;

import com.covid.stats.domain.CovidStats;

import java.util.List;

public interface CovidService {

    List<CovidStats> getCovidStatsDataFromDb(String countryName);
}

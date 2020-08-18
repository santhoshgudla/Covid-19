package com.covid.stats.service;

import com.covid.stats.domain.CovidStats;

import java.util.List;

public interface CovidSourceService {

    List<CovidStats> getCovidStatsDataFromGithub(String countryName);
}

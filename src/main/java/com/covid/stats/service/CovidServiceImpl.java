package com.covid.stats.service;

import com.covid.stats.constant.Country;
import com.covid.stats.domain.CovidStats;
import com.covid.stats.exception.NotFoundException;
import com.covid.stats.repository.CovidStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CovidServiceImpl implements CovidService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CovidServiceImpl.class);

    @Autowired
    private CovidStatsRepository covidStatsRepository;

    @Override
    public List<CovidStats> getCovidStatsDataFromDb(String countryName) {
        try {
            Country country = Country.valueOf(countryName.toUpperCase());
            if(country != null) {
                return covidStatsRepository.findByCountry(country.getCountyName());
            } else {
                LOGGER.error("Given Country not found"+countryName);
                throw new NotFoundException("Data not found!");
            }
        } catch (Exception e){
            LOGGER.error("Given Country not found"+countryName);
            throw new NotFoundException("Data not found!");
        }
    }
}

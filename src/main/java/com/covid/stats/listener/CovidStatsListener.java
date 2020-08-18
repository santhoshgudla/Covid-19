package com.covid.stats.listener;

import com.covid.stats.domain.CovidStats;
import com.covid.stats.repository.CovidStatsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Component
public class CovidStatsListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(CovidStatsListener.class);

    @Autowired
    private CovidStatsRepository covidStatsRepository;

    @JmsListener(destination = "${covid.stats.queue}")
    public void covidStatsListener(String covidStatsString){
        List<CovidStats> covidStats = fromJSON(new TypeReference<List<CovidStats>>() {
        }, covidStatsString);
        for (CovidStats covid: covidStats) {
            Optional<CovidStats> covidStatsOptional = null;
            if(!StringUtils.isEmpty(covid.getState())){
                covidStatsOptional = covidStatsRepository.findByStateAndCountry(
                        covid.getState(), covid.getCountry());
                if (covidStatsOptional.isPresent()) {
                    covid.setId(covidStatsOptional.get().getId());
                }
                CovidStats save = covidStatsRepository.save(covid);
            } else {
                List<CovidStats> byCountry = covidStatsRepository.findByCountry(covid.getCountry());
                if(byCountry.size() == 1){
                    covid.setId(byCountry.get(0).getId());
                    CovidStats save = covidStatsRepository.save(covid);
                } else if(byCountry.size() == 0) {
                    CovidStats save = covidStatsRepository.save(covid);
                } else {
                    /**
                     * This scenario should not happen as per the given data
                     */
                    LOGGER.info("Got country level data with multiples so ignoring it");
                }
            }


        }
    }

    public <T> T fromJSON(final TypeReference<T> type,
                                 final String jsonPacket) {
        T data = null;
        try {
            data = new ObjectMapper().readValue(jsonPacket, type);
        } catch (Exception e) {
            LOGGER.error("Unable to convert json to object ", e);
        }
        return data;
    }
}

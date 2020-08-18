package com.covid.stats.service;

import com.covid.stats.domain.CovidStats;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.List;

@Service
public class CovidScheduleService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CovidScheduleService.class);

    @Autowired
    private Queue queue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private CovidSourceService covidSourceService;

    @Value("${country.covid-stats}")
    private String countryName;

    @Scheduled(fixedDelayString = "${stats.fetch.delay-time:60000}")
    public void sendDataToJms(){
        List<CovidStats> covidStats = covidSourceService.getCovidStatsDataFromGithub(countryName);
        if(!covidStats.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String jsonString = objectMapper.writeValueAsString(covidStats);
                jmsTemplate.convertAndSend(queue, jsonString);
            } catch (JsonProcessingException e) {
                LOGGER.error("Unable to convert list to json string ", e);
            }
        }
    }
}

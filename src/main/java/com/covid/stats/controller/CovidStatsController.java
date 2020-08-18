package com.covid.stats.controller;

import com.covid.stats.domain.CovidStats;
import com.covid.stats.exception.NotFoundException;
import com.covid.stats.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CovidStatsController {

    @Autowired
    private CovidService covidService;

    @GetMapping("/")
    public String covidStatsHome(Model model){
        List<CovidStats> covidStats = covidService.getCovidStatsDataFromDb("CA");
        model.addAttribute("covidStats", covidStats);
        return "home";
    }

    @ExceptionHandler({ NotFoundException.class})
    public String handleException() {
       return "error";
    }
}

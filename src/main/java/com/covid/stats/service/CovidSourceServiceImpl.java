package com.covid.stats.service;

import com.covid.stats.constant.Country;
import com.covid.stats.domain.CovidStats;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CovidSourceServiceImpl implements CovidSourceService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CovidSourceServiceImpl.class);

    @Override
    public List<CovidStats> getCovidStatsDataFromGithub(String countryName) {
        String sourceUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
        try {
            URL url  = new URL(sourceUrl);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            InputStream inputStream = http.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<CovidStats> covidStatsList = bufferedReader.lines().skip(1).filter(line -> {
                String[] data = line.split(",");
                if (data.length > 2 && !StringUtils.isEmpty(data[1])
                        && (data[1].equalsIgnoreCase(Country.CA.getCountyName())
                        || data[1].equalsIgnoreCase(Country.IN.getCountyName()))) {
                    return true;
                }
                return false;
            }).map(mapToItem)
                    .collect(Collectors.toList());
            bufferedReader.close();
            inputStream.close();
            return covidStatsList;
        } catch (MalformedURLException e) {
            LOGGER.error("Got MalformedURLException when fetch from github", e);
        } catch (IOException e) {
            LOGGER.error("Got IOException when fetch from github", e);
        }
        return new ArrayList<>();
    }

    private Function<String, CovidStats> mapToItem = (line) -> {
        String[] p = line.split(",");
        if(p.length > 6) {
            CovidStats covidStats = new CovidStats();
            covidStats.setState(p[0]);
            covidStats.setCountry(p[1]);
            if (NumberUtils.isCreatable(p[p.length - 1])) {
                covidStats.setTotalCase(NumberUtils.createInteger(p[p.length - 1]));
                if (NumberUtils.isCreatable(p[p.length - 2]))
                    covidStats.setLastDayCases((NumberUtils.createInteger(p[p.length - 1])
                            - NumberUtils.createInteger(p[p.length - 2])));
            }
            return covidStats;
        }
        return null;
    };
}

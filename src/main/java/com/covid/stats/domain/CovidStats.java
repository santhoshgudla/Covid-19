package com.covid.stats.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "covid")
public class CovidStats {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String state;

    private String country;

    private int totalCase;

    private int lastDayCases;

    @Override
    public String toString() {
        return "CovidStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", totalCase=" + totalCase +
                ", lastDayCases=" + lastDayCases +
                '}';
    }
}

package com.covid.stats.constant;

public enum Country {

    CA("Canada"),
    IN("India");

    private String countyName;

    public String getCountyName() {
        return countyName;
    }

    Country(String countryName) {
        this.countyName = countryName;
    }
}

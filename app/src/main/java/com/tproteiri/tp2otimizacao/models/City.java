package com.tproteiri.tp2otimizacao.models;

import java.util.List;

/**
 * Created by josue on 11/30/16.
 */

public class City {

    private String locality;
    private Double latitude;
    private Double longitude;
    private List<City> links;

    public City(String locality, Double latitude, Double longitude) {
        this.locality = locality;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<City> getLinks() {
        return links;
    }

    public void setLinks(List<City> links) {
        this.links = links;
    }
}

package com.tproteiri.tp2otimizacao.models;

import java.util.HashMap;

/**
 * Created by josue on 11/30/16.
 */

public class City {

    private String locality;
    private Double latitude;
    private Double longitude;
    private HashMap<City, Double> links;

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

    public HashMap<City, Double> getLinks() {
        return links;
    }

    public void setLinks(HashMap<City, Double> links) {
        this.links = links;
    }

    public void addLink(City city) {
        if (this.links == null) links = new HashMap<>();
        links.put(city, greatCircleTo(city));
    }

    public boolean hasLink(City city) {
        if (links == null) return false;
        for (City c : links.keySet()) {
            if (c.locality.equals(city.locality)) return true;
        }
        return false;
    }

    private double greatCircleTo(City city) {
        double x1 = Math.toRadians(latitude);
        double y1 = Math.toRadians(longitude);
        double x2 = Math.toRadians(city.getLatitude());
        double y2 = Math.toRadians(city.getLongitude());

        /*************************************************************************
         * Compute using law of cosines
         *************************************************************************/
        // great circle distance in radians
        double angle1 = Math.acos(Math.sin(x1) * Math.sin(x2)
                + Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));

        // convert back to degrees
        angle1 = Math.toDegrees(angle1);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance1 = 60 * angle1;

        System.out.println(distance1 + " nautical miles");


        /*************************************************************************
         * Compute using Haverside formula
         *************************************************************************/
        double a = Math.pow(Math.sin((x2-x1)/2), 2)
                + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin((y2-y1)/2), 2);

        // great circle distance in radians
        double angle2 = 2 * Math.asin(Math.min(1, Math.sqrt(a)));

        // convert back to degrees
        angle2 = Math.toDegrees(angle2);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance2 = 60 * angle2;

        return distance2;
    }
}

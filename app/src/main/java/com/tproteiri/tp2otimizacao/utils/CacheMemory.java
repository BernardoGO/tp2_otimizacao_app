package com.tproteiri.tp2otimizacao.utils;

import com.tproteiri.tp2otimizacao.BuildConfig;
import com.tproteiri.tp2otimizacao.models.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josue on 11/30/16.
 */
public class CacheMemory {

    private static List<City> cities;
    private static City param;

    public static void addCity(City city) {
        if (cities == null) cities = new ArrayList<>();
        cities.add(city);
    }

    public static List<City> getCities() {
        if (cities == null) return new ArrayList<>();
        return cities;
    }

    public static void addLink(City origin, City dest) {
        if (cities != null) {
            for (City c  : cities) {
                if (c.getLocality().equals(origin.getLocality())) {
                    c.addLink(dest);
                }
            }
        }
    }

    public static void removeCity(int pos) {
        if (cities != null) cities.remove(pos);
    }

    public static void clear(){
        cities = new ArrayList<>();
        param = null;
    }

    public static void setParam(City city) {
        CacheMemory.param = city;
    }

    public static City getParam() {
        return param;
    }

}

package com.tproteiri.tp2otimizacao.utils;

import com.tproteiri.tp2otimizacao.models.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josue on 11/30/16.
 */
public class CacheMemory {

    private static List<City> cities;

    public static void addCity(City city) {
        if (cities == null) cities = new ArrayList<>();
        cities.add(city);
    }

    public static List<City> getCities() {
        if (cities == null) return new ArrayList<>();
        return cities;
    }

    public static void removeCity(int pos) {
        if (cities != null) cities.remove(pos);
    }

    public static void clear(){
        cities = new ArrayList<>();
    }

}

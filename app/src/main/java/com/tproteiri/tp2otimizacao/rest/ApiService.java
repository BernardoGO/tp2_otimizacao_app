package com.tproteiri.tp2otimizacao.rest;

import com.google.gson.JsonObject;

import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by josue on 01/12/16.
 */

public interface ApiService {
    String END = "http://localhost:4567";

    @GET("/hello")
    Observable<JsonObject> hello();

    @POST("/solve")
    Observable<JsonObject> solve();
}

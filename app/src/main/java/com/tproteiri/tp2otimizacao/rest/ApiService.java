package com.tproteiri.tp2otimizacao.rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.tproteiri.tp2otimizacao.models.Problem;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by josue on 01/12/16.
 */

public interface ApiService {
    String END = "http://ec2-35-161-138-28.us-west-2.compute.amazonaws.com:4567";

    @GET("/hello")
    Call<JsonPrimitive> hello();

    @POST("/solve")
    Call<JsonObject> solve(@Body Problem problem);
}

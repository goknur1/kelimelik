package com.goknur.kelimelik.rest.service;

import com.goknur.kelimelik.rest.model.Kelime;
import com.goknur.kelimelik.rest.model.RWord;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by goknur on 20.07.2017.
 */

public interface KelimeService {

    @Headers("Content-Type: application/json")
    @GET("kelimeler.json")
    Call<List<Kelime>> getKelimelist();


    @Headers("Content-Type: application/json")
    @GET("kelimeler.json")
    Call<HashMap<String, Kelime>> getKelimeList();

    @Headers("Content-Type: application/json")
    @GET("kelimeler.json")
    Call<HashMap<String, Kelime>> getKelimeList2();

    @Headers("Content-Type: application/json")
    @POST("kelimeler.json")
    Call<RWord> saveWord(@Body Kelime kelime);
}

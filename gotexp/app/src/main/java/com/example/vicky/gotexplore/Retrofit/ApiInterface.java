package com.example.vicky.gotexplore.Retrofit;

import com.example.vicky.gotexplore.model.Model;
import com.example.vicky.gotexplore.model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("characters/{character_name}")
    Call<ModelResponse> getCharacterDetails(@Path("character_name") String character_name);
}

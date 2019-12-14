package com.project.androidlivetrack.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    private static final String URL = "https://smarttech-live-track.herokuapp.com/api/v1/";
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <s> s buildService(Class<s> serviceType){
        return retrofit.create(serviceType);
    }
}

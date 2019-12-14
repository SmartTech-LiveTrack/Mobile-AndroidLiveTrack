package com.project.androidlivetrack.retrofit;


import com.project.androidlivetrack.retrofit.models.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AppServices {

    /*@GET("volumes")
    Call<String> getBooks(@Query("q") String searchParam);*/

    @POST("auth/login")
    Call<UserData> login(@Body UserData data);

    @POST("locations")
    Call<UserData> postLocation(@Body UserData data);

    @GET("alert")
    Call<UserData> alertContact(@Header("Authorization") String token,@Body UserData data);

}

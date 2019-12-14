package com.project.androidlivetrack.retrofit.models;

import android.content.Context;
import android.os.Handler;

import com.project.androidlivetrack.SimpleLocation;
import com.google.gson.annotations.SerializedName;
import com.project.androidlivetrack.retrofit.AppServices;
import com.project.androidlivetrack.retrofit.ServiceBuilder;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserData {
    @SerializedName("latitude")
    private String mLatitude;
    @SerializedName("longitude")
    private String mLongitude;
    @SerializedName("email")
    private String mUsername;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("imageLink")
    private String mImageUrl;
    @SerializedName("timestamp")
    private String mDate;


    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    @SerializedName("token")
    private String mToken;

    private SimpleLocation mLocation;
    private Context mContext;

    public UserData(Context context, String username, String password){
        setUsername(username);
        setPassword(password);
        mContext = context;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUSername) {
        this.mUsername = mUSername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public Boolean verifyUser(){
        return  null;
    }

    private String getDateTime(long time){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = new Date(time);
        return formatter.format(date);
    }

    public void queueData(){
        String queue = getLongitude() + ":" + getLatitude() + ":" + getDate() + "\n";
            File file = new File(mContext.getExternalFilesDir(""), "Data");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File gpxfile = new File(file, "data");
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(queue);
                writer.flush();
                writer.close();
            } catch (Exception e) { }
    }


    public void postData(){
        mLocation = new SimpleLocation(mContext,true, false, 1000);
        if(!mLocation.hasLocationEnabled()){
            SimpleLocation.openSettings(mContext);
        }
        mLocation.beginUpdates();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setLatitude(String.valueOf(mLocation.getLatitude()));
                setLongitude(String.valueOf(mLocation.getLongitude()));
                setDate(getDateTime(mLocation.getTimestampInMilliseconds()));
            }
        }, 5000);
        AppServices ms = ServiceBuilder.buildService(AppServices.class);
        Call<UserData> call = ms.postLocation(this);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }
        });
        //try post and if failed --->
        if(false){
            queueData();
        }
    }
}

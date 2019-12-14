package com.project.androidlivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.androidlivetrack.retrofit.AppServices;
import com.project.androidlivetrack.retrofit.ServiceBuilder;
import com.project.androidlivetrack.retrofit.models.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.project.androidlivetrack.NotificationService.CHANNEL_ID;
import static com.project.androidlivetrack.SharedPreferenceUtil.initPref;
import static com.project.androidlivetrack.SharedPreferenceUtil.setPrefString;

public class MainActivity extends AppCompatActivity {
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = initPref(getApplicationContext());
        /*if (!mSettings.getBoolean("loggedIn", false)) {
            Intent intent = new Intent(this, LoggedInActivity.class);
            startActivity(intent);
        }*/
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE},101);


    }

    private void startService() {
        Intent intent = new Intent(this, NotificationService.class);
        ContextCompat.startForegroundService(this,intent);
    }



    public void signUp(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("action", "signup");
        startActivity(intent);


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "LiveTrack",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }

    public void verify(View view) {
        Boolean success = true;
        createNotificationChannel();
        startService();
        EditText userText = findViewById(R.id.emailText);
        EditText passText = findViewById(R.id.passwordText);
        String user = userText.getText().toString();
        String pass = passText.getText().toString();
        //Call
        AppServices ms = ServiceBuilder.buildService(AppServices.class);
        UserData data = new UserData(this,user,pass);
        Call<UserData> call  = ms.login(data);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                setPrefString(getApplicationContext(),"username",response.body().getUsername());
                setPrefString(getApplicationContext(),"password",response.body().getPassword());
                setPrefString(getApplicationContext(),"token", response.body().getToken());
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                t.getStackTrace();
            }
        });

        if(success){
            mSettings.edit().putBoolean("loggedin",true).apply();
            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            startActivity(intent);
        }
    }
}


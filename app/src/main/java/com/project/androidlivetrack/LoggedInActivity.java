package com.project.androidlivetrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import static com.project.androidlivetrack.NotificationService.CHANNEL_ID;
import static com.project.androidlivetrack.SharedPreferenceUtil.initPref;

public class LoggedInActivity extends AppCompatActivity {
    private Toolbar toolbar;
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mSettings = initPref(getApplicationContext());





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_menu) {
            Intent intent = new Intent(LoggedInActivity.this, WebViewActivity.class);
            intent.putExtra("login","login");
            startActivity(intent);
        }
        return true;
    }


}

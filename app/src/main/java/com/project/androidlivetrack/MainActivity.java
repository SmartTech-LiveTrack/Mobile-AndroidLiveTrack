package com.project.androidlivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void signUp(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("action", "signup");
        startActivity(intent);

        /*Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("action", "login");
        startActivity(intent);*/
    }

    public void verify(View view) {

        if(false){
            Intent intent = new Intent(MainActivity.this, Welcome.class);
            intent.putExtra("jwt","");
            startActivity(intent);
        }
    }
}


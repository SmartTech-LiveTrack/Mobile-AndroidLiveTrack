package com.project.androidlivetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EmergencyActivity extends AppCompatActivity {
    CountDownTimer cTimer = null;
    TextView timerText;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        //raise volume 5 times
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        audioManager = null;

        //change to musical value
        mp = MediaPlayer.create(this, R.raw.alarm);
        mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp=null;
            }
        });

        startTimer();


    }


    private void startTimer() {
        timerText = findViewById(R.id.timerText);
        cTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                long timeRem = millisUntilFinished/1000;
                String timeOutput = "0" + timeRem;
                timerText.setText(timeOutput);
            }
            public void onFinish() {
                cancelTimer();
                disableButtons();
                //report emergency
                finishActivity(1);
            }
        };
        cTimer.start();
    }

    private void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }


    public void yes(View view) {
        disableButtons();
        cancelTimer();
        if(mp != null){
            mp.reset();
            mp.release();
            mp=null;
        }
    }

    public void no(View view) {
        disableButtons();
        cancelTimer();
        if(mp != null){
            mp.reset();
            mp.release();
            mp=null;
        }
        finishActivity(0);

    }

    private  void disableButtons(){
        Button btn1 = findViewById(R.id.btnYes);
        Button btn2 = findViewById(R.id.btnNo);
        btn1.setEnabled(false);
        btn2.setEnabled(false);
    }
}

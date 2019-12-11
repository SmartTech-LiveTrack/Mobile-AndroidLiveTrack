package com.project.androidlivetrack;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.project.androidlivetrack.retrofit.models.UserData;

public class NotificationJobService extends JobService {
    private boolean jobCancelled = false;
    public static String mKey;
    private Location mlocation;

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this,"Job started", Toast.LENGTH_SHORT).show();
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
                UserData data = new UserData(getApplicationContext(),"user", "pass");
                data.setLongitude("testlong");
                data.setLatitude("testlat");
                data.setDate("1234567");
                data.queueData();

                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }

}

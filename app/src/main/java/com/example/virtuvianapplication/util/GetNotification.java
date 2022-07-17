package com.example.virtuvianapplication.util;

import android.app.job.JobParameters;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetNotification extends JobServices {
    @Override
    public boolean onStartJob(JobParameters parameters) {
        Log.d("LOG", "onStartJob");
        getNotification(parameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("LOG", "onStopJob");
        return true;
    }

    public void getNotification(JobParameters parameters) {
        //Date date = Calendar.getInstance().getTime();
        //DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
        //String todayDate = formatter.format(date);

        Calendar calendar = Calendar.getInstance();

        int currentMinute = calendar.get(Calendar.MINUTE);

        //for (int i = 0; i < Const.ALARM_MINUTE_TIME.length; i++) {
         //   if (Const.ALARM_MINUTE_TIME[i]) {
         //       Notif notif = new Notif();
         //       notif.sendNotif("syam", "notification", getApplicationInfo().name, getApplicationContext());
         //   }
          //  jobFinished(parameters, false);
        //}

    }
}

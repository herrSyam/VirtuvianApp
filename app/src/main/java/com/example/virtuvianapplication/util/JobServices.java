package com.example.virtuvianapplication.util;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.response.NotificationResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobServices extends JobService {

    private MimicAsyncTask mimicAsyncTask;
    private JobParameters param;
    private PreferenceManager preferenceManager;

    @Override
    public boolean onStartJob(JobParameters parameters) {
        this.param = parameters;
        preferenceManager = new PreferenceManager(getApplicationContext());

        Calendar calendar = Calendar.getInstance();
        mimicAsyncTask = new MimicAsyncTask();
        mimicAsyncTask.execute(calendar);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("Message", "onStopJob: Job Canceled");
        if (null != mimicAsyncTask) {
            if (!mimicAsyncTask.isCancelled()) {
                mimicAsyncTask.cancel(true);
            }
        }
        return false;
    }

    private class MimicAsyncTask extends AsyncTask<Calendar, Calendar, String> {

        @Override
        protected String doInBackground(Calendar... integers) {
            Calendar calendar = Calendar.getInstance();
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String todayDate = formatter.format(date);
            Log.d("Test", "onPostExecute: Message " + todayDate);

            Call<NotificationResponse> call = ApiConfig.getApiRequest().getNotification(preferenceManager.getString(Constants.KEY_OBAT));
            call.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    NotificationResponse notificationResponse = response.body();

                    for (int i = 0; i < notificationResponse.getData().length; i++) {

                        if (notificationResponse.getData()[i].getEvent_time().equals(todayDate)) {
                            SystemClock.sleep(1000);
                            Notif notif = new Notif();
                            notif.sendNotif(notificationResponse.getData()[i].getName(), notificationResponse.getData()[i].getMessage(), getApplicationInfo().name, getApplicationContext(), i);

                        }
                        publishProgress(calendar);
                    }

                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {

                }
            });

            return "calendar";
        }

        @Override
        protected void onProgressUpdate(Calendar... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            jobFinished(param, true);
        }
    }
}

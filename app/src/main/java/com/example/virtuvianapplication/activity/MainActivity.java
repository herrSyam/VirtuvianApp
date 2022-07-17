package com.example.virtuvianapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.fragment.Diet;
import com.example.virtuvianapplication.fragment.Aktivitas;
import com.example.virtuvianapplication.fragment.Obat;
import com.example.virtuvianapplication.fragment.Penkes;
import com.example.virtuvianapplication.model.EventAktivitasModel;
import com.example.virtuvianapplication.model.PersonObatModel;
import com.example.virtuvianapplication.response.AccessToken;
import com.example.virtuvianapplication.response.PersonObatResponse;
import com.example.virtuvianapplication.util.Const;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.JobServices;
import com.example.virtuvianapplication.util.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    private PreferenceManager preferenceManager;
    private static final int DOWNLOAD_JOB_KEY = 101;
    private static final long REFRESH_INTERVAL  = 5 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());
        initJobScheduler();

        bottomNavigationView = findViewById(R.id.navigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Penkes()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.penkes:
                        selectedFragment = new Penkes();
                        break;
                    case R.id.diet:
                        selectedFragment = new Diet();
                        break;
                    case R.id.aktivitas:
                        selectedFragment = new Aktivitas();
                        break;
                    case R.id.obat:
                        selectedFragment = new Obat();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        });
    }

    private void initJobScheduler() {

        Log.d("mainActivity", "initJobScheduler : Start");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ComponentName componentName = new ComponentName(this, JobServices.class);
            JobInfo.Builder builder = new JobInfo.Builder(DOWNLOAD_JOB_KEY, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setMinimumLatency(REFRESH_INTERVAL);
            } else {
                builder.setPeriodic(REFRESH_INTERVAL);
            }

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.schedule(builder.build());
        }
    }
}
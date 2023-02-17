package com.example.virtuvianapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.fragment.Diet;
import com.example.virtuvianapplication.fragment.Aktivitas;
import com.example.virtuvianapplication.fragment.Gds;
import com.example.virtuvianapplication.fragment.Obat;
import com.example.virtuvianapplication.fragment.Penkes;
import com.example.virtuvianapplication.fragment.penkesVideo;
import com.example.virtuvianapplication.response.PostResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    // Test
    BottomNavigationView bottomNavigationView;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());
        getToken();

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
                    case R.id.penkesVideo:
                        selectedFragment = new penkesVideo();
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
                    /*case R.id.gds:
                        selectedFragment = new Gds();
                        break;*/
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        });
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
                        Call<PostResponse> call = ApiConfig.getApiRequest().updateTokenDevice(preferenceManager.getString(Constants.KEY_USER_ID), token);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                            }

                            @Override
                            public void onFailure(Call<PostResponse> call, Throwable t) {

                            }
                        });
                    }
                });
    }

}
package com.example.virtuvianapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.adapter.GdsAdapter;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.ActivityAddEditGdsBinding;
import com.example.virtuvianapplication.fragment.Gds;
import com.example.virtuvianapplication.fragment.Penkes;
import com.example.virtuvianapplication.response.PostResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditGds extends AppCompatActivity {

    private ActivityAddEditGdsBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditGdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        setListeners();
    }

    private void setListeners()
    {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.btnSave.setOnClickListener(v -> saveGds());
    }

    private void saveGds()
    {
        final Double value_gds = Double.parseDouble(binding.inputGds.getText().toString());
        final String user_id = preferenceManager.getString(Constants.KEY_USER_ID);

        Call<PostResponse> call = ApiConfig.getApiRequest().addGds("add",user_id, value_gds);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                PostResponse postResponse = response.body();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }
}
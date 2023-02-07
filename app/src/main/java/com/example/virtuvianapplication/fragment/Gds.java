package com.example.virtuvianapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virtuvianapplication.activity.AddEditGds;
import com.example.virtuvianapplication.activity.MainActivity;
import com.example.virtuvianapplication.adapter.GdsAdapter;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.FragmentGdsBinding;
import com.example.virtuvianapplication.model.GdsModel;
import com.example.virtuvianapplication.response.GdsResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gds extends Fragment {

    private FragmentGdsBinding binding;
    private PreferenceManager preferenceManager;
    private ArrayList<GdsModel> gdsModels;
    private GdsAdapter adapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        preferenceManager = new PreferenceManager(getContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGdsBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setListeners();
        setAdapter();
    }

    private void setListeners()
    {
        binding.createBtn.setOnClickListener(v -> createGds());
    }

    private void createGds() {
        Intent intent = new Intent(getActivity(), AddEditGds.class);
        ((MainActivity) getActivity()).startActivity(intent);
    }

    private void setAdapter() {
        Call<GdsResponse> call = ApiConfig.getApiRequest().getGdsByUserId(preferenceManager.getString(Constants.KEY_USER_ID));
        call.enqueue(new Callback<GdsResponse>() {
            @Override
            public void onResponse(Call<GdsResponse> call, Response<GdsResponse> response) {
                GdsResponse gdsResponse = response.body();
                gdsModels = new ArrayList<>(Arrays.asList(gdsResponse.getData()));

                adapter = new GdsAdapter(gdsModels);
                binding.eventListViewGds.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GdsResponse> call, Throwable t) {
                Log.e("test", "bbbb " + t.getMessage());
            }
        });
    }
}
package com.example.virtuvianapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.virtuvianapplication.adapter.AktivitasAdapter;
import com.example.virtuvianapplication.app.AktivitasListener;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.ActivityAktivitasEditBinding;
import com.example.virtuvianapplication.model.AktivitasAnswerDataModel;
import com.example.virtuvianapplication.model.AktivitasAnswerModel;
import com.example.virtuvianapplication.model.AktivitasModel;
import com.example.virtuvianapplication.model.CheckBoxAktivitasModel;
import com.example.virtuvianapplication.response.AktivitasResponse;
import com.example.virtuvianapplication.response.PostResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AktivitasEditActivity extends AppCompatActivity implements AktivitasListener {
    private ActivityAktivitasEditBinding binding;
    private PreferenceManager preferenceManager;
    private AktivitasAdapter adapter;
    private ArrayList<AktivitasModel> aktivitasModelList;
    ArrayList<CheckBoxAktivitasModel> arrayList_i = new ArrayList<>();
    private LocalTime time;

    AktivitasAnswerDataModel aktivitasAnswerDataModel;
    AktivitasAnswerModel aktivitasAnswerModel;
    ArrayList<AktivitasAnswerDataModel> arrayListData = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAktivitasEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        loadDetails();
    }

    private void setListeners()
    {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.save.setOnClickListener(v -> saveAction(arrayList_i));
    }

    private void loadDetails()
    {
        Call<AktivitasResponse> call = ApiConfig.getApiRequest().aktivitas();
        call.enqueue(new Callback<AktivitasResponse>() {
            @Override
            public void onResponse(Call<AktivitasResponse> call, Response<AktivitasResponse> response) {
                AktivitasResponse aktivitasResponse = response.body();

                aktivitasModelList = new ArrayList<>(Arrays.asList(aktivitasResponse.getData()));

                populateListView(aktivitasModelList);
            }

            @Override
            public void onFailure(Call<AktivitasResponse> call, Throwable t) {

            }
        });
    }

    private void populateListView(ArrayList<AktivitasModel> aktivitasModelList)
    {
        adapter = new AktivitasAdapter(aktivitasModelList, this);
        binding.aktivitasEditListView.setAdapter(adapter);
        binding.aktivitasEditListView.setVisibility(View.VISIBLE);
    }

    private void saveAction(ArrayList<CheckBoxAktivitasModel> arrayList_i) {
        loading(true);

        for (int i = 0; i < arrayList_i.size(); i++)
        {
            aktivitasAnswerDataModel = new AktivitasAnswerDataModel(
                    preferenceManager.getString(Constants.KEY_USER_ID),
                    arrayList_i.get(i).getAktivitas_id(),
                    arrayList_i.get(i).getPar()
            );
            arrayListData.add(aktivitasAnswerDataModel);
        }

        aktivitasAnswerModel = new AktivitasAnswerModel("add", arrayListData);
        Call<PostResponse> call = ApiConfig.getApiRequest().addPersonAktivitas(aktivitasAnswerModel);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful())
                {
                    loading(false);
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAktivitasChange(ArrayList<CheckBoxAktivitasModel> arrayList) {
        arrayList_i = arrayList;
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
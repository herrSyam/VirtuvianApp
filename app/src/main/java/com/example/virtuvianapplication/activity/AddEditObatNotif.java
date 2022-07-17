package com.example.virtuvianapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.virtuvianapplication.adapter.ObatListAdapter;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.app.ObatListener;
import com.example.virtuvianapplication.databinding.ActivityAddEditObatNotifBinding;
import com.example.virtuvianapplication.model.CheckBoxObatModel;
import com.example.virtuvianapplication.model.ObatDataModel;
import com.example.virtuvianapplication.model.ObatModel;
import com.example.virtuvianapplication.model.ObatSaveModel;
import com.example.virtuvianapplication.response.AktivitasResponse;
import com.example.virtuvianapplication.response.ObatResponse;
import com.example.virtuvianapplication.response.PostResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditObatNotif extends AppCompatActivity implements ObatListener {
    private ActivityAddEditObatNotifBinding binding;
    private PreferenceManager preferenceManager;
    private ObatListAdapter adapter;
    private ArrayList<ObatModel> obatModels;
    ArrayList<CheckBoxObatModel> arrayList_i = new ArrayList<>();

    ObatDataModel obatDataModel;
    ObatSaveModel obatSaveModel;
    ArrayList<ObatDataModel> obatDataModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditObatNotifBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        loadDetails();
    }

    private void setListeners()
    {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.btnSave.setOnClickListener(v -> saveObat(arrayList_i));
    }

    private void loadDetails()
    {
        Call<ObatResponse> call = ApiConfig.getApiRequest().getObat();
        call.enqueue(new Callback<ObatResponse>() {
            @Override
            public void onResponse(Call<ObatResponse> call, Response<ObatResponse> response) {
                ObatResponse obatResponse = response.body();

                obatModels = new ArrayList<>(Arrays.asList(obatResponse.getData()));

                populateListView(obatModels);

            }

            @Override
            public void onFailure(Call<ObatResponse> call, Throwable t) {

            }
        });
    }

    private void populateListView(ArrayList<ObatModel> obatModels)
    {
        adapter = new ObatListAdapter(obatModels, this);
        binding.obatEditListView.setAdapter(adapter);
        binding.obatEditListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onObatChange(ArrayList<CheckBoxObatModel> arrayList) {
        arrayList_i = arrayList;
    }

    private void saveObat(ArrayList<CheckBoxObatModel> arrayList_i)
    {
        loading(true);
        for (int i = 0; i<arrayList_i.size(); i++)
        {
            obatDataModel = new ObatDataModel(
                    preferenceManager.getString(Constants.KEY_USER_ID),
                    arrayList_i.get(i).getId()
            );
            obatDataModels.add(obatDataModel);
        }

        obatSaveModel = new ObatSaveModel("add", obatDataModels);
        Call<PostResponse> call = ApiConfig.getApiRequest().addPersonNotification(obatSaveModel);
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

    private void loading(Boolean isLoading){
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
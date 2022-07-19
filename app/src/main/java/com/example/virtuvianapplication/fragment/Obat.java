package com.example.virtuvianapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.virtuvianapplication.activity.AddEditObatNotif;
import com.example.virtuvianapplication.activity.MainActivity;
import com.example.virtuvianapplication.activity.SignInActivity;
import com.example.virtuvianapplication.adapter.ObatNotificationAdapter;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.FragmentObatBinding;
import com.example.virtuvianapplication.model.NotificationModel;
import com.example.virtuvianapplication.response.AccessToken;
import com.example.virtuvianapplication.response.NotificationResponse;
import com.example.virtuvianapplication.response.PersonObatResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Obat extends Fragment {

    private FragmentObatBinding binding;
    private PreferenceManager preferenceManager;
    private ArrayList<NotificationModel> modelArrayList;
    private ObatNotificationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentObatBinding.inflate(inflater, container, false);
        preferenceManager = new PreferenceManager(getContext().getApplicationContext());
        return binding.getRoot();
    }
    private void showToast(String message){
        Toast.makeText(binding.getRoot().getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initPersonObat();
        getToken(preferenceManager.getString(Constants.KEY_USER_ID));
        setListeners();
    }

    private void setListeners()
    {
        binding.btnSetAlarm.setOnClickListener(v -> setAlarm());
    }

    private void setAlarm()
    {
        Intent intent = new Intent(getActivity(), AddEditObatNotif.class);
        ((MainActivity) getActivity()).startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initPersonObat();
        setAdapter();
    }

    private void initPersonObat() {
        Call<PersonObatResponse> call = ApiConfig.getApiRequest().getPersonObat(preferenceManager.getString(Constants.KEY_USER_ID));
        call.enqueue(new Callback<PersonObatResponse>() {
            @Override
            public void onResponse(Call<PersonObatResponse> call, Response<PersonObatResponse> response) {
                PersonObatResponse personObatResponse = response.body();
                if (personObatResponse.getResponses() == 1) {
                    preferenceManager.putString(Constants.KEY_OBAT, personObatResponse.getData().getObatId());
                }
            }

            @Override
            public void onFailure(Call<PersonObatResponse> call, Throwable t) {
            }
        });
    }

    private void setAdapter()
    {
        Call<NotificationResponse> call = ApiConfig.getApiRequest().getNotification(preferenceManager.getString(Constants.KEY_OBAT));
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    NotificationResponse notificationResponse = response.body();
                    modelArrayList = new ArrayList<>(Arrays.asList(notificationResponse.getData()));
                    adapter = new ObatNotificationAdapter(modelArrayList);
                    binding.obatListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
            }
        });
    }

    private void getToken(String userid) {
        Call<AccessToken> call = ApiConfig.getApiRequest().accessToken(userid);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful())
                {
                    AccessToken accessToken = response.body();
                    if (accessToken.getResponses() == 0) {
                        preferenceManager.putString(Constants.KEY_IS_SIGNED_IN,"");
                        preferenceManager.putString(Constants.KEY_USER_ID, "");
                        preferenceManager.putString(Constants.KEY_NAME, "");
                        preferenceManager.putString(Constants.KEY_EMAIL, "");
                        Intent intent = new Intent(getActivity(), SignInActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        showToast("Session expired");
                    }
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
            }
        });
    }
}
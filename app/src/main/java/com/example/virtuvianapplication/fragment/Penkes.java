package com.example.virtuvianapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.virtuvianapplication.activity.MainActivity;
import com.example.virtuvianapplication.activity.SignInActivity;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.FragmentPenkesBinding;
import com.example.virtuvianapplication.response.AccessToken;
import com.example.virtuvianapplication.response.PenkesResponse;
import com.example.virtuvianapplication.response.PostResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;
import static com.example.virtuvianapplication.util.CalenderUtils.formattedDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.time.LocalDate;


public class Penkes extends Fragment {
    private FragmentPenkesBinding binding;
    private PreferenceManager preferenceManager;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPenkesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void showToast(String message){
        Toast.makeText(binding.getRoot().getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getToken(preferenceManager.getString(Constants.KEY_USER_ID));
        populatePenkes();
        setListeners();
    }

    public void populatePenkes() {
        Call<PenkesResponse> call = ApiConfig.getApiRequest().getPenkes();
        call.enqueue(new Callback<PenkesResponse>() {
            @Override
            public void onResponse(Call<PenkesResponse> call, Response<PenkesResponse> response) {
                PenkesResponse penkesResponse = response.body();
                binding.txtPenkes.setText(Html.fromHtml(penkesResponse.getDescription()));
                binding.textToday.setText(formattedDate(LocalDate.now()));
            }

            @Override
            public void onFailure(Call<PenkesResponse> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void setListeners()
    {
        binding.imageSignOut.setOnClickListener(v -> logOut());
    }

    private void logOut()
    {
        Log.e("login", "message" + preferenceManager.getString(Constants.KEY_IS_SIGNED_IN));
        Log.e("login", "email" + preferenceManager.getString(Constants.KEY_EMAIL));
        // preferenceManager.putString(Constants.KEY_IS_SIGNED_IN, "");
        Call<PostResponse> call = ApiConfig.getApiRequest().logOut("Bearer " + preferenceManager.getString(Constants.KEY_IS_SIGNED_IN));
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful())
                {
                    preferenceManager.putString(Constants.KEY_IS_SIGNED_IN,"");
                    preferenceManager.putString(Constants.KEY_USER_ID, "");
                    preferenceManager.putString(Constants.KEY_NAME, "");
                    preferenceManager.putString(Constants.KEY_EMAIL, "");

                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

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
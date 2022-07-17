package com.example.virtuvianapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.app.ApiRequest;
import com.example.virtuvianapplication.databinding.ActivitySignInBinding;
import com.example.virtuvianapplication.model.User;
import com.example.virtuvianapplication.response.AccessToken;
import com.example.virtuvianapplication.response.UserResponse;
import com.example.virtuvianapplication.util.Const;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;
    private ApiConfig apiConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());

        if (preferenceManager.getString(Constants.KEY_IS_SIGNED_IN) != "")
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){
        binding.textCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.buttonSignIn.setOnClickListener(v -> {
            final String email = binding.inputEmail.getText().toString();
            final String password = binding.inputPassword.getText().toString();
            if (isValidSignInDetails(email, password)){
                signIn(email, password);
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidSignInDetails(final String email, final String password){
        if (email.trim().isEmpty()){
            showToast("Enter Email");
            return false;
        } else if (password.trim().isEmpty()){
            showToast("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    private void signIn(final String email, final String password){
        Call<UserResponse> call = apiConfig.getApiRequest().getUser(email,password);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    if (userResponse.getSuccess() == 1){
                        Log.e("login", "message" + userResponse.getAccess_token());
                        preferenceManager.putString(Constants.KEY_IS_SIGNED_IN, userResponse.getAccess_token());
                        preferenceManager.putString(Constants.KEY_USER_ID, userResponse.getUser().getId());
                        preferenceManager.putString(Constants.KEY_NAME, userResponse.getUser().getName());
                        preferenceManager.putString(Constants.KEY_EMAIL, userResponse.getUser().getEmail());
                        // preferenceManager.putString(Constants.KEY_OBAT, userResponse.getUserObat().getObat_event_id());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        showToast("The username or password is incorrect");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
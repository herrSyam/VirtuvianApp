package com.example.virtuvianapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.ActivitySignUpBinding;
import com.example.virtuvianapplication.response.UserResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){
        binding.textSignIn.setOnClickListener(v -> onBackPressed());
        binding.gender.clearCheck();
        binding.gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
            }
        });



        binding.buttonSignUp.setOnClickListener(v -> {
            final String password = binding.inputPassword.getText().toString();
            final String username = binding.inputName.getText().toString();
            final Integer age = Integer.parseInt(binding.age.getText().toString());
            final Double weight = Double.parseDouble(binding.weight.getText().toString());
            final Double height = Double.parseDouble(binding.height.getText().toString());
            Integer gender = 0;
            int selectId = binding.gender.getCheckedRadioButtonId();
            if (selectId == -1) {
                Toast.makeText(SignUpActivity.this,
                        "No answer has been selected",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                RadioButton radioButton = (RadioButton)binding.gender.findViewById(selectId);
                if (selectId == binding.male.getId()) {
                    gender = 0;
                } else {
                    gender = 1;
                }
            }

            if (isValidSignUpDetails(password, username))
            {
                signUp(password, username, age, gender, weight, height);
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidSignUpDetails(final String password, final String username) {
        if (password.trim().isEmpty())
        {
            showToast("Enter password");
            return false;
        } else if (username.trim().isEmpty())
        {
            showToast("Enter userName");
            return false;
        } else
        {
            return true;
        }
    }

    private void signUp(final String password, final String username, final Integer age, Integer selectId, final Double weight, final Double height) {
        Call<UserResponse> call = ApiConfig.getApiRequest().register(password, username, age, selectId, weight, height);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse.getSuccess() == 1)
                {
                    preferenceManager.putString(Constants.KEY_IS_SIGNED_IN, userResponse.getAccess_token());
                    preferenceManager.putString(Constants.KEY_USER_ID, userResponse.getUser().getId());
                    preferenceManager.putString(Constants.KEY_NAME, userResponse.getUser().getName());
                    preferenceManager.putString(Constants.KEY_EMAIL, userResponse.getUser().getEmail());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    showToast("The username or password is incorrect");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }
}
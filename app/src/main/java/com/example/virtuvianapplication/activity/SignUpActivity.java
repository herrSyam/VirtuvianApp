package com.example.virtuvianapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.ActivitySignUpBinding;
import com.example.virtuvianapplication.response.LookupResponse;
import com.example.virtuvianapplication.response.UserResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;
    private List<LookupResponse> listLookup = new ArrayList<>();
    Spinner spinnerPendidikan;
    Spinner spinnerPekerjaan;

    String idEmploy;
    String idSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        lookupEmploy();
        lookupSchool();
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

        spinnerPekerjaan = binding.pekerjaan;
        spinnerPendidikan = binding.pendidikan;

        binding.buttonSignUp.setOnClickListener(v -> {
            final String password = binding.inputPassword.getText().toString();
            final String username = binding.inputName.getText().toString();
            final Integer age = Integer.parseInt(binding.age.getText().toString());



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
                signUp(password, username, age, gender, idSchool, idEmploy);
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void lookupEmploy() {
        Call<List<LookupResponse>> call = ApiConfig.getApiRequest().lookupEmployment("");
        call.enqueue(new Callback<List<LookupResponse>>() {
            @Override
            public void onResponse(Call<List<LookupResponse>> call, Response<List<LookupResponse>> response) {
                listLookup = response.body();
                if (listLookup != null & listLookup.size() > 0) {
                    String[] employ = new String[listLookup.size()];
                    String[] ide = new String[listLookup.size()];
                    for (int i = 0; i<listLookup.size(); i++) {
                        employ[i] = listLookup.get(i).getName();
                        ide[i] = listLookup.get(i).getId();

                        ArrayAdapter<String > adapter = new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_item, employ);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPekerjaan.setAdapter(adapter);
                        spinnerPekerjaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idEmploy = ide[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LookupResponse>> call, Throwable t) {

            }
        });
    }

    private void lookupSchool() {
        Call<List<LookupResponse>> call = ApiConfig.getApiRequest().lookupschool("");
        call.enqueue(new Callback<List<LookupResponse>>() {
            @Override
            public void onResponse(Call<List<LookupResponse>> call, Response<List<LookupResponse>> response) {
                listLookup = response.body();
                if (listLookup != null & listLookup.size() > 0) {
                    String[] school = new String[listLookup.size()];
                    String[] ids = new String[listLookup.size()];
                    for (int i = 0; i<listLookup.size(); i++) {
                        school[i] = listLookup.get(i).getName();
                        ids[i] = listLookup.get(i).getId();
                        ArrayAdapter<String > adapter = new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_item, school);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPendidikan.setAdapter(adapter);
                        spinnerPendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idSchool = ids[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LookupResponse>> call, Throwable t) {

            }
        });
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

    private void signUp(final String password, final String username, final Integer age, Integer selectId, final String idSchool, final String idEmploy) {
        Call<UserResponse> call = ApiConfig.getApiRequest().register(password, username, age, selectId, idSchool, idEmploy);
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
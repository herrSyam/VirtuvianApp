package com.example.virtuvianapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.adapter.QuestionAdapter;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.app.QuestionListener;
import com.example.virtuvianapplication.databinding.ActivityEventEditBinding;

import com.example.virtuvianapplication.fragment.Diet;
import com.example.virtuvianapplication.fragment.Penkes;
import com.example.virtuvianapplication.model.CheckBoxModel;
import com.example.virtuvianapplication.model.DietModel;
import com.example.virtuvianapplication.model.QuestionModel;
import com.example.virtuvianapplication.model.QuestionModelAnswer;
import com.example.virtuvianapplication.model.QuestionModelAnswerData;
import com.example.virtuvianapplication.response.PostResponse;
import com.example.virtuvianapplication.response.QuestionResponse;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventEditActivity extends AppCompatActivity implements QuestionListener {

    private ActivityEventEditBinding binding;
    private PreferenceManager preferenceManager;
    private QuestionAdapter adapter;
    private ArrayList<QuestionModel> questionModelList;
    ArrayList<CheckBoxModel> arrayList_i = new ArrayList<>();
    private LocalTime time;

    QuestionModelAnswerData questionModelAnswerData;
    QuestionModelAnswer questionModelAnswer;
    ArrayList<QuestionModelAnswerData> arrayListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventEditBinding.inflate(getLayoutInflater());
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
        Call<QuestionResponse> call = ApiConfig.getApiRequest().question();
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                QuestionResponse questionResponse = response.body();
                binding.textEvent.setText(questionResponse.getEvent());
                questionModelList = new ArrayList<>(Arrays.asList(questionResponse.getData()));
                populateListView(questionModelList);
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {

            }
        });
    }

    private void populateListView(ArrayList<QuestionModel> questionModelList)
    {
        adapter = new QuestionAdapter(questionModelList, this);
        binding.eventEditListView.setAdapter(adapter);
        binding.eventEditListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onQuestionChange(ArrayList<CheckBoxModel> arrayList) {
        arrayList_i = arrayList;
    }

    private void saveAction(ArrayList<CheckBoxModel> arrayList_i)
    {
        loading(true);
        for (int i = 0; i < arrayList_i.size(); i++)
        {
            questionModelAnswerData = new QuestionModelAnswerData(
                    preferenceManager.getString(Constants.KEY_USER_ID),
                    preferenceManager.getString(Constants.KEY_NAME),
                    arrayList_i.get(i).getDiet_id(),
                    arrayList_i.get(i).getQuestion(),
                    "Yes",
                    arrayList_i.get(i).getDiet_event_id());
            arrayListData.add(questionModelAnswerData);
        }


        questionModelAnswer = new QuestionModelAnswer("add", arrayListData);
        Call<PostResponse> call = ApiConfig.getApiRequest().addQues(questionModelAnswer);
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
package com.example.virtuvianapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.activity.EventEditActivity;
import com.example.virtuvianapplication.activity.MainActivity;
import com.example.virtuvianapplication.activity.SignInActivity;
import com.example.virtuvianapplication.adapter.CalenderAdapter;

import com.example.virtuvianapplication.adapter.EventAdapter;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.FragmentDietBinding;
import com.example.virtuvianapplication.model.EventDietModel;
import com.example.virtuvianapplication.response.AccessToken;
import com.example.virtuvianapplication.response.EventDietResponse;
import com.example.virtuvianapplication.util.CalenderUtils;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.Event;
import com.example.virtuvianapplication.util.PreferenceManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.virtuvianapplication.util.CalenderUtils.daysInMonthArray;
import static com.example.virtuvianapplication.util.CalenderUtils.monthYearFromDate;

public class Diet extends Fragment implements CalenderAdapter.OnItemListener {

    private FragmentDietBinding binding;
    private PreferenceManager preferenceManager;
    private EventAdapter eventAdapter;
    private ArrayList<EventDietModel> eventDietModels;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        CalenderUtils.selectDate = LocalDate.now();

    }

    private void showToast(String message){
        Toast.makeText(binding.getRoot().getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDietBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getToken(preferenceManager.getString(Constants.KEY_USER_ID));
        setMonthView();
        setListeners();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null)
        {
            CalenderUtils.selectDate = date;
            setMonthView();
        }
    }

    private void setListeners()
    {
        binding.createBtn.setOnClickListener(v -> createEvent());
        binding.buttonPrev.setOnClickListener(v -> prevMonth());
        binding.buttonNext.setOnClickListener(v -> nextMonth());
    }

    private void createEvent()
    {
        Intent intent = new Intent(getActivity(), EventEditActivity.class);
        ((MainActivity) getActivity()).startActivity(intent);
    }

    private void prevMonth()
    {
        CalenderUtils.selectDate = CalenderUtils.selectDate.minusMonths(1);
        setMonthView();
    }

    private void nextMonth()
    {
        CalenderUtils.selectDate = CalenderUtils.selectDate.plusMonths(1);
        setMonthView();
    }

    
    private void setMonthView(){

        binding.monthYear.setText(monthYearFromDate(CalenderUtils.selectDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalenderUtils.selectDate);

        CalenderAdapter calenderAdapter = new CalenderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        binding.calenderRecyclerView.setLayoutManager(layoutManager);
        binding.calenderRecyclerView.setAdapter(calenderAdapter);
        setEventAdapter();

    }

    @Override
    public void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        String startDate = CalenderUtils.selectDate.toString();

        Call<EventDietResponse> call = ApiConfig.getApiRequest().getEventDiet(startDate, startDate);

        call.enqueue(new Callback<EventDietResponse>() {
            @Override
            public void onResponse(Call<EventDietResponse> call, Response<EventDietResponse> response) {
                EventDietResponse eventDietResponse = response.body();
                eventDietModels = new ArrayList<>(Arrays.asList(eventDietResponse.getData()));

                //ArrayList<Event> dailyEvents = Event.eventsForDate(CalenderUtils.selectDate);
                eventAdapter = new EventAdapter(eventDietModels);
                binding.eventListView.setAdapter(eventAdapter);

            }

            @Override
            public void onFailure(Call<EventDietResponse> call, Throwable t) {
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
                        preferenceManager.putString(Constants.KEY_FCM_TOKEN, "");
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
package com.example.virtuvianapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.virtuvianapplication.adapter.CalenderAdapter;
import com.example.virtuvianapplication.adapter.EventAdapter;
import com.example.virtuvianapplication.databinding.ActivityWeekViewBinding;
import com.example.virtuvianapplication.util.CalenderUtils;
import com.example.virtuvianapplication.util.Event;
import com.example.virtuvianapplication.util.PreferenceManager;


import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.virtuvianapplication.util.CalenderUtils.daysInWeekArray;
import static com.example.virtuvianapplication.util.CalenderUtils.monthYearFromDate;

public class WeekViewActivity extends AppCompatActivity implements CalenderAdapter.OnItemListener {
    private ActivityWeekViewBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeekViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setWeekView();
    }

    private void setWeekView()
    {
        binding.monthYear.setText(monthYearFromDate(CalenderUtils.selectDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalenderUtils.selectDate);

        CalenderAdapter calenderAdapter = new CalenderAdapter(days,this);
        RecyclerView.LayoutManager layoutManager =  new GridLayoutManager(getApplicationContext(), 7);
        binding.calenderRecyclerView.setLayoutManager(layoutManager);
        binding.calenderRecyclerView.setAdapter(calenderAdapter);
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        //ArrayList<Event> dailyEvents = Event.eventsForDate(CalenderUtils.selectDate);
        //EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        //binding.eventListView.setAdapter(eventAdapter);
    }

    public void previousWeekAction(View view) {
    }

    public void nextWeekAction(View view) {
    }

    public void newEventAction(View view) {
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalenderUtils.selectDate = date;
        setWeekView();
    }
}
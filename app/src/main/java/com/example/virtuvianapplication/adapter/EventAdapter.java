package com.example.virtuvianapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.databinding.ActivityEventEditCallBinding;
import com.example.virtuvianapplication.databinding.EventCallBinding;
import com.example.virtuvianapplication.model.EventDietModel;
import com.example.virtuvianapplication.model.QuestionModel;
import com.example.virtuvianapplication.util.CalenderUtils;
import com.example.virtuvianapplication.util.Event;


import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>
{
    private final ArrayList<EventDietModel> eventDietModels;

    public EventAdapter(ArrayList<EventDietModel> eventDietModels) {
        this.eventDietModels = eventDietModels;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventCallBinding eventCallBinding = EventCallBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new EventAdapter.EventViewHolder(eventCallBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.setEvent(eventDietModels.get(position));
    }

    @Override
    public int getItemCount() {
        return eventDietModels.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        EventCallBinding binding;

        EventViewHolder(EventCallBinding eventCallBinding){
            super(eventCallBinding.getRoot());
            binding = eventCallBinding;
        }

        void setEvent(EventDietModel event){
            binding.eventCallTV.setText(event.getName());
            binding.eventText.setText(event.getEvent());
            binding.getRoot();
        }
    }
}

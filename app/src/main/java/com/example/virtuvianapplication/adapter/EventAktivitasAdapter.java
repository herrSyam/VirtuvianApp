package com.example.virtuvianapplication.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.databinding.EventCallBinding;
import com.example.virtuvianapplication.model.EventAktivitasModel;
import com.example.virtuvianapplication.model.EventDietModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EventAktivitasAdapter extends RecyclerView.Adapter<EventAktivitasAdapter.EventViewHolder> {

    private final ArrayList<EventAktivitasModel> eventAktivitasModels;

    public EventAktivitasAdapter(ArrayList<EventAktivitasModel> eventAktivitasModels) {
        this.eventAktivitasModels = eventAktivitasModels;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventCallBinding eventCallBinding  = EventCallBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new EventAktivitasAdapter.EventViewHolder(eventCallBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.setEvent(eventAktivitasModels.get(position));
    }

    @Override
    public int getItemCount() {
        return eventAktivitasModels.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        EventCallBinding binding;

        EventViewHolder(EventCallBinding eventCallBinding) {
            super(eventCallBinding.getRoot());
            binding = eventCallBinding;
        }

        void setEvent(EventAktivitasModel event){
            if (event.getAktivitas_status().equals("Aktivitas Ringan")) {
                binding.eventCallTV.setText(event.getAktivitas_status());
                binding.layout.setBackgroundResource(R.drawable.background_aktivitas_ringan);
                binding.eventCallTV.setTextColor(Color.parseColor("#000080"));
            }
            if (event.getAktivitas_status().equals("Aktivitas Sedang")) {
                binding.eventCallTV.setText(event.getAktivitas_status());
                binding.layout.setBackgroundResource(R.drawable.background_aktivitas_sedang);
                binding.eventCallTV.setTextColor(Color.parseColor("#000080"));
            }
            if (event.getAktivitas_status().equals("Aktivitas Berat")) {
                binding.eventCallTV.setText(event.getAktivitas_status());
                binding.layout.setBackgroundResource(R.drawable.background_aktivitas_berat);
                binding.eventCallTV.setTextColor(Color.parseColor("#000080"));
            }
            binding.getRoot();
        }
    }
}

package com.example.virtuvianapplication.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
            if (event.getTotal() >= 1.40 || event.getTotal() <= 1.69) {
                binding.eventCallTV.setText("Aktifitas Ringan");
                binding.eventCallTV.setBackgroundColor(Color.parseColor("#ffffff"));
                binding.eventCallTV.setTextColor(Color.parseColor("#cccccc"));
            }
            if (event.getTotal() >= 1.70 || event.getTotal() <= 1.99) {
                binding.eventCallTV.setText("Aktifitas Sedang");
                binding.eventCallTV.setBackgroundColor(Color.parseColor("#ffffff"));
                binding.eventCallTV.setTextColor(Color.parseColor("#cccccc"));
            }
            if (event.getTotal() >2.00) {
                binding.eventCallTV.setText("Aktifitas Berat");
                binding.eventCallTV.setBackgroundColor(Color.parseColor("#bce1d8"));
                binding.eventCallTV.setTextColor(Color.parseColor("#ffffff"));
            }
            binding.getRoot();
        }
    }
}

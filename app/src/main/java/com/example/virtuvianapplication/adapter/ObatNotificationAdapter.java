package com.example.virtuvianapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.databinding.ActivityObatNotificationCallBinding;
import com.example.virtuvianapplication.model.NotificationModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ObatNotificationAdapter extends RecyclerView.Adapter<ObatNotificationAdapter.ObatNotificationViewHendler> {
    private final ArrayList<NotificationModel> notificationModels;

    public ObatNotificationAdapter(ArrayList<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
    }

    @NonNull
    @Override
    public ObatNotificationViewHendler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ActivityObatNotificationCallBinding binding = ActivityObatNotificationCallBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ObatNotificationAdapter.ObatNotificationViewHendler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ObatNotificationViewHendler holder, int position) {
        holder.setBinding(notificationModels.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class ObatNotificationViewHendler extends RecyclerView.ViewHolder  {
        ActivityObatNotificationCallBinding binding;

        ObatNotificationViewHendler(ActivityObatNotificationCallBinding obatNotificationCallBinding)
        {
            super(obatNotificationCallBinding.getRoot());
            binding = obatNotificationCallBinding;
        }

        void setBinding(NotificationModel model)
        {
            binding.id.setText(model.getId());
            binding.message.setText(model.getMessage());
            binding.name.setText(model.getName());
            binding.timeEvent.setText(model.getEvent_time());
            binding.getRoot();
        }
    }
}

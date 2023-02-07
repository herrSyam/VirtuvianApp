package com.example.virtuvianapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.databinding.ActivityGdsListviewBinding;
import com.example.virtuvianapplication.model.GdsModel;
import com.example.virtuvianapplication.model.NotificationModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GdsAdapter extends RecyclerView.Adapter<GdsAdapter.GdsViewHendler> {

    private final ArrayList<GdsModel> gdsModelArrayList;

    public GdsAdapter(ArrayList<GdsModel> gdsModelArrayList) {
        this.gdsModelArrayList = gdsModelArrayList;
    }

    @NonNull
    @Override
    public GdsViewHendler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityGdsListviewBinding binding = ActivityGdsListviewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new GdsAdapter.GdsViewHendler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GdsViewHendler holder, int position) {
        holder.setBinding(gdsModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return gdsModelArrayList.size();
    }

    public class GdsViewHendler extends RecyclerView.ViewHolder {
        ActivityGdsListviewBinding binding;

        GdsViewHendler( ActivityGdsListviewBinding activityGdsListviewBinding) {
           super(activityGdsListviewBinding.getRoot());
           binding = activityGdsListviewBinding;
        }

        void setBinding(GdsModel model)
        {
            binding.date.setText(model.getUpdated());
            binding.message.setText("Normal");
            binding.mgdl.setText(model.getValue_gds().toString());
            binding.time.setText(model.getUpdatedTime());
        }
    }
}

package com.example.virtuvianapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.app.ObatListener;
import com.example.virtuvianapplication.databinding.ActivityObatAddeditCallBinding;
import com.example.virtuvianapplication.model.CheckBoxObatModel;
import com.example.virtuvianapplication.model.ObatModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ObatListAdapter extends RecyclerView.Adapter<ObatListAdapter.ObatListViewHolder> {

    private final ArrayList<ObatModel> models;
    private final ObatListener obatListener;
    CheckBoxObatModel checkBoxObatModel;
    ArrayList<CheckBoxObatModel> arrayList_o = new ArrayList<>();

    public ObatListAdapter(ArrayList<ObatModel> models, ObatListener obatListener) {
        this.models = models;
        this.obatListener = obatListener;
    }

    @NonNull
    @Override
    public ObatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityObatAddeditCallBinding binding = ActivityObatAddeditCallBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ObatListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ObatListViewHolder holder, int position) {
        holder.setObat(models.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()) {
                    checkBoxObatModel  = new CheckBoxObatModel(models.get(position).getId());
                    arrayList_o.add(checkBoxObatModel);
                } else {
                    checkBoxObatModel  = new CheckBoxObatModel(models.get(position).getId());
                    arrayList_o.remove(checkBoxObatModel);
                }
                obatListener.onObatChange(arrayList_o);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ObatListViewHolder extends RecyclerView.ViewHolder {
        ActivityObatAddeditCallBinding binding;
        CheckBox checkBox;

        ObatListViewHolder(ActivityObatAddeditCallBinding activityObatAddeditCallBinding) {
            super(activityObatAddeditCallBinding.getRoot());
            binding = activityObatAddeditCallBinding;
            checkBox = binding.checkBox;
        }

        void setObat(ObatModel model) {
            binding.editId.setText(model.getId());
            binding.editName.setText(model.getName());
            binding.getRoot();
        }
    }
}

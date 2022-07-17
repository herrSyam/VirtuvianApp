package com.example.virtuvianapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.app.AktivitasListener;
import com.example.virtuvianapplication.databinding.ActivityAktivitasEditCallBinding;
import com.example.virtuvianapplication.model.AktivitasModel;
import com.example.virtuvianapplication.model.CheckBoxAktivitasModel;
import com.example.virtuvianapplication.model.CheckBoxModel;

import java.util.ArrayList;


public class AktivitasAdapter extends RecyclerView.Adapter<AktivitasAdapter.AktivitasViewHolder> {
    private final ArrayList<AktivitasModel> models;
    private final AktivitasListener aktivitasListener;
    CheckBoxAktivitasModel checkBoxAktivitasModel;
    ArrayList<CheckBoxAktivitasModel> arrayList_o = new ArrayList<>();

    public AktivitasAdapter(ArrayList<AktivitasModel> models, AktivitasListener aktivitasListener) {
        this.models = models;
        this.aktivitasListener = aktivitasListener;
    }

    @NonNull
    @Override
    public AktivitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityAktivitasEditCallBinding activityAktivitasEditCallBinding = ActivityAktivitasEditCallBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new AktivitasViewHolder(activityAktivitasEditCallBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AktivitasViewHolder holder, int position) {
        holder.setAktivitas(models.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()){
                    checkBoxAktivitasModel = new CheckBoxAktivitasModel(models.get(position).getId(), models.get(position).getPar());
                    arrayList_o.add(checkBoxAktivitasModel);
                } else {
                    checkBoxAktivitasModel = new CheckBoxAktivitasModel(models.get(position).getId(), models.get(position).getPar());
                    arrayList_o.remove(checkBoxAktivitasModel);
                }
                aktivitasListener.onAktivitasChange(arrayList_o);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class AktivitasViewHolder extends RecyclerView.ViewHolder {
        ActivityAktivitasEditCallBinding binding;
        CheckBox checkBox;

        AktivitasViewHolder(ActivityAktivitasEditCallBinding activityAktivitasEditCallBinding) {
            super(activityAktivitasEditCallBinding.getRoot());
            binding = activityAktivitasEditCallBinding;
            checkBox = binding.checkBox;
        }

        void setAktivitas(AktivitasModel aktivitas) {
            binding.editId.setText(aktivitas.getId());
            binding.editName.setText(aktivitas.getName());
            binding.editPar.setText(aktivitas.getPar().toString());
            binding.getRoot();
        }
    }
}

package com.example.virtuvianapplication.util;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.adapter.CalenderAdapter;
import com.example.virtuvianapplication.databinding.CalenderCellBinding;

import java.time.LocalDate;
import java.util.ArrayList;


public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CalenderCellBinding binding;
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    private final CalenderAdapter.OnItemListener onItemListener;

    public CalenderViewHolder(CalenderCellBinding calenderCellBinding,  CalenderAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
        super(calenderCellBinding.getRoot());
        binding = calenderCellBinding;

        parentView = binding.parentView;
        dayOfMonth = binding.callDayText;
        this.onItemListener = onItemListener;
        binding.getRoot().setOnClickListener(this);
        this.days = days;
    }


    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}

package com.example.virtuvianapplication.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.R;
import com.example.virtuvianapplication.databinding.CalenderCellBinding;
import com.example.virtuvianapplication.util.CalenderUtils;
import com.example.virtuvianapplication.util.CalenderViewHolder;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderViewHolder> {

    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalenderAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CalenderCellBinding calenderCellBinding = CalenderCellBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        //ViewGroup.LayoutParams layoutParams = calenderCellBinding.callDayText.getLayoutParams();
        //layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalenderViewHolder(calenderCellBinding, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if (date == null)
            holder.dayOfMonth.setText("");
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            holder.parentView.setBackgroundResource(R.drawable.background_calender);
            if (date.equals(CalenderUtils.selectDate))
                holder.parentView.setBackgroundResource(R.drawable.background_calender_select);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}

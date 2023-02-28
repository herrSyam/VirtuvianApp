package com.example.virtuvianapplication.util;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ClaimsXAxisValueFormatter extends ValueFormatter {
    List<String> datesList;

    public ClaimsXAxisValueFormatter(List<String> arrayOfDates) {
        this.datesList = arrayOfDates;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Integer position = Math.round(value);
        Integer values = Math.round(value);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        if (values < 1){
            position = 0;
        }
        else if (values > 1 && values < 2) {
            position = 1;
        } else if (values > 2 && values < 3) {
            position = 2;
        } else if (values > 3 && values < 4) {
            position = 3;
        } else if (values > 4 && values <= 5) {
            position = 4;
        } else if (values > 5) {
            position = position;
        }
        if (position < datesList.size())
            return sdf.format(new Date((CalenderUtils.getDateInMilliSeconds(datesList.get(position), "dd-MM-yyyy"))));
        return "";
    }
}

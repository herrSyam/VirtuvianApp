package com.example.virtuvianapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virtuvianapplication.activity.AddEditGds;
import com.example.virtuvianapplication.activity.MainActivity;
import com.example.virtuvianapplication.adapter.GdsAdapter;
import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.FragmentGdsBinding;
import com.example.virtuvianapplication.model.GdsModel;
import com.example.virtuvianapplication.response.GdsResponse;
import com.example.virtuvianapplication.util.ClaimsXAxisValueFormatter;
import com.example.virtuvianapplication.util.Constants;
import com.example.virtuvianapplication.util.PreferenceManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gds extends Fragment {
    private FragmentGdsBinding binding;
    private PreferenceManager preferenceManager;
    LineChart lineChart;
    LineDataSet lineDataSet;
    LineData lineData;

    ArrayList<Entry> yEntrys = new ArrayList<>();
    final ArrayList<String> xEntrys = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        preferenceManager = new PreferenceManager(getContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGdsBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
        drawLineChart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setListeners();
//        setAdapter();
        drawLineChart();
    }

    private void setListeners()
    {
        binding.createBtn.setOnClickListener(v -> createGds());
    }

    private void createGds() {
        Intent intent = new Intent(getActivity(), AddEditGds.class);
        ((MainActivity) getActivity()).startActivity(intent);
    }

    private void getData() {
        Call<GdsResponse> call = ApiConfig.getApiRequest().getGdsByUserId(preferenceManager.getString(Constants.KEY_USER_ID));
        call.enqueue(new Callback<GdsResponse>() {
            @Override
            public void onResponse(Call<GdsResponse> call, Response<GdsResponse> response) {
                GdsResponse gdsResponse = response.body();
                for (int i = 0; i < gdsResponse.getData().length; i++)
                {
                    yEntrys.add(new Entry(i, gdsResponse.getData()[i].getValue_gds().floatValue()));
                    xEntrys.add(gdsResponse.getData()[i].getUpdated());
                }
                drawLineChart();
            }

            @Override
            public void onFailure(Call<GdsResponse> call, Throwable t) {
                Log.e("error", "onFailure " + t.getMessage());
            }
        });
    }


    private void drawLineChart() {
        lineChart = binding.chartGDS;
        lineDataSet = new LineDataSet(yEntrys, "Chart GDS");
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setCircleColor(Color.GREEN);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setCircleRadius(10f);
        lineDataSet.setCircleHoleRadius(10f);
        lineDataSet.setValueTextSize(12.f);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.enableDashedLine(10f, 0f, 0f);
        lineDataSet.enableDashedHighlightLine(10f, 0f, 0f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.enableGridDashedLine(2f, 7f, 0f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(315f);
        xAxis.setValueFormatter(new ClaimsXAxisValueFormatter(xEntrys));
        xAxis.setCenterAxisLabels(false);
    }
}
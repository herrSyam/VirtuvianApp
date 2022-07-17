package com.example.virtuvianapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuvianapplication.app.QuestionListener;
import com.example.virtuvianapplication.databinding.ActivityEventEditCallBinding;
import com.example.virtuvianapplication.model.CheckBoxModel;
import com.example.virtuvianapplication.model.QuestionModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private final ArrayList<QuestionModel> models;
    private final QuestionListener questionListener;
    CheckBoxModel checkBoxModel;

    ArrayList<CheckBoxModel> arrayList_o = new ArrayList<>();

    public QuestionAdapter(ArrayList<QuestionModel> models, QuestionListener questionListener) {
        this.models = models;
        this.questionListener = questionListener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityEventEditCallBinding activityEventEditCallBinding = ActivityEventEditCallBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new QuestionViewHolder(activityEventEditCallBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.setQuestion(models.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked())
                {
                    checkBoxModel = new CheckBoxModel(models.get(position).getId(), models.get(position).getName(), models.get(position).getId_event());
                    arrayList_o.add(checkBoxModel);

                } else {
                    checkBoxModel = new CheckBoxModel(models.get(position).getId(), models.get(position).getName(), models.get(position).getId_event());
                    arrayList_o.remove(checkBoxModel);
                }

                questionListener.onQuestionChange(arrayList_o);
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
       ActivityEventEditCallBinding binding;
       CheckBox checkBox;

       QuestionViewHolder(ActivityEventEditCallBinding activityEventEditCallBinding){
           super(activityEventEditCallBinding.getRoot());
           binding = activityEventEditCallBinding;
           checkBox = binding.checkBox;
       }

       void setQuestion(QuestionModel question){
           binding.editName.setText(question.getName());
           binding.editId.setText(question.getId());
           binding.editIdEvent.setText(question.getId_event());
           binding.getRoot();
       }

    }
}

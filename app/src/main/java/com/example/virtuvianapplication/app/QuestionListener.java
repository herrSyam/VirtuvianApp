package com.example.virtuvianapplication.app;

import com.example.virtuvianapplication.model.CheckBoxModel;
import com.example.virtuvianapplication.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public interface QuestionListener {
    void onQuestionChange(ArrayList<CheckBoxModel> arrayList);
}

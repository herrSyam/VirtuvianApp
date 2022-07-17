package com.example.virtuvianapplication.util;

import java.time.LocalDate;
import java.util.ArrayList;

public class Question {

    public static ArrayList<Question> questionArrayList = new ArrayList<>();

    public static ArrayList<Question> eventsForName(String Name)
    {
        ArrayList<Question> questions = new ArrayList<>();

        for (Question question : questionArrayList)
        {
            if (question.getName().equals(Name))
                questions.add(question);
        }

        return questions;
    }

    private String Id;
    private String Name;

    public Question(String id, String name) {
        Id = id;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

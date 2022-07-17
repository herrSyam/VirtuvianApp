package com.example.virtuvianapplication.model;

import javax.xml.transform.sax.SAXResult;

public class QuestionModelAnswerData {
    private String user_id;
    private String name;
    private String diet_id;
    private String question;
    private String answer;
    private String diet_event_id;

    public QuestionModelAnswerData(String user_id, String name, String diet_id, String question, String answer, String diet_event_id) {
        this.user_id = user_id;
        this.name = name;
        this.diet_id = diet_id;
        this.question = question;
        this.answer = answer;
        this.diet_event_id = diet_event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiet_id() {
        return diet_id;
    }

    public void setDiet_id(String diet_id) {
        this.diet_id = diet_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDiet_event_id() {
        return diet_event_id;
    }

    public void setDiet_event_id(String diet_event_id) {
        this.diet_event_id = diet_event_id;
    }
}

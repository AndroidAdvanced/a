package com.example.rupal.a.data;

/**
 * Created by Lincoln on 15/01/16.
 */
public class ReviewQuestion {

    private String question, answer;

    public ReviewQuestion() {
    }

    public ReviewQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setTitle(String name) {
        this.question = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String year) {
        this.answer = year;
    }

}

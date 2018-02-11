package com.example.rupal.a.utils;

/**
 * Created by Rupal on 9/24/2017.
 */
public class UserScore {

    //private variable
    private int level_number, score;
    private String category;

    // Empty constructor
    public UserScore(){

    }

    public UserScore(int level_number, int score, String category) {
        this.level_number = level_number;
        this.score = score;
        this.category = category;
    }

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
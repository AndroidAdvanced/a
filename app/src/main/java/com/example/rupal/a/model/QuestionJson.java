
package com.example.rupal.a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionJson {

    @SerializedName("questions")
    @Expose
    private List<Science> science = null;

    public List<Science> getScience() {
        return science;
    }

    public void setScience(List<Science> science) {
        this.science = science;
    }

    @Override
    public String toString() {
        return "QuestionJson{" +
                "questions=" + science +
                '}';
    }
}

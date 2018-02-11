
package com.example.rupal.a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchQuestions {

    @SerializedName("question_json")
    @Expose
    private List<QuestionJson> questionJson = null;

    public List<QuestionJson> getQuestionJson() {
        return questionJson;
    }

    public void setQuestionJson(List<QuestionJson> questionJson) {
        this.questionJson = questionJson;
    }

    @Override
    public String toString() {
        return "FetchQuestions{" +
                "questionJson=" + questionJson +
                '}';
    }
}

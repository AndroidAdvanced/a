
package com.example.rupal.a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Science {

    @SerializedName("question_id")
    @Expose
    @DatabaseField(generatedId=true)
    private int questionId;
    @SerializedName("question")
    @Expose
    @DatabaseField
    private String question;
    @SerializedName("option1")
    @Expose
    @DatabaseField
    private String option1;
    @SerializedName("option2")
    @Expose
    @DatabaseField
    private String option2;
    @SerializedName("option3")
    @Expose
    @DatabaseField
    private String option3;
    @SerializedName("answer")
    @Expose
    @DatabaseField
    private String answer;

    @SerializedName("category")
    @Expose
    @DatabaseField
    private String category;

    @SerializedName("answer_f")
    @Expose
    @DatabaseField
    private String answerF;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerF() {
        return answerF;
    }

    public void setAnswerF(String answerF) {
        this.answerF = answerF;
    }

    public String getOption(int position) {

        switch (position) {
            case 1:
                return option1;
            case 2:
                return option2;
            case 3:
                return option3;
            case 4:
                return answer;
            default:
                return "0";
        }
    }

    @Override
    public String toString() {
        return "Science{" +
                "questionId='" + questionId + '\'' +
                ", question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", answer='" + answer + '\'' +
                ", answerF='" + answerF + '\'' +
                '}';
    }

    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private WishScience list;

    public WishScience getList() {
        return list;
    }

    public void setList(WishScience list) {
        this.list = list;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}


package com.example.rupal.a.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobScreenModel {

    @SerializedName("job12")
    @Expose
    private List<Job12> job12 = null;

    public List<Job12> getJob12() {
        return job12;
    }

    public void setJob12(List<Job12> job12) {
        this.job12 = job12;
    }

    @Override
    public String toString() {
        return "JobScreenModel{" +
                "job12=" + job12 +
                '}';
    }
}

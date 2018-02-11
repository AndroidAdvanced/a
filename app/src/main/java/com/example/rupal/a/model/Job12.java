
package com.example.rupal.a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Job12 {

    @SerializedName("job_id")
    @Expose
    @DatabaseField(generatedId=true)
    private int jobId;
    @SerializedName("job_name")
    @Expose
    @DatabaseField
    private String jobName;
    @SerializedName("place")
    @Expose
    @DatabaseField
    private String place;
    @SerializedName("salary")
    @Expose
    @DatabaseField
    private String salary;
    @SerializedName("qualification")
    @Expose
    @DatabaseField
    private String qualification;
    @SerializedName("last_date")
    @Expose
    @DatabaseField
    private String lastDate;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        return "Job12{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", place='" + place + '\'' +
                ", salary='" + salary + '\'' +
                ", qualification='" + qualification + '\'' +
                ", lastDate='" + lastDate + '\'' +
                '}';
    }
}

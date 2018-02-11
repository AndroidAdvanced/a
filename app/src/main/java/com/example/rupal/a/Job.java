package com.example.rupal.a;

/**
 * Created by Rupal on 9/24/2017.
 */

public class Job {

    //private variable
    private int job_id;
    private String job_name, place, salary, qualification;
    private String last_date;

    // Empty constructor
    public Job(){

    }
    // constructor
    public Job(int job_id, String job_name, String place, String salary, String qualification, String last_date){
        this.job_id = job_id;
        this.job_name = job_name;
        this.place = place;
        this.salary = salary;
        this.qualification = qualification;

        this.last_date = last_date;
    }

    public int getJob_id() {
        return job_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public String getPlace() {
        return place;
    }

    public String getSalary() {
        return salary;
    }

    public String getQualification() {
        return qualification;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
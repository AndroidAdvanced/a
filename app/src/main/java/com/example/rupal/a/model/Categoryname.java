package com.example.rupal.a.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.google.gson.annotations.Expose;

public class Categoryname {

    @SerializedName("cat_id")
    @Expose
    @DatabaseField(generatedId=true)
    private int catId;
    @SerializedName("catname")
    @Expose

    @DatabaseField
    private String catname;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
}

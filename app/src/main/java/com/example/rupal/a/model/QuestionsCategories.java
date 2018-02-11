
package com.example.rupal.a.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionsCategories {

    @SerializedName("categoryname")
    @Expose
    private List<Categoryname> categoryname = null;

    public List<Categoryname> getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(List<Categoryname> categoryname) {
        this.categoryname = categoryname;
    }

}

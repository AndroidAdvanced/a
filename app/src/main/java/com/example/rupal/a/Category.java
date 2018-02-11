package com.example.rupal.a;

/**
 * Created by Rupal on 9/24/2017.
 */

public class Category {

    //private variable
    String category_name;

    // Empty constructor
    public Category(){

    }
    // constructor
    public Category(String categoryName){

    }

    // getting name
    public String getCategoryName() {
        return this.category_name;
    }


    // setting name
    public void setCategoryName(String name){
        this.category_name = name;
    }
}
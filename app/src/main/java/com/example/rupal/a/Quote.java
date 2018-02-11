package com.example.rupal.a;

/**
 * Created by Rupal on 9/24/2017.
 */

public class Quote {

    //private variable
    String quote_desc;

    int quote_id;

    // Empty constructor
    public Quote(){

    }
    // constructor
    public Quote(int quote_id, String quote_desc){

        this.quote_id = quote_id;
        this.quote_desc = quote_desc;
    }

    // getting name
    public String getQuoteDesc(){
        return this.quote_desc;
    }


    public int getQuoteId(){
        return this.quote_id;
    }


    // setting name
    public void setName(String name){
        this.quote_desc = name;
    }
}
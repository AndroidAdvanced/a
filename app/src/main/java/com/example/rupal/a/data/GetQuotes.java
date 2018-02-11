
package com.example.rupal.a.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetQuotes {

    @SerializedName("position")
    @Expose
    private List<Position> position = null;

    public List<Position> getPosition() {
        return position;
    }

    public void setPosition(List<Position> position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "GetQuotes{" +
                "position=" + position +
                '}';
    }
}

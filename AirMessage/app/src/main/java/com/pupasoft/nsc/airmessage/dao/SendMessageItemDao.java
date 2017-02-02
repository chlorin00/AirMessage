package com.pupasoft.nsc.airmessage.dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by wong_ on 01-Feb-17.
 */
public class SendMessageItemDao {

    @SerializedName("locationid")       private int locationId;
    @SerializedName("statement")        private String statement;
    @SerializedName("date")             private String date;
    @SerializedName("name")             private String name;
    @SerializedName("category")         private String category;

    public SendMessageItemDao() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

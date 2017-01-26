package com.pupasoft.nsc.airmessage.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class MessageItemDao {
    @SerializedName("id")           private int id;
    @SerializedName("date")         private String date;
    @SerializedName("locationid")   private int locationId;
    @SerializedName("statement")    private String statement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}

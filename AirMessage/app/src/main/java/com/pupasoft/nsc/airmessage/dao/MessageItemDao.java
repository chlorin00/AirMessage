package com.pupasoft.nsc.airmessage.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class MessageItemDao implements Parcelable{
    @SerializedName("id")           private int id;
    @SerializedName("date")         private String date;
    @SerializedName("name")         private String name;
    @SerializedName("locationid")   private int locationId;
    @SerializedName("statement")    private String statement;
    @SerializedName("category")     private String category;

    public MessageItemDao() {

    }

    protected MessageItemDao(Parcel in) {
        id = in.readInt();
        date = in.readString();
        name = in.readString();
        locationId = in.readInt();
        statement = in.readString();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(name);
        dest.writeInt(locationId);
        dest.writeString(statement);
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageItemDao> CREATOR = new Creator<MessageItemDao>() {
        @Override
        public MessageItemDao createFromParcel(Parcel in) {
            return new MessageItemDao(in);
        }

        @Override
        public MessageItemDao[] newArray(int size) {
            return new MessageItemDao[size];
        }
    };

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

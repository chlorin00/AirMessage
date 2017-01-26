package com.pupasoft.nsc.airmessage.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class MessageItemCollectionDao {
    @SerializedName("idlocation")   private int idLocation;
    @SerializedName("success")      private boolean success;
    @SerializedName("information")  private List<MessageItemDao> information;

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<MessageItemDao> getInformation() {
        return information;
    }

    public void setInformation(List<MessageItemDao> information) {
        this.information = information;
    }
}

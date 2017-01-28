package com.pupasoft.nsc.airmessage.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class MessageItemCollectionDao implements Parcelable {
    @SerializedName("idlocation")   private int idLocation;
    @SerializedName("currentid")    private int currentId;
    @SerializedName("success")      private boolean success;
    @SerializedName("information")  private List<MessageItemDao> information;

    public MessageItemCollectionDao(int idLocation) {
        this.idLocation = idLocation;
        this.currentId = 0;
    }

    public MessageItemCollectionDao(int idLocation, int currentId) {
        this.idLocation = idLocation;
        this.currentId = currentId;
    }

    protected MessageItemCollectionDao(Parcel in) {
        idLocation = in.readInt();
        currentId = in.readInt();
        success = in.readByte() != 0;
        information = in.createTypedArrayList(MessageItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idLocation);
        dest.writeInt(currentId);
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeTypedList(information);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageItemCollectionDao> CREATOR = new Creator<MessageItemCollectionDao>() {
        @Override
        public MessageItemCollectionDao createFromParcel(Parcel in) {
            return new MessageItemCollectionDao(in);
        }

        @Override
        public MessageItemCollectionDao[] newArray(int size) {
            return new MessageItemCollectionDao[size];
        }
    };

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

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }
}

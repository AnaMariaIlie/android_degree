package com.journaldev.barcodevisionapi.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Interaction  implements Parcelable {


    private String id;

    private String firstIngredientName;

    private String secondIngredientName;

    private String toxicityLevel;

    private String description;

    public Interaction(String firstIngredientName, String secondIngredientName, String toxicityLevel, String description) {
        this.firstIngredientName = firstIngredientName;
        this.secondIngredientName = secondIngredientName;
        this.toxicityLevel = toxicityLevel;
        this.description = description;
    }

    public Interaction(){
    }

    protected Interaction(Parcel in) {
        id = in.readString();
        firstIngredientName = in.readString();
        secondIngredientName = in.readString();
        toxicityLevel = in.readString();
        description = in.readString();
    }

    public static final Creator<Interaction> CREATOR = new Creator<Interaction>() {
        @Override
        public Interaction createFromParcel(Parcel in) {
            return new Interaction(in);
        }

        @Override
        public Interaction[] newArray(int size) {
            return new Interaction[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstIngredientName() {
        return firstIngredientName;
    }

    public void setFirstIngredientName(String firstIngredientName) {
        this.firstIngredientName = firstIngredientName;
    }

    public String getSecondIngredientName() {
        return secondIngredientName;
    }

    public void setSecondIngredientName(String secondIngredientName) {
        this.secondIngredientName = secondIngredientName;
    }

    public String getToxicityLevel() {
        return toxicityLevel;
    }

    public void setToxicityLevel(String toxicityLevel) {
        this.toxicityLevel = toxicityLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(firstIngredientName);
        parcel.writeString(secondIngredientName);
        parcel.writeString(toxicityLevel);
        parcel.writeString(description);
    }

    @Override
    public String toString() {
        return "Interaction{" +
                "id='" + id + '\'' +
                ", firstIngredientName='" + firstIngredientName + '\'' +
                ", secondIngredientName='" + secondIngredientName + '\'' +
                ", toxicityLevel='" + toxicityLevel + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

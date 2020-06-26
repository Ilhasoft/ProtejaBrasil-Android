package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john-mac on 4/29/16.
 */
public class InternetViolation implements Parcelable {

    private String id;

    private String title;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
    }

    public InternetViolation() {
    }

    protected InternetViolation(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Creator<InternetViolation> CREATOR = new Creator<InternetViolation>() {
        @Override
        public InternetViolation createFromParcel(Parcel source) {
            return new InternetViolation(source);
        }

        @Override
        public InternetViolation[] newArray(int size) {
            return new InternetViolation[size];
        }
    };

    @Override
    public String toString() {
        return title;
    }
}

package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johncordeiro on 10/10/15.
 */
public class State implements Parcelable {

    private String initials;

    private String title;

    private Float latitude;

    private Float longitude;

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return title;
    }

    public State(String initials) {
        this.initials = initials;
    }

    public State(String initials, String title) {
        this.initials = initials;
        this.title = title;
    }

    public State(String initials, String title, Float latitude, Float longitude) {
        this.initials = initials;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.initials);
        dest.writeString(this.title);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    protected State(Parcel in) {
        this.initials = in.readString();
        this.title = in.readString();
        this.latitude = (Float) in.readValue(Float.class.getClassLoader());
        this.longitude = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        public State createFromParcel(Parcel source) {
            return new State(source);
        }

        public State[] newArray(int size) {
            return new State[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return initials.equals(state.initials);

    }

    @Override
    public int hashCode() {
        return initials.hashCode();
    }
}

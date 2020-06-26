package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.Normalizer;

/**
 * Created by john-mac on 1/11/16.
 */
public class City implements Parcelable, Comparable<City> {

    @SerializedName("city_code")
    private String code;

    @SerializedName("city_name")
    private String name;

    private String state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.state);
    }

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    protected City(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.state = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;
        return code.equals(city.code);

    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public int compareTo(City another) {
        String strippedName = stripAccents(name);
        String strippedComparedName = stripAccents(another.name);
        return strippedName.compareTo(strippedComparedName);
    }

    private static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}

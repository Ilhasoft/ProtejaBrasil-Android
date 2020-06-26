package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class NetworkType implements Parcelable {

    private Integer id;

    private String icon;

    private String color;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkType that = (NetworkType) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.icon);
        dest.writeString(this.color);
        dest.writeString(this.name);
    }

    public NetworkType() {
    }

    protected NetworkType(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.icon = in.readString();
        this.color = in.readString();
        this.name = in.readString();
    }

    public static final Creator<NetworkType> CREATOR = new Creator<NetworkType>() {
        public NetworkType createFromParcel(Parcel source) {
            return new NetworkType(source);
        }

        public NetworkType[] newArray(int size) {
            return new NetworkType[size];
        }
    };

    @Override
    public String toString() {
        return "NetworkType{" +
                "color='" + color + '\'' +
                ", id=" + id +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

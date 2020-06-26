package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class Theme implements Parcelable {

    private Integer id;

    private String title;

    private String icon;

    private String color;

    private String description;

    @SerializedName("sondha_id")
    private Integer sondhaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSondhaId() {
        return sondhaId;
    }

    public void setSondhaId(Integer sondhaId) {
        this.sondhaId = sondhaId;
    }

    public Theme(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theme theme = (Theme) o;
        return id.equals(theme.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.icon);
        dest.writeString(this.color);
        dest.writeString(this.description);
    }

    protected Theme(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.icon = in.readString();
        this.color = in.readString();
        this.description = in.readString();
    }

    public static final Creator<Theme> CREATOR = new Creator<Theme>() {
        public Theme createFromParcel(Parcel source) {
            return new Theme(source);
        }

        public Theme[] newArray(int size) {
            return new Theme[size];
        }
    };
}

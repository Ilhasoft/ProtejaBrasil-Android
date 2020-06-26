package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class Violation implements Parcelable {

    private Integer id;

    private String name;

    private String description;

    private Theme theme;

    private List<NetworkType> types;

    private String color;

    private String icon;

    private String action;

    private List<ViolationCategory> categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public List<NetworkType> getTypes() {
        return types;
    }

    public void setTypes(List<NetworkType> types) {
        this.types = types;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<ViolationCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ViolationCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Violation violation = (Violation) o;
        return id.equals(violation.id);
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
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeParcelable(this.theme, 0);
        dest.writeTypedList(types);
        dest.writeString(this.color);
        dest.writeString(this.icon);
        dest.writeString(this.action);
        dest.writeTypedList(categories);
    }

    public Violation() {
    }

    protected Violation(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.theme = in.readParcelable(Theme.class.getClassLoader());
        this.types = in.createTypedArrayList(NetworkType.CREATOR);
        this.color = in.readString();
        this.icon = in.readString();
        this.action = in.readString();
        this.categories = in.createTypedArrayList(ViolationCategory.CREATOR);
    }

    public static final Creator<Violation> CREATOR = new Creator<Violation>() {
        public Violation createFromParcel(Parcel source) {
            return new Violation(source);
        }

        public Violation[] newArray(int size) {
            return new Violation[size];
        }
    };


}

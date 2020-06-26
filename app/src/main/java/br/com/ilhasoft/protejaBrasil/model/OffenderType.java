package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johncordeiro on 19/10/15.
 */
public class OffenderType implements Parcelable {

    private String id;

    private String name;

    public OffenderType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OffenderType that = (OffenderType) o;
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
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected OffenderType(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<OffenderType> CREATOR = new Creator<OffenderType>() {
        public OffenderType createFromParcel(Parcel source) {
            return new OffenderType(source);
        }

        public OffenderType[] newArray(int size) {
            return new OffenderType[size];
        }
    };

    @Override
    public String toString() {
        return name;
    }
}

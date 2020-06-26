package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johncordeiro on 19/10/15.
 */
public class ResponseObject implements Parcelable {

    private Integer id;

    private String displayName;

    private String keyName;

    public ResponseObject(Integer id, String displayName, String keyName) {
        this.id = id;
        this.displayName = displayName;
        this.keyName = keyName;
    }

    public ResponseObject(Integer id, String displayName) {
        this.id = id;
        this.displayName = displayName;
        this.keyName = displayName;
    }

    public Integer getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getKeyName() {
        return keyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseObject that = (ResponseObject) o;
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
        dest.writeString(this.displayName);
    }

    public ResponseObject(Integer id) {
        this.id = id;
    }

    protected ResponseObject(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.displayName = in.readString();
    }

    public static final Creator<ResponseObject> CREATOR = new Creator<ResponseObject>() {
        public ResponseObject createFromParcel(Parcel source) {
            return new ResponseObject(source);
        }

        public ResponseObject[] newArray(int size) {
            return new ResponseObject[size];
        }
    };

    @Override
    public String toString() {
        return displayName;
    }
}

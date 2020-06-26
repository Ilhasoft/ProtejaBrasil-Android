package br.com.ilhasoft.protejaBrasil.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class ViolationCategory implements Parcelable {

    public enum Category {
        Violation,
        InternetCrime,
        Accessibility;
    }

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.category == null ? -1 : this.category.ordinal());
    }

    public ViolationCategory(Category category) {
        this.category = category;
    }

    public ViolationCategory() {
    }

    protected ViolationCategory(Parcel in) {
        int tmpCategory = in.readInt();
        this.category = tmpCategory == -1 ? null : Category.values()[tmpCategory];
    }

    public static final Creator<ViolationCategory> CREATOR = new Creator<ViolationCategory>() {
        public ViolationCategory createFromParcel(Parcel source) {
            return new ViolationCategory(source);
        }

        public ViolationCategory[] newArray(int size) {
            return new ViolationCategory[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViolationCategory that = (ViolationCategory) o;

        return category == that.category;

    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }
}

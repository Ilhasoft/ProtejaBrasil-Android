package br.com.ilhasoft.protejaBrasil.model.view;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.ilhasoft.support.databinding.bindings.BindableField;
import br.com.ilhasoft.support.databinding.bindings.BindableString;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class InternetCrimeViewModel implements Parcelable {

    public BindableField violationType = new BindableField();
    public BindableString website = new BindableString();
    public BindableString description = new BindableString();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.violationType, 0);
        dest.writeParcelable(this.website, 0);
        dest.writeParcelable(this.description, 0);
    }

    public InternetCrimeViewModel() {
    }

    protected InternetCrimeViewModel(Parcel in) {
        this.violationType = in.readParcelable(BindableField.class.getClassLoader());
        this.website = in.readParcelable(BindableString.class.getClassLoader());
        this.description = in.readParcelable(BindableString.class.getClassLoader());
    }

    public static final Creator<InternetCrimeViewModel> CREATOR = new Creator<InternetCrimeViewModel>() {
        public InternetCrimeViewModel createFromParcel(Parcel source) {
            return new InternetCrimeViewModel(source);
        }

        public InternetCrimeViewModel[] newArray(int size) {
            return new InternetCrimeViewModel[size];
        }
    };
}

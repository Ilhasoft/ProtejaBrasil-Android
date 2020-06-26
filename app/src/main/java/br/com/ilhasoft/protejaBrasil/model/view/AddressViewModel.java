package br.com.ilhasoft.protejaBrasil.model.view;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.ilhasoft.support.databinding.bindings.BindableField;
import br.com.ilhasoft.support.databinding.bindings.BindableString;

/**
 * Created by johncordeiro on 19/10/15.
 */
public class AddressViewModel implements Parcelable {

    public BindableField city = new BindableField();
    public BindableField state = new BindableField();
    public BindableString address = new BindableString();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.city, 0);
        dest.writeParcelable(this.address, 0);
        dest.writeParcelable(this.state, 0);
    }

    public AddressViewModel() {
    }

    protected AddressViewModel(Parcel in) {
        this.city = in.readParcelable(BindableString.class.getClassLoader());
        this.address = in.readParcelable(BindableString.class.getClassLoader());
        this.state = in.readParcelable(BindableField.class.getClassLoader());
    }

    public static final Creator<AddressViewModel> CREATOR = new Creator<AddressViewModel>() {
        public AddressViewModel createFromParcel(Parcel source) {
            return new AddressViewModel(source);
        }

        public AddressViewModel[] newArray(int size) {
            return new AddressViewModel[size];
        }
    };
}

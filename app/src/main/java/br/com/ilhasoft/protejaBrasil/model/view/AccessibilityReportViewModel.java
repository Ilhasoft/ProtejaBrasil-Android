package br.com.ilhasoft.protejaBrasil.model.view;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.ilhasoft.support.databinding.bindings.BindableField;
import br.com.ilhasoft.support.databinding.bindings.BindableString;

/**
 * Created by johncordeiro on 18/10/15.
 */
public class AccessibilityReportViewModel implements Parcelable {

    public BindableString offenderName = new BindableString();
    public BindableField offenderType = new BindableField();
    public AddressViewModel offenderAddress = new AddressViewModel();
    public BindableField violationType = new BindableField();
    public BindableField violation = new BindableField();
    public BindableString otherViolation = new BindableString();
    public BindableString violationDescription = new BindableString();
    public BindableString institutionActivated = new BindableString();
    public BindableString victimName = new BindableString();
    public AddressViewModel victimAddress = new AddressViewModel();
    public BindableField victimAgeGroup = new BindableField();
    public BindableField victimEthnicity = new BindableField();
    public BindableField victimGender = new BindableField();
    public BindableField victimSexOption = new BindableField();

    public AccessibilityReportViewModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.offenderName, flags);
        dest.writeParcelable(this.offenderType, flags);
        dest.writeParcelable(this.offenderAddress, flags);
        dest.writeParcelable(this.violationType, flags);
        dest.writeParcelable(this.violation, flags);
        dest.writeParcelable(this.otherViolation, flags);
        dest.writeParcelable(this.violationDescription, flags);
        dest.writeParcelable(this.institutionActivated, flags);
        dest.writeParcelable(this.victimName, flags);
        dest.writeParcelable(this.victimAddress, flags);
        dest.writeParcelable(this.victimAgeGroup, flags);
        dest.writeParcelable(this.victimEthnicity, flags);
        dest.writeParcelable(this.victimGender, flags);
        dest.writeParcelable(this.victimSexOption, flags);
    }

    protected AccessibilityReportViewModel(Parcel in) {
        this.offenderName = in.readParcelable(BindableString.class.getClassLoader());
        this.offenderType = in.readParcelable(BindableField.class.getClassLoader());
        this.offenderAddress = in.readParcelable(AddressViewModel.class.getClassLoader());
        this.violationType = in.readParcelable(BindableField.class.getClassLoader());
        this.violation = in.readParcelable(BindableField.class.getClassLoader());
        this.otherViolation = in.readParcelable(BindableString.class.getClassLoader());
        this.violationDescription = in.readParcelable(BindableString.class.getClassLoader());
        this.institutionActivated = in.readParcelable(BindableString.class.getClassLoader());
        this.victimName = in.readParcelable(BindableString.class.getClassLoader());
        this.victimAddress = in.readParcelable(AddressViewModel.class.getClassLoader());
        this.victimAgeGroup = in.readParcelable(BindableField.class.getClassLoader());
        this.victimEthnicity = in.readParcelable(BindableField.class.getClassLoader());
        this.victimGender = in.readParcelable(BindableField.class.getClassLoader());
        this.victimSexOption = in.readParcelable(BindableField.class.getClassLoader());
    }

    public static final Creator<AccessibilityReportViewModel> CREATOR = new Creator<AccessibilityReportViewModel>() {
        @Override
        public AccessibilityReportViewModel createFromParcel(Parcel source) {
            return new AccessibilityReportViewModel(source);
        }

        @Override
        public AccessibilityReportViewModel[] newArray(int size) {
            return new AccessibilityReportViewModel[size];
        }
    };
}

package br.com.ilhasoft.protejaBrasil.model.view;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.ilhasoft.support.databinding.bindings.BindableField;
import br.com.ilhasoft.support.databinding.bindings.BindableString;

/**
 * Created by johncordeiro on 18/10/15.
 */
public class ViolationReportViewModel implements Parcelable {

    public BindableString victimName = new BindableString();
    public BindableString victimLocation = new BindableString();
    public BindableField reportTheme = new BindableField();
    public BindableField subtypes = new BindableField();
    public BindableField offenderType = new BindableField();
    public BindableString offenderName = new BindableString();
    public BindableField violationPlace = new BindableField();
    public BindableField violationTime = new BindableField();
    public BindableString institutionActivated = new BindableString();
    public BindableString violationDescription = new BindableString();
    public AddressViewModel victimAddress = new AddressViewModel();
    public BindableField victimAgeGroup = new BindableField();
    public BindableField victimEthnicity = new BindableField();
    public BindableField victimGender = new BindableField();
    public BindableField victimSexOption = new BindableField();
    public AddressViewModel offenderAddress = new AddressViewModel();
    public BindableField offenderAgeGroup = new BindableField();
    public BindableField offenderEthnicity = new BindableField();
    public BindableField offenderGender = new BindableField();
    public BindableField offenderSexOption = new BindableField();

    public ViolationReportViewModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.victimName, 0);
        dest.writeParcelable(this.victimLocation, 0);
        dest.writeParcelable(this.reportTheme, 0);
        dest.writeParcelable(this.subtypes, 0);
        dest.writeParcelable(this.offenderType, 0);
        dest.writeParcelable(this.offenderName, 0);
        dest.writeParcelable(this.violationPlace, 0);
        dest.writeParcelable(this.violationTime, 0);
        dest.writeParcelable(this.institutionActivated, 0);
        dest.writeParcelable(this.violationDescription, 0);
        dest.writeParcelable(this.victimAddress, 0);
        dest.writeParcelable(this.victimAgeGroup, 0);
        dest.writeParcelable(this.victimEthnicity, 0);
        dest.writeParcelable(this.victimGender, 0);
        dest.writeParcelable(this.victimSexOption, 0);
        dest.writeParcelable(this.offenderAddress, 0);
        dest.writeParcelable(this.offenderAgeGroup, 0);
        dest.writeParcelable(this.offenderEthnicity, 0);
        dest.writeParcelable(this.offenderGender, 0);
        dest.writeParcelable(this.offenderSexOption, 0);
    }

    protected ViolationReportViewModel(Parcel in) {
        this.victimName = in.readParcelable(BindableString.class.getClassLoader());
        this.victimLocation = in.readParcelable(BindableString.class.getClassLoader());
        this.reportTheme = in.readParcelable(BindableField.class.getClassLoader());
        this.subtypes = in.readParcelable(BindableField.class.getClassLoader());
        this.offenderType = in.readParcelable(BindableField.class.getClassLoader());
        this.offenderName = in.readParcelable(BindableString.class.getClassLoader());
        this.violationPlace = in.readParcelable(BindableField.class.getClassLoader());
        this.violationTime = in.readParcelable(BindableField.class.getClassLoader());
        this.institutionActivated = in.readParcelable(BindableField.class.getClassLoader());
        this.violationDescription = in.readParcelable(BindableField.class.getClassLoader());
        this.victimAddress = in.readParcelable(AddressViewModel.class.getClassLoader());
        this.victimAgeGroup = in.readParcelable(BindableField.class.getClassLoader());
        this.victimEthnicity = in.readParcelable(BindableField.class.getClassLoader());
        this.victimGender = in.readParcelable(BindableField.class.getClassLoader());
        this.victimSexOption = in.readParcelable(BindableField.class.getClassLoader());
        this.offenderAddress = in.readParcelable(AddressViewModel.class.getClassLoader());
        this.offenderAgeGroup = in.readParcelable(BindableField.class.getClassLoader());
        this.offenderEthnicity = in.readParcelable(BindableField.class.getClassLoader());
        this.offenderGender = in.readParcelable(BindableField.class.getClassLoader());
        this.offenderSexOption = in.readParcelable(BindableField.class.getClassLoader());
    }

    public static final Creator<ViolationReportViewModel> CREATOR = new Creator<ViolationReportViewModel>() {
        public ViolationReportViewModel createFromParcel(Parcel source) {
            return new ViolationReportViewModel(source);
        }

        public ViolationReportViewModel[] newArray(int size) {
            return new ViolationReportViewModel[size];
        }
    };
}

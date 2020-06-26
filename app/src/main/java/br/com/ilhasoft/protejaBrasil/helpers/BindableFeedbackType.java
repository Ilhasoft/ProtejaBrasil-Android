package br.com.ilhasoft.protejaBrasil.helpers;

import android.os.Parcel;

import br.com.ilhasoft.protejaBrasil.model.Feedback;
import br.com.ilhasoft.support.databinding.bindings.Bindable;

/**
 * Created by john-mac on 1/8/16.
 */
public class BindableFeedbackType extends Bindable<Feedback.FeedbackType> {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value == null ? -1 : this.value.ordinal());
    }

    public BindableFeedbackType(Feedback.FeedbackType value) {
        super(value);
    }

    public BindableFeedbackType() {
    }

    protected BindableFeedbackType(Parcel in) {
        int tmpValue = in.readInt();
        this.value = tmpValue == -1 ? null : Feedback.FeedbackType.values()[tmpValue];
    }

    public static final Creator<BindableFeedbackType> CREATOR = new Creator<BindableFeedbackType>() {
        public BindableFeedbackType createFromParcel(Parcel source) {
            return new BindableFeedbackType(source);
        }

        public BindableFeedbackType[] newArray(int size) {
            return new BindableFeedbackType[size];
        }
    };
}

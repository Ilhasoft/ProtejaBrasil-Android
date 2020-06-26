package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.support.databinding.bindings.BindableField;
import br.com.ilhasoft.support.databinding.bindings.BindableString;

/**
 * Created by johncordeiro on 18/10/15.
 */
public abstract class BaseReportFragment<ViewModelType extends Parcelable> extends Fragment {

    private static final String TAG = "BaseReportFragment";

    protected static final String EXTRA_VIEW_MODEL = "viewModel";
    protected static final String EXTRA_REQUIRED_FIELDS = "requiredFields";

    protected ViewModelType viewModel;
    protected boolean hasRequiredFields;

    public static <ViewModelType extends Parcelable> BaseReportFragment newInstance(
            Class<? extends BaseReportFragment> mClass, ViewModelType viewModel, boolean hasRequiredFields) {
        try {
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_VIEW_MODEL, viewModel);
            args.putBoolean(EXTRA_REQUIRED_FIELDS, hasRequiredFields);

            BaseReportFragment fragment = mClass.newInstance();
            fragment.setArguments(args);
            return fragment;
        } catch(Exception exception) {
            Log.e(TAG, "newInstance ", exception);
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getArguments().getParcelable(EXTRA_VIEW_MODEL);
        hasRequiredFields = getArguments().getBoolean(EXTRA_REQUIRED_FIELDS, false);

        FragmentActivity activity = getActivity();
        if (activity instanceof MultipleStepsReport) {
            if (hasRequiredFields) {
                ((MultipleStepsReport) activity).showRequiredFieldsWarning();
            } else {
                ((MultipleStepsReport) activity).hideRequiredFieldsWarning();
            }
        }

        setupData(viewModel);
    }

    protected abstract void setupData(ViewModelType viewModel);

    public abstract boolean validateForm();

    protected boolean validateSpinner(Spinner spinner, BindableField bindableField) {
        if(bindableField.exists()) {
            spinner.setBackgroundResource(R.drawable.default_spinner_background);
            return true;
        }
        spinner.setBackgroundResource(R.drawable.error_spinner_background);
        return false;
    }

    protected boolean validateText(EditText editText, BindableString bindableString) {
        if(!bindableString.isEmpty()) {
            editText.setBackgroundResource(R.drawable.selector_edittext_focus_background);
            return true;
        }
        editText.setBackgroundResource(R.drawable.shape_error_background);
        return false;
    }

    protected void setDefaultValueIfNeeded(BindableString field, String value) {
        if(!field.exists() || field.isEmpty()) {
            field.set(value);
        }
    }

    protected void setDefaultValueIfNeeded(BindableField field, int id) {
        if(!field.exists()) {
            field.set(new ResponseObject(id));
        }
    }
}

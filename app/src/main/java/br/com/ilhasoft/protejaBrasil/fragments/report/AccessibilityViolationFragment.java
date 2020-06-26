package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentAccessibilityViolationBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.managers.AccessibilityDataManager;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;
import br.com.ilhasoft.support.databinding.bindings.Bindable;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class AccessibilityViolationFragment extends BaseReportFragment<AccessibilityReportViewModel> {

    private FragmentAccessibilityViolationBinding binding;

    public static BaseReportFragment newInstance(ViolationReportViewModel viewModel, boolean hasRequiredFields) {
        return BaseReportFragment.newInstance(AccessibilityViolationFragment.class, viewModel, hasRequiredFields);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accessibility_violation, container, false);
        setupView();
        return binding.getRoot();
    }

    private void setupView() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionViolation);
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionViolationDescription);
    }

    @Override
    protected void setupData(AccessibilityReportViewModel viewModel) {
        binding.setViewModel(viewModel);

        binding.setViolationTypes(AccessibilityDataManager.getViolationTypes(getContext()));
        binding.setViolations(AccessibilityDataManager.getViolations(getContext()));
        viewModel.violationType.setOnBindableChangedListener(onBindableChangedListener);
    }

    @Override
    public boolean validateForm() {
        boolean lackOfAccessibility = isOtherLackOfAccessibility(viewModel.violationType.get());
        return validateSpinner(binding.violationTypeSpinner, viewModel.violationType)
            && ((lackOfAccessibility && validateText(binding.otherViolation, viewModel.otherViolation))
            || (!lackOfAccessibility && validateSpinner(binding.violationsSpinner, viewModel.violation)))
            && validateText(binding.violationDescription, viewModel.violationDescription);
    }

    private Bindable.OnBindableChangedListener<Parcelable> onBindableChangedListener = new Bindable.OnBindableChangedListener<Parcelable>() {
        @Override
        public void onBindableChanged(Parcelable object) {
            if (isOtherLackOfAccessibility(object)) {
                binding.getViewModel().violation.set(new ResponseObject(AccessibilityDataManager.OTHER_LACK_ACCESSIBILITY_VIOLATION_ID));
                binding.violationsSpinner.setVisibility(View.GONE);
                binding.otherViolation.setVisibility(View.VISIBLE);
            } else {
                binding.getViewModel().otherViolation.set("");
                binding.violationsSpinner.setVisibility(View.VISIBLE);
                binding.otherViolation.setVisibility(View.GONE);
            }
        }
    };

    private boolean isOtherLackOfAccessibility(Parcelable object) {
        ResponseObject responseObject = (ResponseObject) object;
        return responseObject != null
                && responseObject.getId().equals(AccessibilityDataManager.OTHER_LACK_ACCESSIBILITY_ID);
    }

}

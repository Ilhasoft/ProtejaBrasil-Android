package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentAccessibilityInstitutionBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class AccessibilityInstitutionFragment extends BaseReportFragment<AccessibilityReportViewModel> {

    private FragmentAccessibilityInstitutionBinding binding;

    public static BaseReportFragment newInstance(ViolationReportViewModel viewModel, boolean hasRequiredFields) {
        return BaseReportFragment.newInstance(AccessibilityInstitutionFragment.class, viewModel, hasRequiredFields);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accessibility_institution, container, false);
        setupView();
        return binding.getRoot();
    }

    private void setupView() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionInstitutionActivated);
        binding.typeGroup.setOnCheckedChangeListener(onActivatedChangedListener);
    }

    @Override
    protected void setupData(AccessibilityReportViewModel viewModel) {
        binding.setViewModel(viewModel);
        binding.setInstitutionActivated(true);
    }

    @Override
    public boolean validateForm() {
        return !binding.getInstitutionActivated()
            || validateText(binding.institutionActivatedText, viewModel.institutionActivated);
    }

    private RadioGroup.OnCheckedChangeListener onActivatedChangedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            binding.setInstitutionActivated(checkedId == R.id.yes);
        }
    };

}

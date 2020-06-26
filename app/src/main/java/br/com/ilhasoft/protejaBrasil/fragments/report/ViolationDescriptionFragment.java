package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentViolationDescriptionBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 19/10/15.
 */
public class ViolationDescriptionFragment extends BaseReportFragment<ViolationReportViewModel> {

    private FragmentViolationDescriptionBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_violation_description, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    @Override
    protected void setupData(ViolationReportViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    private void setupViews() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionViolationDescription);
        binding.institutionActivatedRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.yes:
                        binding.institutionActivated.setVisibility(View.VISIBLE); break;
                    case R.id.no:
                        binding.getViewModel().institutionActivated.set("");
                        binding.institutionActivated.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean validateForm() {
        boolean institutionValid = binding.institutionActivated.getVisibility() == View.GONE
                || validateText(binding.institutionActivated, binding.getViewModel().institutionActivated);
        boolean descriptionValid = validateText(binding.violationDescription, binding.getViewModel().violationDescription);

        if(institutionValid && descriptionValid) {
            setDefaultValueIfNeeded(viewModel.institutionActivated, "Não informado");
            return true;
        }
        return false;
    }

}

package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentViolationInfoBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.managers.DefaultDataManager;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 19/10/15.
 */
public class ViolationInfoFragment extends BaseReportFragment<ViolationReportViewModel> {

    private FragmentViolationInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_violation_info, container, false);
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
        binding.setFrequencies(DefaultDataManager.getFrequencies(getContext()));
        binding.setPlaces(DefaultDataManager.getViolationPlaces(getContext()));
    }

    private void setupViews() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionVictimViolationPlace);
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionVictimHowLong);
    }

    @Override
    public boolean validateForm() {
        return validateSpinner(binding.placesSpinner, binding.getViewModel().violationPlace)
            & validateSpinner(binding.frequenciesSpinner, binding.getViewModel().violationTime);
    }
}

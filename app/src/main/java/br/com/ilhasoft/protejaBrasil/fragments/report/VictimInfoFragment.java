package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentVictimInfoBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 17/10/15.
 */
public class VictimInfoFragment extends BaseReportFragment<ViolationReportViewModel> {

    private static final String TAG = "ViolationTypeFragment";

    private FragmentVictimInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_victim_info, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    protected void setupData(ViolationReportViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    private void setupViews() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionVictimName);
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionVictimLocale);
    }

    @Override
    public boolean validateForm() {
        return validateText(binding.victimName, binding.getViewModel().victimName)
            && validateText(binding.victimLocation, binding.getViewModel().victimLocation);
    }
}

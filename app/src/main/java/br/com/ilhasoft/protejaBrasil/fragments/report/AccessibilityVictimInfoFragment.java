package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentSubjectInfoBinding;
import br.com.ilhasoft.protejaBrasil.managers.DefaultDataManager;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class AccessibilityVictimInfoFragment extends BaseReportFragment<AccessibilityReportViewModel> {

    private FragmentSubjectInfoBinding binding;

    public static BaseReportFragment newInstance(ViolationReportViewModel viewModel, boolean hasRequiredFields) {
        return BaseReportFragment.newInstance(AccessibilityVictimInfoFragment.class, viewModel, hasRequiredFields);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subject_info, container, false);
        setupView();
        return binding.getRoot();
    }

    private void setupView() {
        binding.questionAgeGroup.setText(R.string.title_question_age_group_victim);
        binding.questionEthnicity.setText(R.string.title_question_ethnicity_victim);
    }

    @Override
    protected void setupData(AccessibilityReportViewModel viewModel) {
        binding.setSubjectAgeGroup(viewModel.victimAgeGroup);
        binding.setSubjectEthnicity(viewModel.victimEthnicity);

        binding.setAgeGroups(DefaultDataManager.getAgeGroups(getContext()));
        binding.setEthnicities(DefaultDataManager.getEthnicities(getContext()));
    }

    @Override
    public boolean validateForm() {
        if(!viewModel.victimEthnicity.exists()) {
            viewModel.victimEthnicity.set(new ResponseObject(DefaultDataManager.DEFAULT_ETHNICITY));
        }

        return true;
    }

}

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
import br.com.ilhasoft.protejaBrasil.model.SubjectType;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class ViolationSubjectInfoFragment extends BaseReportFragment<ViolationReportViewModel> {

    private static final String EXTRA_TYPE = "type";

    private FragmentSubjectInfoBinding binding;
    private SubjectType subjectType;

    public static BaseReportFragment newInstance(ViolationReportViewModel viewModel, SubjectType subjectType, boolean hasRequiredFields) {
        BaseReportFragment fragment = BaseReportFragment.newInstance(ViolationSubjectInfoFragment.class, viewModel, hasRequiredFields);
        Bundle args = fragment.getArguments();
        args.putSerializable(EXTRA_TYPE, subjectType);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subject_info, container, false);
        binding.setEthnicities(DefaultDataManager.getEthnicities(getContext()));
        binding.setAgeGroups(DefaultDataManager.getAgeGroups(getContext()));
        return binding.getRoot();
    }

    @Override
    protected void setupData(ViolationReportViewModel viewModel) {
        subjectType = (SubjectType) getArguments().getSerializable(EXTRA_TYPE);
        bindAddressViewModel(viewModel);
    }

    @Override
    public boolean validateForm() {
        if(subjectType == SubjectType.Victim) {
            setDefaultValueIfNeeded(viewModel.victimEthnicity, DefaultDataManager.DEFAULT_ETHNICITY);
        } else {
            setDefaultValueIfNeeded(viewModel.offenderEthnicity, DefaultDataManager.DEFAULT_ETHNICITY);
        }
        return true;
    }

    private void bindAddressViewModel(ViolationReportViewModel viewModel) {
        if(subjectType == SubjectType.Victim) {
            binding.questionAgeGroup.setText(R.string.title_question_age_group_victim);
            binding.setSubjectAgeGroup(viewModel.victimAgeGroup);
            binding.questionEthnicity.setText(R.string.title_question_ethnicity_victim);
            binding.setSubjectEthnicity(viewModel.victimEthnicity);
        } else {
            binding.questionAgeGroup.setText(R.string.title_question_age_group_offender);
            binding.setSubjectAgeGroup(viewModel.offenderAgeGroup);
            binding.questionEthnicity.setText(R.string.title_question_ethnicity_offender);
            binding.setSubjectEthnicity(viewModel.offenderEthnicity);
        }
    }
}

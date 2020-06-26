package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentSubjectGenderBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.managers.DefaultDataManager;
import br.com.ilhasoft.protejaBrasil.model.SubjectType;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class ViolationSubjectGenderFragment extends BaseReportFragment<ViolationReportViewModel> {

    private static final String EXTRA_TYPE = "type";

    private FragmentSubjectGenderBinding binding;
    private SubjectType subjectType;

    public static BaseReportFragment newInstance(ViolationReportViewModel viewModel, SubjectType subjectType, boolean hasRequiredFields) {
        BaseReportFragment fragment = BaseReportFragment.newInstance(ViolationSubjectGenderFragment.class, viewModel, hasRequiredFields);
        Bundle args = fragment.getArguments();
        args.putSerializable(EXTRA_TYPE, subjectType);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subject_gender, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    private void setupView() {
        binding.help.setOnClickListener(onHelpClickListener);

    }

    @Override
    protected void setupData(ViolationReportViewModel viewModel) {
        subjectType = (SubjectType)getArguments().getSerializable(EXTRA_TYPE);

        bindAddressViewModel(viewModel);
        binding.setGenders(DefaultDataManager.getGenders(getContext()));
        binding.setSexOptions(DefaultDataManager.getSexOptions(getContext()));
    }

    @Override
    public boolean validateForm() {
        if(subjectType == SubjectType.Victim) {
            setDefaultValueIfNeeded(viewModel.victimSexOption, DefaultDataManager.DEFAULT_SEX_OPTION_ID);
        } else {
            setDefaultValueIfNeeded(viewModel.offenderSexOption, DefaultDataManager.DEFAULT_SEX_OPTION_ID);
        }

        return validateSpinner(binding.gendersSpinner, binding.getSubjectGender());
    }

    private void bindAddressViewModel(ViolationReportViewModel viewModel) {
        if(subjectType == SubjectType.Victim) {
            binding.questionGender.setText(R.string.title_question_gender_victim);
            binding.setSubjectGender(viewModel.victimGender);
            binding.questionSexOption.setText(R.string.title_question_sex_option_victim);
            binding.setSubjectSexOption(viewModel.victimSexOption);
        } else {
            binding.questionGender.setText(R.string.title_question_gender_offender);
            binding.setSubjectGender(viewModel.offenderGender);
            binding.questionSexOption.setText(R.string.title_question_sex_option_offender);
            binding.setSubjectSexOption(viewModel.offenderSexOption);
        }
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionGender);
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext())
                    .setTitle(R.string.title_gender_help)
                    .setMessage(R.string.message_gender_help)
                    .setNeutralButton(R.string.label_help_button, null);
            alertBuilder.show();
        }
    };
}

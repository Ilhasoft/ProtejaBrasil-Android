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
import br.com.ilhasoft.protejaBrasil.managers.AccessibilityDataManager;
import br.com.ilhasoft.protejaBrasil.managers.DefaultDataManager;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class AccessibilityVictimGenderFragment extends BaseReportFragment<AccessibilityReportViewModel> {

    private FragmentSubjectGenderBinding binding;

    public static BaseReportFragment newInstance(ViolationReportViewModel viewModel, boolean hasRequiredFields) {
        return BaseReportFragment.newInstance(AccessibilityVictimGenderFragment.class, viewModel, hasRequiredFields);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subject_gender, container, false);
        setupView();
        return binding.getRoot();
    }

    private void setupView() {
        binding.questionGender.setText(R.string.title_question_victim_gender_no_req);
        binding.questionSexOption.setText(R.string.title_question_sex_option_victim);

        binding.help.setOnClickListener(onHelpClickListener);
    }

    @Override
    protected void setupData(AccessibilityReportViewModel viewModel) {
        binding.setSubjectGender(viewModel.victimGender);
        binding.setSubjectSexOption(viewModel.victimSexOption);

        binding.setGenders(DefaultDataManager.getGenders(getContext()));
        binding.setSexOptions(AccessibilityDataManager.getSexOptions(getContext()));
    }

    @Override
    public boolean validateForm() {
        if(!viewModel.victimGender.exists()) {
            viewModel.victimGender.set(new ResponseObject(DefaultDataManager.DEFAULT_GENDER_ID));
        }

        if(!viewModel.victimSexOption.exists()) {
            viewModel.victimSexOption.set(new ResponseObject(AccessibilityDataManager.DEFAULT_SEX_OPTION_ID));
        }

        return true;
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

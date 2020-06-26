package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentOffenderInfoBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;
import br.com.ilhasoft.support.databinding.bindings.BindableField;

/**
 * Created by johncordeiro on 18/10/15.
 */
public class OffenderInfoFragment extends BaseReportFragment<ViolationReportViewModel> {

    private FragmentOffenderInfoBinding binding;
    private static final String TAG = "OffenderInfoFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offender_info, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    protected void setupData(ViolationReportViewModel viewModel) {
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setPerson(new BindableField(new ResponseObject(2, getString(R.string.title_person))));
        binding.setCompany(new BindableField(new ResponseObject(1, getString(R.string.title_company))));
        checkPersonByDefault();
    }

    private void setupViews() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionOffender);
    }

    private void checkPersonByDefault() {
        if (binding.getViewModel().offenderType.get() == null) {
            binding.getViewModel().offenderType.set(binding.getPerson().get());
        }
    }

    @Override
    public boolean validateForm() {
        return binding.typeGroup.getCheckedRadioButtonId() > 0
                && validateText(binding.offenderName, binding.getViewModel().offenderName);
    }

    public int getSelectedOffenderId() {
        return ((ResponseObject) viewModel.offenderType.get()).getId();
    }

}

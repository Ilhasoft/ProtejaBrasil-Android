package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentAccessibilityReportBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;

/**
 * Created by Dielson Sales on 02/03/16.
 */
public class AccessibilityReportFragment extends Fragment {

    private FragmentAccessibilityReportBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accessibility_report, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    private void setupView() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionBarrier);
    }

    public boolean validateForm() {
        return ! binding.barrierDescription.getText().toString().isEmpty();
    }

    public String getReportDescription() {
        return binding.barrierDescription.getText().toString();
    }
}

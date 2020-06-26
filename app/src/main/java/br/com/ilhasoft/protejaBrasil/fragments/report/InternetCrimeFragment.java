package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentInternetCrimeBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.model.InternetViolation;
import br.com.ilhasoft.protejaBrasil.model.view.InternetCrimeViewModel;
import br.com.ilhasoft.protejaBrasil.network.InternetViolationApi;
import br.com.ilhasoft.protejaBrasil.network.InternetViolationServices;
import br.com.ilhasoft.support.validation.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class InternetCrimeFragment extends BaseReportFragment<InternetCrimeViewModel> {

    private FragmentInternetCrimeBinding binding;
    private Validator validator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_internet_crime, container, false);
        validator = new Validator(binding);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InternetViolationServices services = new InternetViolationServices(getContext(), true);
        services.listViolations().enqueue(onInternetViolationsCallback);

        setupView();
    }

    @Override
    protected void setupData(InternetCrimeViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    private void setupView() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionWhatHappened);
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionWhereHappened);
    }

    public boolean validateForm() {
        fixWebsiteIfNeeded();

        return validateSpinner(binding.violationTypeSpinner, binding.getViewModel().violationType)
            & validateText(binding.violationDescription, binding.getViewModel().description)
            & (validateText(binding.website, binding.getViewModel().website) || validator.validate());
    }

    private void fixWebsiteIfNeeded() {
        String website = binding.getViewModel().website.get();
        if(!website.startsWith("http://") && !website.startsWith("https://")) {
            binding.getViewModel().website.set("http://" + website);
        }
    }

    private Callback<InternetViolationApi.ListResponse<InternetViolation>> onInternetViolationsCallback = new Callback<InternetViolationApi.ListResponse<InternetViolation>>() {
        @Override
        public void onResponse(Call<InternetViolationApi.ListResponse<InternetViolation>> call, Response<InternetViolationApi.ListResponse<InternetViolation>> response) {
            binding.setViolationTypes(response.body().getData());
        }

        @Override
        public void onFailure(Call<InternetViolationApi.ListResponse<InternetViolation>> call, Throwable t) {
            if (isAdded()) {
                Toast.makeText(getContext(), R.string.error_loading_data, Toast.LENGTH_SHORT).show();
            }
        }
    };
}

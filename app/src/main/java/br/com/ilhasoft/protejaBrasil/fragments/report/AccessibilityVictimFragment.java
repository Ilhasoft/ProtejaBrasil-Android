package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentAccessibilityVictimBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.loaders.CitiesLoader;
import br.com.ilhasoft.protejaBrasil.managers.StatesManager;
import br.com.ilhasoft.protejaBrasil.model.City;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;
import br.com.ilhasoft.support.databinding.bindings.Bindable;

/**
 * Created by john-mac on 4/16/16.
 */
public class AccessibilityVictimFragment extends BaseReportFragment<AccessibilityReportViewModel>
        implements LoaderManager.LoaderCallbacks<List<City>> {

    private static final String EXTRA_STATE = "state";
    private static final int CITIES_LOADER_ID = 100;

    private FragmentAccessibilityVictimBinding binding;

    public static BaseReportFragment newInstance(AccessibilityReportViewModel viewModel, boolean hasRequiredFields) {
        return BaseReportFragment.newInstance(AccessibilityVictimFragment.class
                , viewModel, hasRequiredFields);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accessibility_victim, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    private void setupView() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionLocationInfo);
        binding.getAddress().state.setOnBindableChangedListener(onStateBindableChangedListener);
    }

    @Override
    protected void setupData(AccessibilityReportViewModel viewModel) {
        binding.setViewModel(viewModel);
        binding.setAddress(viewModel.victimAddress);
        binding.setStates(StatesManager.getStates());

        if(!binding.getAddress().city.exists())
            binding.setCities(new ArrayList<City>());
    }

    private void loadData(State state) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_STATE, state);

        getLoaderManager().initLoader(CITIES_LOADER_ID, args, this).forceLoad();
    }

    @Override
    public
    boolean validateForm() {
        setDefaultValueIfNeeded(viewModel.victimName, "Não informado");
        return validateLocation();
    }

    private boolean validateLocation() {
        return validateSpinner(binding.statesSpinner, binding.getAddress().state)
                && validateSpinner(binding.citiesSpinner, binding.getAddress().city)
                && validateText(binding.address, binding.getAddress().address);
    }

    @Override
    public Loader<List<City>> onCreateLoader(int id, Bundle args) {
        State state = args.getParcelable(EXTRA_STATE);
        return new CitiesLoader(getContext(), state.getInitials());
    }

    @Override
    public void onLoadFinished(Loader<List<City>> loader, List<City> data) {
        getLoaderManager().destroyLoader(CITIES_LOADER_ID);
        binding.setCities(data);
        binding.getAddress().city.set(binding.getAddress().city.get());
    }

    @Override
    public void onLoaderReset(Loader<List<City>> loader) {}

    private Bindable.OnBindableChangedListener<Parcelable> onStateBindableChangedListener = new Bindable.OnBindableChangedListener<Parcelable>() {
        @Override
        public void onBindableChanged(Parcelable object) {
            if(object != null) {
                State state = (State) object;
                loadData(state);
            }
        }
    };

}

package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentViolationAddressBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.loaders.CitiesLoader;
import br.com.ilhasoft.protejaBrasil.managers.StatesManager;
import br.com.ilhasoft.protejaBrasil.model.City;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.model.SubjectType;
import br.com.ilhasoft.protejaBrasil.model.view.AddressViewModel;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;
import br.com.ilhasoft.protejaBrasil.tasks.CompleteAddressByLocationTask;
import br.com.ilhasoft.support.databinding.bindings.Bindable;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class ViolationAddressFragment extends BaseReportFragment<ViolationReportViewModel>
        implements LoaderManager.LoaderCallbacks<List<City>> {

    private static final String TAG = "ViolationAddressFrag";

    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_LOCATION = "location";

    private static final String EXTRA_STATE = "state";
    private static final int CITIES_LOADER_ID = 100;

    private FragmentViolationAddressBinding binding;
    private Location location;

    public static BaseReportFragment newInstance(ViolationReportViewModel viewModel, Location location, SubjectType subjectType, boolean hasRequiredFields) {
        BaseReportFragment fragment = BaseReportFragment.newInstance(ViolationAddressFragment.class, viewModel, hasRequiredFields);
        Bundle args = fragment.getArguments();
        args.putParcelable(EXTRA_LOCATION, location);
        args.putSerializable(EXTRA_TYPE, subjectType);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_violation_address, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    private void setupView() {
        binding.location.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.fillForm.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    protected void setupData(ViolationReportViewModel viewModel) {
        bindAddressViewModel(viewModel);
        location = getArguments().getParcelable(EXTRA_LOCATION);
        binding.setStates(StatesManager.getStates());

        if(!binding.getAddress().city.exists())
            binding.setCities(new ArrayList<City>());

        if(location == null) {
            binding.location.setVisibility(View.GONE);
            binding.setCheckedRadio(R.id.fillForm);
        }
    }

    private void loadData(State state) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_STATE, state);

        getLoaderManager().initLoader(CITIES_LOADER_ID, args, ViolationAddressFragment.this).forceLoad();
    }

    @Override
    public boolean validateForm() {
        return binding.getCheckedRadio() > 0
        && (binding.getCheckedRadio() != R.id.fillForm || validateLocation());
    }

    private boolean validateLocation() {
        return validateSpinner(binding.statesSpinner, binding.getAddress().state)
            && validateSpinner(binding.citiesSpinner, binding.getAddress().city)
            && validateText(binding.address, binding.getAddress().address);
    }

    private void bindAddressViewModel(ViolationReportViewModel viewModel) {
        SubjectType subjectType = (SubjectType)getArguments().getSerializable(EXTRA_TYPE);
        if(subjectType == SubjectType.Victim) {
            binding.setAddress(viewModel.victimAddress);
            binding.questionVictimAddress.setText(R.string.title_question_victim_location);
        } else {
            binding.setAddress(viewModel.offenderAddress);
            binding.questionVictimAddress.setText(R.string.title_question_offender_location);
        }
        binding.getAddress().state.setOnBindableChangedListener(onStateBindableChangedListener);
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionVictimAddress);
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                if(buttonView.getId() == R.id.location) {
                    findAddressByLocation();
                } else {
                    binding.setCheckedRadio(buttonView.getId());
                }
            }
        }
    };

    private void findAddressByLocation() {
        CompleteAddressByLocationTask addressByLocationTask = new CompleteAddressByLocationTask(getContext()) {
            @Override
            protected void onPostExecute(AddressViewModel address) {
                super.onPostExecute(address);
                if(address == null) {
                    binding.setCheckedRadio(R.id.fillForm);
                    Toast.makeText(getContext(), R.string.error_message_my_location, Toast.LENGTH_SHORT).show();
                } else {
                    binding.setCheckedRadio(R.id.location);
                    binding.getAddress().city.set(address.city.get());
                    binding.getAddress().state.set(address.state.get());
                    binding.getAddress().address.set(address.address.get());
                }
            }
        };
        addressByLocationTask.execute(location);
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

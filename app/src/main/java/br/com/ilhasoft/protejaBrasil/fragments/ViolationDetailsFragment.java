package br.com.ilhasoft.protejaBrasil.fragments;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.activities.ViolationDetailsActivity;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentViolationsDetailsBinding;
import br.com.ilhasoft.protejaBrasil.helpers.LocationHelper;
import br.com.ilhasoft.protejaBrasil.managers.MarkerBuilder;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.Violation;
import br.com.ilhasoft.protejaBrasil.model.holder.MarkerData;
import br.com.ilhasoft.protejaBrasil.network.ProtectionNetworkServices;
import br.com.ilhasoft.protejaBrasil.tasks.LoadMarkerDataTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johncordeiro on 10/10/15.
 */
public class ViolationDetailsFragment extends TrackedFragment implements OnMapReadyCallback {

    private static final String TAG = "ViolationDetails";
    private static final String ANALYTICS_SCREEN_NAME = "Violation type details";

    private static final String EXTRA_VIOLATION = "violation";
    public static final int DEFAULT_ZOOM = 15;

    private FragmentViolationsDetailsBinding binding;

    private GoogleMap googleMap;

    private boolean locationLoaded = false;
    private MarkerBuilder markerBuilder;
    private LocationHelper locationHelper;

    private Location location;
    private Violation violation;

    public static ViolationDetailsFragment newInstance(Violation violation) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_VIOLATION, violation);

        ViolationDetailsFragment fragment = new ViolationDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_violations_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObjects();
        setupView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof ViolationDetailsActivity) {
            ((ViolationDetailsActivity)getActivity()).setupInfo(violation.getName(), violation.getDescription());
        }
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);
    }

    private void setupObjects() {
        locationHelper = new LocationHelper(getContext());
        markerBuilder = new MarkerBuilder(getContext());
        violation = getViolationFromArgs();
        binding.setViolation(violation);
        binding.setContainsNetwork(true);
    }

    private void setupView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.setLocationEnabled(locationHelper.isLocationAvailable());
    }

    private Violation getViolationFromArgs() {
        Bundle args = getArguments();
        if(args != null && args.containsKey(EXTRA_VIOLATION)) {
            return args.getParcelable(EXTRA_VIOLATION);
        }
        return null;
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
    }

    private GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {

        @Override
        public void onMyLocationChange(Location location) {
            ViolationDetailsFragment.this.location = location;
            binding.setLocationEnabled(true);
            if(!locationLoaded) {
                locationLoaded = true;
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationToLatLng(location), DEFAULT_ZOOM));
                loadData();
            }
        }
    };

    private void loadData() {
        if(violation.getTypes() != null && !violation.getTypes().isEmpty()) {
            ProtectionNetworkServices services = new ProtectionNetworkServices(getContext());
            services.listProtectionNetworksByIdAndProximity(violation.getId()
                    , location.getLatitude(), location.getLongitude())
                    .enqueue(onProtectionNetworksLoaded);
        } else {
            binding.setContainsNetwork(false);
        }
    }

    @NonNull
    private LatLng locationToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    private Callback<ProtectionNetwork> onProtectionNetworksLoaded = new Callback<ProtectionNetwork>() {
        @Override
        public void onResponse(Call<ProtectionNetwork> call, Response<ProtectionNetwork> response) {
            if(response != null && response.body() != null) {
                loadProtectionNetworkInfo(response.body());
                binding.setContainsNetwork(true);
            } else {
                binding.setContainsNetwork(false);
            }
        }

        @Override
        public void onFailure(Call<ProtectionNetwork> call, Throwable throwable) {
            Log.e(TAG, "onFailure ", throwable);
            binding.setContainsNetwork(false);
        }
    };

    private void loadProtectionNetworkInfo(ProtectionNetwork protectionNetwork) {
        if(isAdded()) {
            LoadMarkerDataTask loadMarkerDataTask = new LoadMarkerDataTask(getActivity()) {
                @Override
                protected void onPostExecute(MarkerData markerData) {
                    super.onPostExecute(markerData);
                    Marker marker = googleMap.addMarker(markerBuilder.convertMarkerDataToOptions(markerData));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), DEFAULT_ZOOM));
                }
            };
            loadMarkerDataTask.execute(protectionNetwork);
            addNetworkDetails(protectionNetwork);
        }
    }

    private void addNetworkDetails(ProtectionNetwork protectionNetwork) {
        NetworkDetailsFragment networkDetailsFragment = NetworkDetailsFragment.newInstance(protectionNetwork, location);
        getFragmentManager().beginTransaction()
                .replace(R.id.protectionNetworkInfo, networkDetailsFragment)
                .commit();
    }

}

package br.com.ilhasoft.protejaBrasil.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.activities.StepBasedReportActivity;
import br.com.ilhasoft.protejaBrasil.activities.ViolationReportActivity;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentNetworkDetailsBinding;
import br.com.ilhasoft.protejaBrasil.helpers.CallHelper;
import br.com.ilhasoft.protejaBrasil.helpers.RouteHelper;
import br.com.ilhasoft.protejaBrasil.listener.OnCloseListener;
import br.com.ilhasoft.protejaBrasil.model.OperatingDay;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;

/**
 * Created by johncordeiro on 11/10/15.
 */
public class NetworkDetailsFragment extends Fragment {

    public static final int REQUEST_CODE_CALL_PERMISSION = 101;

    private static final String EXTRA_PROTECTION_NETWORK = "protectionNetwork";
    private static final String EXTRA_MY_LOCATION = "myLocation";

    private FragmentNetworkDetailsBinding binding;

    private OnCloseListener onCloseListener;

    private ProtectionNetwork protectionNetwork;
    private Location myLocation;

    public static NetworkDetailsFragment newInstance(ProtectionNetwork protectionNetwork, Location myLocation) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PROTECTION_NETWORK, protectionNetwork);
        args.putParcelable(EXTRA_MY_LOCATION, myLocation);

        NetworkDetailsFragment fragment = new NetworkDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_network_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupData();
        setupView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_CODE_CALL_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callProtectionPhone();
                } else {
                    Toast.makeText(getContext(), R.string.prompt_call_permission_error, Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void callProtectionPhone() {
        CallHelper callHelper = new CallHelper(NetworkDetailsFragment.this);
        callHelper.callOnlyPhone(protectionNetwork.getPhone1());
    }

    private void setupView() {
        binding.setStandalone(onCloseListener != null);
        binding.close.setOnClickListener(onCloseClickListener);
        binding.call.setOnClickListener(onCallClickListener);
        binding.route.setOnClickListener(OnRouteClickListener);
        binding.addContact.setOnClickListener(onAddContactClickListener);
        binding.report.setOnClickListener(onReportClickListener);
    }

    private void setupData() {
        myLocation = getArguments().getParcelable(EXTRA_MY_LOCATION);
        protectionNetwork = getArguments().getParcelable(EXTRA_PROTECTION_NETWORK);
        binding.setProtectionNetwork(protectionNetwork);
        binding.setView(this);
    }

    public String getDistance() {
        if(protectionNetwork.getPosition() != null && myLocation != null) {
            double distance = SphericalUtil.computeDistanceBetween(locationToLatLng(myLocation)
                    , new LatLng(protectionNetwork.getPosition().getLatitude(), protectionNetwork.getPosition().getLongitude()));
            return " - " + getDistanceDescription(distance);
        }
        return "";
    }

    @NonNull
    private String getDistanceDescription(double distance) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        if(distance > 1000) {
            return numberFormat.format(distance/1000) + " km";
        } else {
            return numberFormat.format(distance/1000) + " m";
        }
    }

    public String getAddress() {
        String fullAddress = protectionNetwork.getAddress();

        if(existsString(protectionNetwork.getNeighborhood()))
            fullAddress += " - " + protectionNetwork.getNeighborhood();

        return fullAddress;
    }

    public String getSchedule() {
        StringBuilder schedule = new StringBuilder("");

        List<OperatingDay> operatingDays = protectionNetwork.getOperatingdays();
        if(operatingDays != null && !operatingDays.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            OperatingDay operatingDay = OperatingDay.getByDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));

            if(operatingDays.contains(operatingDay)) {
                schedule.append(getString(R.string.label_open_today));
            } else {
                schedule.append(getString(R.string.label_closed_today));
            }

            if(existsString(protectionNetwork.getSchedule())) {
                schedule.append(": ");
            }
        }

        if(existsString(protectionNetwork.getSchedule())) {
            schedule.append(protectionNetwork.getSchedule());
        }

        return schedule.toString();
    }

    public String getCityInfo() {
        String cityInfo = "";
        if(existsString(protectionNetwork.getCity()))
            cityInfo += protectionNetwork.getCity() + ", ";

        if(protectionNetwork.getState() != null && existsString(protectionNetwork.getState().getInitials()))
            cityInfo += protectionNetwork.getState().getInitials();

        if(existsString(protectionNetwork.getZipcode()))
            cityInfo += " - " + protectionNetwork.getZipcode();

        return cityInfo;
    }

    @Nullable
    private LatLng locationToLatLng(Location location) {
        return location == null ? null : new LatLng(location.getLatitude(), location.getLongitude());
    }

    private boolean existsString(String value) {
        return value != null && !value.isEmpty();
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    private View.OnClickListener onCloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onCloseListener != null)
                onCloseListener.onClose();
        }
    };

    private View.OnClickListener onCallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL_PERMISSION);
            } else {
                callProtectionPhone();
            }
        }
    };

    private View.OnClickListener OnRouteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(myLocation != null) {
                RouteHelper routeHelper = new RouteHelper(NetworkDetailsFragment.this);
                routeHelper.traceRoute(myLocation, protectionNetwork.getPosition());
            } else {
                Toast.makeText(getActivity(), R.string.error_location_disabled, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener onAddContactClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, protectionNetwork.getPhone1());
            intent.putExtra(ContactsContract.Intents.Insert.NAME, protectionNetwork.getName());
            getActivity().startActivity(intent);
        }
    };

    private View.OnClickListener onReportClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent reportViolationIntent = new Intent(getActivity(), ViolationReportActivity.class);
            reportViolationIntent.putExtra(StepBasedReportActivity.EXTRA_LOCATION, myLocation);
            startActivity(reportViolationIntent);
        }
    };
}

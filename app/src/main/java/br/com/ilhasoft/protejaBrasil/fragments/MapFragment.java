package br.com.ilhasoft.protejaBrasil.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.activities.MainActivity;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentMapBinding;
import br.com.ilhasoft.protejaBrasil.helpers.FloatingAnimations;
import br.com.ilhasoft.protejaBrasil.helpers.ProtectionClusterItem;
import br.com.ilhasoft.protejaBrasil.helpers.ProtectionNetworkRenderer;
import br.com.ilhasoft.protejaBrasil.listener.OnCloseListener;
import br.com.ilhasoft.protejaBrasil.listener.OnReportSelectedListener;
import br.com.ilhasoft.protejaBrasil.listener.TaskListener;
import br.com.ilhasoft.protejaBrasil.loaders.ProtectionNetworksLoader;
import br.com.ilhasoft.protejaBrasil.managers.ReportManager;
import br.com.ilhasoft.protejaBrasil.managers.StatesManager;
import br.com.ilhasoft.protejaBrasil.managers.UserDataManager;
import br.com.ilhasoft.protejaBrasil.model.GoogleMapsZoom;
import br.com.ilhasoft.protejaBrasil.model.NetworkType;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.holder.MarkerData;
import br.com.ilhasoft.protejaBrasil.tasks.AddressByLocationTask;
import br.com.ilhasoft.protejaBrasil.tasks.LoadNetworkTypeData;
import br.com.ilhasoft.protejaBrasil.tasks.ProtectionNetworkLoadTask;
import br.com.ilhasoft.support.utils.KeyboardHandler;

/**
 * Created by johncordeiro on 08/10/15.
 */
public class MapFragment extends TrackedFragment
        implements OnMapReadyCallback, OnCloseListener, TaskListener<ProtectionNetwork>,
        LoaderManager.LoaderCallbacks<List<ProtectionNetwork>> {

    private static final String TAG = "MapFragment";
    private static final String ANALYTICS_SCREEN_NAME = "Map";

    private static final int DURATION_ANIMATION_MS = 300;
    private static final String BACK_STACK_NETWORK_DETAILS = "networkDetails";

    public static final int DEFAULT_ZOOM = 11;
    public static final int CLOSER_ZOOM = 15;

    private static final String EXTRA_LOADER_STATE = "state";
    private static final String EXTRA_FROM_CACHE = "fromCache";

    private static final int LOADER_ID = 100;
    public static final int REQUEST_CODE_MY_LOCATION = 100;

    private FragmentMapBinding binding;

    private boolean shouldOpenFloatingActionMenu = false;
    private GoogleMap googleMap;
    private Location myLocation;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private List<State> states;
    private Location cacheLocation = new Location("Marker");

    private KeyboardHandler keyboardHandler;
    private UserDataManager userDataManager;

    private OnReportSelectedListener onReportSelectedListener;

    private boolean firstSelection = true;
    private boolean stillLoadingCorrectLocation = false;

    private Set<Theme> themes;
    private State state;

    private ClusterManager<ProtectionClusterItem> clusterManager;
    private ProtectionNetworkRenderer clusterRenderer;

    // Fragment methods ----------------------------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObjects();
        setupView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnReportSelectedListener) {
            onReportSelectedListener = (OnReportSelectedListener) context;
        }

        if (context instanceof MainActivity) {
            themes = ((MainActivity) context).getSelectedThemes();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), R.string.protect_and_report, Toast.LENGTH_LONG).show();
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);

        if (shouldOpenFloatingActionMenu) {
            binding.menuReport.open(true);
            shouldOpenFloatingActionMenu = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        firstSelection = false;
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onClose() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.details);
        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    @NonNull
    private Bundle buildArgs(boolean fromCache) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_LOADER_STATE, state);
        args.putBoolean(EXTRA_FROM_CACHE, fromCache);
        return args;
    }

    // Getters & setters ---------------------------------------------------------------------------

    /**
     * Sets whether the floating action menu should appear opened when the map fragment appears on
     * the sreen.
     *
     * @param shouldOpenFloatingActionMenu
     */
    public void setShouldOpenFloatingActionMenu(boolean shouldOpenFloatingActionMenu) {
        this.shouldOpenFloatingActionMenu = shouldOpenFloatingActionMenu;
    }

    // Constructor ---------------------------------------------------------------------------------

    private void setupObjects() {
        keyboardHandler = new KeyboardHandler();
        userDataManager = new UserDataManager(getContext());
    }

    private void setupView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingAnimations floatingAnimations = new FloatingAnimations();
        floatingAnimations.setSwitchImageAnimation(binding.menuReport, R.drawable.ic_report_small, R.drawable.ic_close_white_24dp);

        setupButtons();
        setupStatesSpinner();
        setupSearchField();
    }

    private void setupButtons() {
        binding.reportCall.setOnClickListener(onReportCallClickListener);
        binding.womenReportCall.setOnClickListener(onWomenReportCallClickListener);
        binding.reportAccessibility.setOnClickListener(onReportAccessibilityClickListener);
        binding.reportInternet.setOnClickListener(onReportInternetCrimeClickListener);
        binding.reportViolation.setOnClickListener(onReportViolationClickListener);
        binding.menuReport.setClosedOnTouchOutside(true);
    }

    private void setupSearchField() {
        if (getActivity() instanceof MainActivity) {
            EditText searchField = ((MainActivity) getActivity()).getSearchField();
            searchField.setHint(R.string.hint_search_networks);
            searchField.setOnEditorActionListener(onSearchActionListener);
        }
    }

    private void setupStatesSpinner() {
        this.states = StatesManager.getStates();

        ArrayAdapter<State> adapter = new ArrayAdapter<>(getActivity()
                , R.layout.view_simple_spinner_item, android.R.id.text1, states);
        adapter.setDropDownViewResource(R.layout.view_simple_spinner_dropdown_item);
        binding.location.setAdapter(adapter);
        binding.location.setOnItemSelectedListener(onStateSelectedListener);
    }

    @SuppressWarnings({"MissingPermission"})
    private void setupListeners() {
        this.googleMap.setMyLocationEnabled(true);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(locationConnectionCallbacksListener)
                .addOnConnectionFailedListener(locationConnectionFailedListener)
                .build();
        googleApiClient.connect(); // check locationConnectionCallbackListener for possible result
    }

    // Map methods ---------------------------------------------------------------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);

        this.clusterManager = new ClusterManager<>(getContext(), this.googleMap);
        this.clusterRenderer = new ProtectionNetworkRenderer(getContext(), googleMap, clusterManager);
        this.clusterManager.setRenderer(clusterRenderer);
        this.clusterManager.setOnClusterItemClickListener(onClusterItemClickListener);

        this.googleMap.setOnCameraIdleListener(clusterManager);
        this.googleMap.setOnMarkerClickListener(clusterManager);

        requestMapLocation();
    }

    private void requestMapLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            setupListeners();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_MY_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_MY_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupListeners();
                } else {
                    loadCurrentState();
                }
        }
    }

    public void moveCameraToLocation(LatLng location, int zoom) {
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom), DURATION_ANIMATION_MS, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Log.d(TAG, "Camera moved");
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private GoogleApiClient.ConnectionCallbacks locationConnectionCallbacksListener = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        @SuppressWarnings({"MissingPermission"})
        public void onConnected(@Nullable Bundle bundle) {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(5000); // updates location every 5 seconds
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.d(TAG, "GoogleApiClient connection was suspended");
        }
    };

    private GoogleApiClient.OnConnectionFailedListener locationConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        @SuppressWarnings({"MissingPermission"})
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            Log.e(TAG, "GoogleApiClient connection failed. Result: " + connectionResult.getErrorMessage());
        }
    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (myLocation == null) {
                Log.d(TAG, "Loading first location");
                stillLoadingCorrectLocation = true;
            }
            myLocation = location;
            loadCurrentState();
        }
    };

    // List methods --------------------------------------------------------------------------------

    private void loadCurrentState() {
        if (TextUtils.isEmpty(userDataManager.getState())) {
            loadAddressByLocation();
        } else {
            int statePosition = states.indexOf(new State(userDataManager.getState()));
            selectState(statePosition);
        }
    }

    /**
     * Finds the current State depending on the user location and selects the corresponding State in
     * the States' list.
     * <p>
     * If permission to access location hasn't been granted, it returns a default State
     */
    private void loadAddressByLocation() {
        AddressByLocationTask locationTask = new AddressByLocationTask(getContext()) {
            @Override
            protected void onPostExecute(Address address) {
                super.onPostExecute(address);

                int statePosition = -1;
                State state = getStatePositionByAddress(address);
                if (state != null) {
                    userDataManager.setState(state.getInitials());
                    statePosition = states.indexOf(state);
                }
                selectState(statePosition);
            }
        };
        locationTask.execute(myLocation);
    }

    /**
     * Returns the State's position in the list based on the address passed.
     */
    private State getStatePositionByAddress(Address address) {
        if (address != null) {
            for (int position = 0; position < states.size(); position++) {
                State state = states.get(position);
                if (address.getAdminArea().contains(state.getTitle())) {
                    return state;
                }
            }
        }
        return null;
    }

    /**
     * Changes the selected item in the list based on the position passed.
     */
    private void selectState(int statePosition) {
        statePosition = statePosition >= 0 ? statePosition : StatesManager.DEFAULT_STATE_POSITION;
        binding.location.setSelection(statePosition);
    }

    private AdapterView.OnItemSelectedListener onStateSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // First selection is invalid, the list was just setup
            if (!firstSelection) {
                state = (State) parent.getItemAtPosition(position);
                userDataManager.setState(state.getInitials());
                loadProtectionNetworks(true);
                if (stillLoadingCorrectLocation) {
                    try {
                        // zoom to current user location
                        moveCameraToLocation(new LatLng(MapFragment.this.myLocation.getLatitude(), MapFragment.this.myLocation.getLongitude()), CLOSER_ZOOM);
                    } catch (NullPointerException e) {
                        // nothing
                    }
                } else {
                    // zoom to state location
                    moveCameraToLocation(new LatLng(state.getLatitude(), state.getLongitude()), DEFAULT_ZOOM);
                }

            }
            firstSelection = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    // Markers methods -----------------------------------------------------------------------------

    private void loadProtectionNetworks(boolean fromCache) {
        if (themes != null && state != null) {
            Loader loader = getLoaderManager().getLoader(LOADER_ID);
            if (loader != null) {
                getLoaderManager().restartLoader(LOADER_ID, buildArgs(fromCache), this).forceLoad();
            } else {
                getLoaderManager().initLoader(LOADER_ID, buildArgs(fromCache), this).forceLoad();
            }
        }
    }

    @Override
    public void onLoadFinished(Loader<List<ProtectionNetwork>> loader, List<ProtectionNetwork> data) {
        if (((ProtectionNetworksLoader) loader).isFromCache()) {
            loadProtectionNetworks(false);
        } else {
            binding.loading.setVisibility(View.GONE);
        }
        if (data != null) {
            clusterManager.clearItems();

            final List<ProtectionNetwork> filteredNetworks = filterProtectionNetworks(data);
            LoadNetworkTypeData loadNetworkDataType = new LoadNetworkTypeData(getContext()) {
                @Override
                protected void onPostExecute(Map<NetworkType, MarkerData> networkTypeMarkerDataMap) {
                    clusterRenderer.setNetworkTypeDataMap(networkTypeMarkerDataMap);
                    addMarkersForNetworks(filteredNetworks);
                }
            };
            loadNetworkDataType.execute(filteredNetworks);
        }
    }

    private List<ProtectionNetwork> filterProtectionNetworks(List<ProtectionNetwork> loadedNetworks) {
        ListIterator<ProtectionNetwork> iterator = loadedNetworks.listIterator();
        while (iterator.hasNext()) {
            List<Theme> networkThemes = iterator.next().getThemes();
            // Deletes the item if it contains none of the visible themes
            if (Collections.disjoint(themes, networkThemes)) {
                iterator.remove();
            }
        }
        return loadedNetworks;
    }

    private void addMarkersForNetworks(List<ProtectionNetwork> filteredNetworks) {
        List<ProtectionClusterItem> protectionClusterItems = new ArrayList<>();

        for (ProtectionNetwork protectionNetwork : filteredNetworks) {
            protectionClusterItems.add(new ProtectionClusterItem(protectionNetwork));
        }

        clusterManager.addItems(protectionClusterItems);
        clusterManager.cluster();

        onAllNetworksLoaded(protectionClusterItems, stillLoadingCorrectLocation);
        if (stillLoadingCorrectLocation) {
            stillLoadingCorrectLocation = false;
        }
    }

    private void addMarkerForNetwork(final ProtectionNetwork protectionNetwork, final boolean goToMarker) {
        if (getActivity() == null || protectionNetwork.getPosition() == null) return;

        ProtectionClusterItem item = new ProtectionClusterItem(protectionNetwork);
        clusterManager.addItem(item);

        if (goToMarker) {
            moveCameraToLocation(item.getPosition(), CLOSER_ZOOM);
            openNetworkDetails(protectionNetwork);
        }
    }

    /**
     * This function is called when all networks have been loaded into the Map.
     */
    private void onAllNetworksLoaded(List<ProtectionClusterItem> protectionNetworks, boolean shouldZoomToCurrentLocation) {
        if (shouldZoomToCurrentLocation) {
            Double minDistance = null;
            for (ProtectionClusterItem protectionNetworkCluster : protectionNetworks) {
                cacheLocation.setLatitude(protectionNetworkCluster.getPosition().latitude);
                cacheLocation.setLongitude(protectionNetworkCluster.getPosition().longitude);
                double distance = myLocation.distanceTo(cacheLocation);
                if (minDistance == null || distance < minDistance) {
                    minDistance = distance;
                }
            }
            int zoomLevel;
            try {
                zoomLevel = GoogleMapsZoom.calculateZoomLevelForDistance(minDistance, getView().getWidth(), CLOSER_ZOOM);
            } catch (NullPointerException e) {
                zoomLevel = CLOSER_ZOOM;
            }
            moveCameraToLocation(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), zoomLevel);
        }
    }

    private void openNetworkDetails(ProtectionNetwork protectionNetwork) {
        getFragmentManager().popBackStack(BACK_STACK_NETWORK_DETAILS, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        NetworkDetailsFragment networkDetailsFragment = NetworkDetailsFragment.newInstance(protectionNetwork, myLocation);
        networkDetailsFragment.setOnCloseListener(this);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom
                        , R.anim.slide_in_top, R.anim.slide_out_bottom)
                .replace(R.id.details, networkDetailsFragment)
                .addToBackStack(BACK_STACK_NETWORK_DETAILS)
                .commit();
    }

    // Other methods -------------------------------------------------------------------------------


    public void onThemesSelected(Set<Theme> themes) {
        this.themes = themes;
        loadProtectionNetworks(true);
    }

    // Listeners -----------------------------------------------------------------------------------

    private ClusterManager.OnClusterItemClickListener<ProtectionClusterItem> onClusterItemClickListener = new ClusterManager.OnClusterItemClickListener<ProtectionClusterItem>() {
        @Override
        public boolean onClusterItemClick(final ProtectionClusterItem protectionClusterItem) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.menuReport.close(true);

                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(CLOSER_ZOOM), DURATION_ANIMATION_MS, null);
                    openNetworkDetails(protectionClusterItem.getProtectionNetwork());
                }
            }, 500);
            return false;
        }
    };

    private View.OnClickListener onWomenReportCallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onReportSelectedListener != null) {
                onReportSelectedListener.onCallToDisk180();
            }
            ReportManager.reportToDisk180(MapFragment.this);
            binding.menuReport.close(false);
        }
    };

    private View.OnClickListener onReportCallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onReportSelectedListener != null) {
                onReportSelectedListener.onCallToDisk100();
            }
            ReportManager.reportToDisk100(MapFragment.this);
            binding.menuReport.close(false);
        }
    };

    private View.OnClickListener onReportAccessibilityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onReportSelectedListener != null) {
                onReportSelectedListener.onReportAccessibility();
            }
            binding.menuReport.close(false);
        }
    };

    private View.OnClickListener onReportInternetCrimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onReportSelectedListener != null)
                onReportSelectedListener.onReportInternetCrime(myLocation);
            binding.menuReport.close(false);
        }
    };

    private View.OnClickListener onReportViolationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onReportSelectedListener != null)
                onReportSelectedListener.onReportViolationSelected(myLocation);
            binding.menuReport.close(false);
        }
    };

    private TextView.OnEditorActionListener onSearchActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyboardHandler.changeKeyboardVisibility(getActivity(), false);
                new ProtectionNetworkLoadTask(getContext(), MapFragment.this, myLocation).execute(view.getText().toString());
                return true;
            }
            return false;
        }
    };

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener = new GoogleMap.OnMyLocationButtonClickListener() {
        @Override
        public boolean onMyLocationButtonClick() {
            loadAddressByLocation();
            return false;
        }
    };

    @Override
    public Loader<List<ProtectionNetwork>> onCreateLoader(int id, Bundle args) {
        boolean fromCache = args.getBoolean(EXTRA_FROM_CACHE, false);
        binding.loading.setVisibility(View.VISIBLE);
        binding.loadingMessage.setText(fromCache ? R.string.loading_protection_networks : R.string.refreshing_data);

        return new ProtectionNetworksLoader(getActivity()
                , (State) args.getParcelable(EXTRA_LOADER_STATE)
                , fromCache);
    }

    @Override
    public void onLoaderReset(Loader<List<ProtectionNetwork>> loader) {
    }

    @Override
    public void onTaskFinished(List<ProtectionNetwork> items) {
        if (items != null && !items.isEmpty()) {
            displayProtectionNetworks(items);
        } else {
            Toast.makeText(getContext(), R.string.error_message_networks_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void displayProtectionNetworks(final List<ProtectionNetwork> items) {
        ArrayAdapter<ProtectionNetwork> adapter = new ArrayAdapter<>(getContext()
                , R.layout.item_search_network, R.id.name, items);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_networks_found)
                .setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ProtectionNetwork protectionNetwork = items.get(which);
                        addMarkerForNetwork(protectionNetwork, true);
                    }
                }).create();
        alertDialog.show();
    }

    public void openFloatingActionMenu() {
        getFragmentManager().popBackStack(BACK_STACK_NETWORK_DETAILS, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        binding.menuReport.open(true);
        shouldOpenFloatingActionMenu = false;
    }
}

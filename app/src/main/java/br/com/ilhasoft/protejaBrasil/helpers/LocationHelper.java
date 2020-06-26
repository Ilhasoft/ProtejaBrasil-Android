package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by johncordeiro on 22/10/15.
 */
public class LocationHelper {

    private static final String TAG = "LocationHelper";

    private Context context;

    public LocationHelper(Context context) {
        this.context = context;
    }

    public boolean isLocationAvailable() {
        try {
            if (context != null) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager != null) {
                    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                            && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                }
            }
        } catch(Exception exception) {
            Log.e(TAG, "isLocationAvailable error!", exception);
        }
        return false;
    }

}

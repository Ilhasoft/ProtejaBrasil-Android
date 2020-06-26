package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Locale;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class AddressByLocationTask extends AsyncTask<Location, Void, Address> {

    private static final String TAG = "SearchAddress";
    private static final Locale BRAZILIAN_LOCALE = new Locale("pt_BR");

    private Context context;

    public AddressByLocationTask(Context context) {
        this.context = context;
    }

    @Override
    protected Address doInBackground(Location... params) {
        try {
            Location location = params[0];
            Geocoder geocoder = new Geocoder(context, BRAZILIAN_LOCALE);
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude()
                    , location.getLongitude(), 1);

            if(!addresses.isEmpty())
                return addresses.get(0);
        } catch(Exception exception) {
            Log.e(TAG, "onMapReady ", exception);
        }
        return null;
    }

}

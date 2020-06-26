package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.managers.StatesManager;
import br.com.ilhasoft.protejaBrasil.model.City;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.model.view.AddressViewModel;

/**
 * Created by john-mac on 1/24/16.
 */
public class CompleteAddressByLocationTask extends ProgressTask<Location, Void, AddressViewModel> {

    private static final String TAG = "CompleteAddressBy";

    public CompleteAddressByLocationTask(Context context) {
        super(context, R.string.message_wait_a_moment);
    }

    @Override
    protected AddressViewModel doInBackground(Location... params) {
        try {
            AddressByLocationTask addressByLocationTask = new AddressByLocationTask(getContext());
            Address address = addressByLocationTask.doInBackground(params);

            if (address != null) {
                AddressViewModel addressViewModel = new AddressViewModel();

                City city = new City(address.getLocality());
                addressViewModel.city.set(city);

                State state = getStateByAddress(address);
                addressViewModel.state.set(state);
                addressViewModel.address.set(getAddressLines(address).toString());

                return addressViewModel;
            }
        } catch (Exception exception) {
            Log.e(TAG, "doInBackground: ", exception);
        }
        return null;
    }

    @NonNull
    private StringBuffer getAddressLines(Address address) {
        StringBuffer addressLines = new StringBuffer();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressLines.append(address.getAddressLine(i));
            if(i < address.getMaxAddressLineIndex()) {
                addressLines.append(", ");
            }
        }
        return addressLines;
    }

    @Nullable
    private State getStateByAddress(Address address) {
        for (State state : StatesManager.getStates()) {
            if(state.getTitle().equalsIgnoreCase(address.getAdminArea())) {
                return state;
            }
        }
        return null;
    }
}

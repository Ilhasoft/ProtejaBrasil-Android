package br.com.ilhasoft.protejaBrasil.loaders;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.managers.CitiesManager;
import br.com.ilhasoft.protejaBrasil.model.City;

/**
 * Created by john-mac on 1/11/16.
 */
public class CitiesLoader extends AsyncTaskLoader<List<City>> {

    private static final String TAG = "CitiesLoader";

    private final String state;

    public CitiesLoader(Context context, String state) {
        super(context);
        this.state = state;
    }

    @Override
    public List<City> loadInBackground() {
        try {
            return CitiesManager.getCitiesByState(getContext(), state);
        } catch (Exception exception) {
            Log.e(TAG, "doInBackground: ", exception);
        }
        return new ArrayList<>();
    }

}

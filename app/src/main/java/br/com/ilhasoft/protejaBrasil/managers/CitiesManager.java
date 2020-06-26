package br.com.ilhasoft.protejaBrasil.managers;

import android.content.Context;

import androidx.annotation.WorkerThread;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.helpers.IOUtils;
import br.com.ilhasoft.protejaBrasil.model.City;

/**
 * Created by john-mac on 1/24/16.
 */
public class CitiesManager {

    public static final String ASSET_CITIES_JSON = "cities.json";

    @WorkerThread
    public static List<City> getCitiesByState(Context context, String state) throws Exception {
        List<City> cities = new ArrayList<>();

        String json = IOUtils.loadFileFromAssets(context, ASSET_CITIES_JSON);
        for (City city : getDataList(json)) {
            if (city.getState().equalsIgnoreCase(state)) {
                cities.add(city);
            }
        }
        Collections.sort(cities);
        return cities;
    }

    @WorkerThread
    public static List<City> getCities(Context context) throws Exception {
        List<City> cities = new ArrayList<>();

        String json = IOUtils.loadFileFromAssets(context, ASSET_CITIES_JSON);
        for (City city : getDataList(json)) {
            cities.add(city);
        }
        return cities;
    }

    private static List<City> getDataList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<City>>(){}.getType());
    }

}

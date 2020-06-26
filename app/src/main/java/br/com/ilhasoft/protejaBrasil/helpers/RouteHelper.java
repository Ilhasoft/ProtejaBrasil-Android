package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import br.com.ilhasoft.protejaBrasil.model.Position;

/**
 * Created by johncordeiro on 11/10/15.
 */
public class RouteHelper {

    private Fragment fragment;

    public RouteHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public void traceRoute(Location myLocation, Position toLocation) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
        Uri.parse("http://maps.google.com/maps?saddr=" + myLocation.getLatitude()
                + "," + myLocation.getLongitude() + "&daddr=" + toLocation.getLatitude() + "," + toLocation.getLongitude()));
        fragment.startActivity(intent);
    }

}

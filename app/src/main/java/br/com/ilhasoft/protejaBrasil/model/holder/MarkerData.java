package br.com.ilhasoft.protejaBrasil.model.holder;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by johncordeiro on 11/10/15.
 */
public class MarkerData {

    protected BitmapDescriptor bitmapDescriptor;
    protected Bitmap markerIcon;
    protected LatLng position;

    public MarkerData(BitmapDescriptor bitmapDescriptor, Bitmap markerIcon, LatLng position) {
        this.bitmapDescriptor = bitmapDescriptor;
        this.markerIcon = markerIcon;
        this.position = position;
    }

    public MarkerData(BitmapDescriptor bitmapDescriptor, Bitmap markerIcon) {
        this.bitmapDescriptor = bitmapDescriptor;
        this.markerIcon = markerIcon;
    }

    public BitmapDescriptor getBitmapDescriptor() {
        return bitmapDescriptor;
    }

    public Bitmap getMarkerIcon() {
        return markerIcon;
    }

    public LatLng getPosition() {
        return position;
    }
}

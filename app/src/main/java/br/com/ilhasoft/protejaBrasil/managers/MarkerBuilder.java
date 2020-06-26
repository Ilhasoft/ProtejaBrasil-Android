package br.com.ilhasoft.protejaBrasil.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ViewMarkerBinding;
import br.com.ilhasoft.protejaBrasil.model.NetworkType;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.holder.MarkerData;

/**
 * Created by johncordeiro on 11/10/15.
 */
public class MarkerBuilder {

    private Context context;
    private ViewMarkerBinding binding;

    @MainThread
    public MarkerBuilder(Context context) {
        this.context = context;
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_marker, null, false);
    }

    @NonNull
    public MarkerOptions convertMarkerDataToOptions(MarkerData markerData) {
        return new MarkerOptions().
                icon(markerData.getBitmapDescriptor()).
                position(markerData.getPosition());
    }

    @NonNull
    @WorkerThread
    public MarkerData buildMarkerDataForNetworkType(NetworkType networkType) throws IOException {
        Bitmap markerIcon = generateIcon(binding, networkType);
        BitmapDescriptor markerIconDescriptor = BitmapDescriptorFactory.fromBitmap(markerIcon);
        return new MarkerData(markerIconDescriptor, markerIcon);
    }

    @NonNull
    @WorkerThread
    public MarkerData buildMarkerDataForNetwork(ProtectionNetwork protectionNetwork) throws IOException {
        LatLng position = getLatLngFromNetwork(protectionNetwork);
        Bitmap markerIcon = generateIcon(binding, protectionNetwork.getType());
        BitmapDescriptor markerIconDescriptor = BitmapDescriptorFactory.fromBitmap(markerIcon);
        return new MarkerData(markerIconDescriptor, markerIcon, position);
    }

    private PorterDuffColorFilter getColorFilterFromNetwork(NetworkType type) {
        return type.getColor() != null && !type.getColor().isEmpty() ? new PorterDuffColorFilter(
                Color.parseColor(type.getColor()), PorterDuff.Mode.SRC_ATOP) : null;
    }

    @NonNull
    private Bitmap generateIcon(ViewMarkerBinding binding, NetworkType networkType) throws IOException {
        IconGenerator iconFactory = new IconGenerator(context);
        iconFactory.setBackground(null);
        iconFactory.setContentView(binding.getRoot());

        Bitmap bitmap = loadBitmapFromNetwork(networkType);
        if(bitmap != null)
            binding.icon.setImageBitmap(bitmap);

        ColorFilter colorFilter = getColorFilterFromNetwork(networkType);
        if(colorFilter != null)
            binding.marker.setColorFilter(colorFilter);

        Bitmap icon = iconFactory.makeIcon();

        ViewGroup parent = (ViewGroup) binding.getRoot().getParent();
        parent.removeAllViews();

        return icon;
    }
    @Nullable
    private Bitmap loadBitmapFromNetwork(NetworkType networkType) throws IOException {
        if(networkType != null && networkType.getIcon() != null) {
            return Picasso.with(context).load(networkType.getIcon()).get();
        }
        return null;
    }

    @NonNull
    private LatLng getLatLngFromNetwork(ProtectionNetwork protectionNetwork) {
        return new LatLng(protectionNetwork.getPosition().getLatitude()
                , protectionNetwork.getPosition().getLongitude());
    }

}

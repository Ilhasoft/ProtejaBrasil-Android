package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.Map;

import br.com.ilhasoft.protejaBrasil.model.NetworkType;
import br.com.ilhasoft.protejaBrasil.model.holder.MarkerData;

/**
 * Created by john-mac on 8/3/16.
 */
public class ProtectionNetworkRenderer extends DefaultClusterRenderer<ProtectionClusterItem> {

    private static final String TAG = "ProtectionNetworkRend";

    private Map<NetworkType, MarkerData> networkTypeDataMap;

    public ProtectionNetworkRenderer(Context context, GoogleMap map, ClusterManager<ProtectionClusterItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(ProtectionClusterItem item, MarkerOptions markerOptions) {
        try {
            MarkerData markerData = networkTypeDataMap.get(item.getProtectionNetwork().getType());
            markerOptions.icon(markerData.getBitmapDescriptor());
        } catch(Exception exception) {
            Log.e(TAG, "onBeforeClusterItemRendered: ", exception);
        }
    }

    public ProtectionNetworkRenderer setNetworkTypeDataMap(Map<NetworkType, MarkerData> networkTypeDataMap) {
        this.networkTypeDataMap = networkTypeDataMap;
        return this;
    }
}

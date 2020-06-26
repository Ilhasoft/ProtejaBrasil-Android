package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ilhasoft.protejaBrasil.managers.MarkerBuilder;
import br.com.ilhasoft.protejaBrasil.model.NetworkType;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.holder.MarkerData;

/**
 * Created by john-mac on 8/3/16.
 */
public class LoadNetworkTypeData extends AsyncTask<List<ProtectionNetwork>, Void, Map<NetworkType, MarkerData>> {

    private static final String TAG = "LoadNetworkTypeData";

    private Context context;
    private MarkerBuilder markerBuilder;

    public LoadNetworkTypeData(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        markerBuilder = new MarkerBuilder(context);
    }

    @Override
    protected Map<NetworkType, MarkerData> doInBackground(List<ProtectionNetwork>... params) {
        Map<NetworkType, MarkerData> networkTypeMarker = new HashMap<>();
        try {
            List<ProtectionNetwork> protectionNetworks = params[0];

            for (ProtectionNetwork protectionNetwork : protectionNetworks) {
                NetworkType networkType = protectionNetwork.getType();
                if (!networkTypeMarker.containsKey(networkType)) {
                    networkTypeMarker.put(networkType, markerBuilder.buildMarkerDataForNetworkType(networkType));
                }
            }
        } catch(Exception exception) {
            Log.e(TAG, "doInBackground: ", exception);
        }
        return networkTypeMarker;
    }
}

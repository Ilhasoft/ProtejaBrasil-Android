package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.com.ilhasoft.protejaBrasil.managers.MarkerBuilder;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.holder.MarkerData;

/**
 * Created by johncordeiro on 10/10/15.
 */
public class LoadMarkerDataTask extends AsyncTask<ProtectionNetwork, Void, MarkerData> {

    private static final String TAG = "LoadNetworksMap";

    private final Context context;

    private MarkerBuilder markerBuilder;

    public LoadMarkerDataTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        markerBuilder = new MarkerBuilder(context);
    }

    @Override
    protected MarkerData doInBackground(ProtectionNetwork... params) {
        try {
            ProtectionNetwork protectionNetwork = params[0];
            return markerBuilder.buildMarkerDataForNetwork(protectionNetwork);
        } catch(Exception exception) {
            Log.e(TAG, "doInBackground ", exception);
        }
        return null;
    }

}

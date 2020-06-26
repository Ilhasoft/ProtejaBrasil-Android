package br.com.ilhasoft.protejaBrasil.loaders;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.network.ProtectionNetworkServices;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class ProtectionNetworksLoader extends AsyncTaskLoader<List<ProtectionNetwork>> {

    private static final String TAG = "ProtectionNetworks";

    private State state;
    private boolean fromCache;

    public ProtectionNetworksLoader(Context context, State state, boolean fromCache) {
        super(context);
        this.state = state;
        this.fromCache = fromCache;
    }

    @Override
    public List<ProtectionNetwork> loadInBackground() {
        try {
            ProtectionNetworkServices services = new ProtectionNetworkServices(getContext(), fromCache);
            return services.listProtectionNetworksByState(state).execute().body();
        } catch (Exception exception) {
            Log.e(TAG, "loadInBackground ", exception);
        }
        return new ArrayList<>();
    }

    public boolean isFromCache() {
        return fromCache;
    }
}

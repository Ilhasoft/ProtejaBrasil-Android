package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.listener.TaskListener;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.network.ProtectionNetworkServices;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class ProtectionNetworkLoadTask extends ProgressTask<String, Void, List<ProtectionNetwork>> {

    private static final String TAG = "ProtectionNetwork";

    private TaskListener<ProtectionNetwork> taskListener;
    private Location myLocation;

    public ProtectionNetworkLoadTask(Context context, TaskListener<ProtectionNetwork> taskListener, Location myLocation) {
        super(context, R.string.load_message_network);
        this.taskListener = taskListener;
        this.myLocation = myLocation;
    }

    @Override
    protected List<ProtectionNetwork> doInBackground(String... params) {
        try {
            ProtectionNetworkServices services = new ProtectionNetworkServices(getContext());
            String name = params[0];

            if(myLocation != null && myLocation.getLatitude() != 0 && myLocation.getLongitude() != 0) {
                return services.listNearProtectionNetworksByName(name, myLocation.getLatitude(), myLocation.getLongitude())
                        .execute().body();
            } else {
                return services.listProtectionNetworksByName(name).execute().body();
            }
        } catch(Exception exception) {
            Log.e(TAG, "doInBackground ", exception);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<ProtectionNetwork> protectionNetworks) {
        super.onPostExecute(protectionNetworks);
        if(taskListener != null)
            taskListener.onTaskFinished(protectionNetworks);
    }
}

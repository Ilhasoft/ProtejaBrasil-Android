package br.com.ilhasoft.protejaBrasil.fragments;

import androidx.fragment.app.Fragment;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import br.com.ilhasoft.protejaBrasil.Application;

/**
 * Created by dielsonsales on 09/03/16.
 */
public class TrackedFragment extends Fragment {
    protected void sendScreenViewReport(String analyticsScreenName) {
        final Tracker tracker = ((Application) getActivity().getApplication()).getTracker();
        if (tracker != null) {
            tracker.setScreenName(analyticsScreenName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
}

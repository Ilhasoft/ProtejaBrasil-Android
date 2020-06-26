package br.com.ilhasoft.protejaBrasil;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.analytics.Tracker;

/**
 * Created by dielsonsales on 09/03/16.
 */
public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AnalyticsTrackers.initialize(this);
    }

    public synchronized Tracker getTracker() {
        AnalyticsTrackers tracker = AnalyticsTrackers.getInstance();
        return  tracker.get(AnalyticsTrackers.Target.APP);
    }

}

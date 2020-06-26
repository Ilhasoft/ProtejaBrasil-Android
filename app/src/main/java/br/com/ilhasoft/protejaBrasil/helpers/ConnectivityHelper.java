package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;
import android.net.NetworkInfo;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class ConnectivityHelper {

    public static boolean isNetworkAvailable(Context context) {
        android.net.ConnectivityManager manager =
                (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}

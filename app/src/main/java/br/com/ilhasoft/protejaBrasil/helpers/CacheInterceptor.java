package br.com.ilhasoft.protejaBrasil.helpers;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class CacheInterceptor implements Interceptor {

    private final Context context;
    private final boolean forceCache;

    public CacheInterceptor(Context context, boolean forceCache) {
        this.context = context;
        this.forceCache = forceCache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!ConnectivityHelper.isNetworkAvailable(context) || forceCache) {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        return chain.proceed(request);
    }
}

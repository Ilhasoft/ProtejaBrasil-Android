package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import java.io.File;

import br.com.ilhasoft.protejaBrasil.BuildConfig;
import br.com.ilhasoft.protejaBrasil.helpers.CacheInterceptor;
import br.com.ilhasoft.protejaBrasil.helpers.LoggingInterceptor;
import br.com.ilhasoft.protejaBrasil.helpers.RewriteResponseInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by john-mac on 4/29/16.
 */
public abstract class RetrofitServices {

    private static final int MAX_SIZE = 10 * 1024 * 1024;

    private boolean forceCache = false;

    protected void setCache(Context context, OkHttpClient.Builder clientBuilder) {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, MAX_SIZE);
        clientBuilder.cache(cache);

        clientBuilder.addNetworkInterceptor(new RewriteResponseInterceptor());
        clientBuilder.addInterceptor(new CacheInterceptor(context, forceCache));
    }

    protected void setLoggingInterceptorForDebug(OkHttpClient.Builder clientBuilder) {
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(new LoggingInterceptor());
        }
    }

    public RetrofitServices setForceCache(boolean forceCache) {
        this.forceCache = forceCache;
        return this;
    }
}

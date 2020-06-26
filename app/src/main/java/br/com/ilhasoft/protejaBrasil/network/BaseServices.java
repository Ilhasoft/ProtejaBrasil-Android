package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import br.com.ilhasoft.protejaBrasil.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by johncordeiro on 09/10/15.
 */
public abstract class BaseServices<T> extends RetrofitServices {

    private String apiToken;

    protected T service;

    protected final String lang = Locale.getDefault().getLanguage();

    public BaseServices(Class<T> serviceClass, boolean cacheEnabled, Context context) {
        initRetrofit(serviceClass, cacheEnabled, context, false);
    }

    public BaseServices(Class<T> serviceClass, boolean cacheEnabled, Context context, boolean forceCache) {
        initRetrofit(serviceClass, cacheEnabled, context, forceCache);
    }

    private void initRetrofit(Class<T> serviceClass, boolean cacheEnabled, Context context, boolean forceCache) {
        setForceCache(forceCache);

        String baseUrl = context.getString(R.string.api_base_url);
        apiToken = context.getString(R.string.api_base_token);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.readTimeout(60, TimeUnit.SECONDS);
        clientBuilder.addInterceptor(new AuthorizationInterceptor());
        setLoggingInterceptorForDebug(clientBuilder);

        if (cacheEnabled) {
            setCache(context, clientBuilder);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();

        service = retrofit.create(serviceClass);
    }

    private class AuthorizationInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", apiToken)
                    .build();

            return chain.proceed(newRequest);
        }
    }
}

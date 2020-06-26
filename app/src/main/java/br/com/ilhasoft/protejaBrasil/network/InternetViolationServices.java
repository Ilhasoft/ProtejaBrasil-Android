package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.InternetCrime;
import br.com.ilhasoft.protejaBrasil.model.InternetReportResult;
import br.com.ilhasoft.protejaBrasil.model.InternetViolation;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class InternetViolationServices extends RetrofitServices {

    private String apiToken;
    private InternetViolationApi service;

    public InternetViolationServices(Context context, boolean cacheEnabled) {
        initRetrofit(context, cacheEnabled);
    }

    private void initRetrofit(Context context, boolean cacheEnabled) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        setLoggingInterceptorForDebug(clientBuilder);

        if (cacheEnabled) {
            setCache(context, clientBuilder);
        }

        String baseUrl = context.getString(R.string.safernet_base_url);
        apiToken = context.getString(R.string.safernet_token);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .baseUrl(baseUrl)
                .build();

        service = retrofit.create(InternetViolationApi.class);
    }

    public Call<InternetViolationApi.ListResponse<InternetViolation>> listViolations() {
        return service.listViolationsByTheme(apiToken);
    }

    public Call<InternetViolationApi.ObjectResponse<InternetReportResult>> sendInternetCrime(InternetCrime internetCrime) {
        return service.sendInternetCrime(apiToken, internetCrime.getWebsite()
                , internetCrime.getDescription(), internetCrime.getViolationType().getId());
    }

}

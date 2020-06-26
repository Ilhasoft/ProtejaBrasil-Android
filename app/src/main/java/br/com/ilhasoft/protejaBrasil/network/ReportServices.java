package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import br.com.ilhasoft.protejaBrasil.BuildConfig;
import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.helpers.LoggingInterceptor;
import br.com.ilhasoft.protejaBrasil.model.InternetCrime;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class ReportServices {

    private final Retrofit.Builder retrofitBuilder;
    private final String baseUrl;
    private final String violatedWomenBaseUrl;
    private final String violatedWomenSondhaThemeId;

    public ReportServices(Context context) {
        baseUrl = context.getString(R.string.api_report_url);
        violatedWomenBaseUrl = context.getString(R.string.api_violated_women_report_url);
        violatedWomenSondhaThemeId = context.getString(R.string.violated_women_sondha_theme_id);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        setLoggingInterceptorForDebug(clientBuilder);
        retrofitBuilder = new Retrofit.Builder().client(clientBuilder.build());
    }

    public Call<ResponseBody> sendViolationReport(String body, String sondhaThemeId) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), body);
        ReportApi service = createService(sondhaThemeId.equals(violatedWomenSondhaThemeId)
                ? violatedWomenBaseUrl : baseUrl);
        return service.sendViolationReport(requestBody);
    }

    public Call<ResponseBody> sendAccessibilityReport(String body) {
        final RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), body);
        return createService(baseUrl).sendAccessibilityReport(requestBody);
    }

    public Call<InternetCrime> sendInternetCrime(InternetCrime internetCrime) {
        return createService(baseUrl).sendInternetCrime(internetCrime);
    }

    private void setLoggingInterceptorForDebug(OkHttpClient.Builder clientBuilder) {
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(new LoggingInterceptor());
        }
    }

    private ReportApi createService(String baseUrl) {
        return retrofitBuilder.baseUrl(baseUrl).build().create(ReportApi.class);
    }

}

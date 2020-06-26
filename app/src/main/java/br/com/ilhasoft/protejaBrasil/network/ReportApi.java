package br.com.ilhasoft.protejaBrasil.network;

import br.com.ilhasoft.protejaBrasil.model.InternetCrime;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by johncordeiro on 20/10/15.
 */
public interface ReportApi {

    @Headers({"Content-Type: text/xml; charset=utf-8"})
    @POST("webservice.php")
    Call<ResponseBody> sendViolationReport(@Body RequestBody body);

    @Headers({"Content-Type: text/xml; charset=utf-8"})
    @POST("webservice-acessibilidade/webservice.php")
    Call<ResponseBody> sendAccessibilityReport(@Body RequestBody body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("report/internet_crime/")
    Call<InternetCrime> sendInternetCrime(@Body InternetCrime internetCrime);

}

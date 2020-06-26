package br.com.ilhasoft.protejaBrasil.network;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.InternetReportResult;
import br.com.ilhasoft.protejaBrasil.model.InternetViolation;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by johncordeiro on 09/10/15.
 */
public interface InternetViolationApi {

    @GET("report_kit.json")
    Call<ListResponse<InternetViolation>> listViolationsByTheme(@Query("token") String token);

    @FormUrlEncoded
    @POST("report_kit.json")
    Call<ObjectResponse<InternetReportResult>> sendInternetCrime(@Field("token") String token, @Field("report[url]") String url
        , @Field("report[comment]") String comment, @Field("report_kit_last_option") String id);

    class Response {

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    class ListResponse<T> extends Response {
        private List<T> data;

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }
    }

    class ObjectResponse<T> extends Response {
        private T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }


}

package br.com.ilhasoft.protejaBrasil.network;

import br.com.ilhasoft.protejaBrasil.model.Feedback;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by johncordeiro on 18/10/15.
 */
public interface FeedbackApi {

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("feedback/")
    Call<Feedback> sendFeedback(@Body Feedback feedback);

}

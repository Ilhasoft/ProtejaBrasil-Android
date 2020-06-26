package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import br.com.ilhasoft.protejaBrasil.model.Feedback;
import retrofit2.Call;

/**
 * Created by johncordeiro on 18/10/15.
 */
public class FeedbackServices extends BaseServices<FeedbackApi> {

    public FeedbackServices(Context context) {
        super(FeedbackApi.class, true, context);
    }

    public Call<Feedback> sendFeedback(Feedback feedback) {
        return service.sendFeedback(feedback);
    }
}

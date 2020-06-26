package br.com.ilhasoft.protejaBrasil.network;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.Violation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by johncordeiro on 09/10/15.
 */
public interface ViolationApi {

    @GET("violation_type")
    Call<List<Violation>> listViolations(@Query("lang") String lang);

    @GET("violation_type/theme/{theme}")
    Call<List<Violation>> listViolationsByTheme(@Path("theme") Integer theme, @Query("lang") String lang);

    @GET("violation_type/category/{category}")
    Call<List<Violation>> listViolationsByCategory(@Path("category") String category, @Query("lang") String lang);

}

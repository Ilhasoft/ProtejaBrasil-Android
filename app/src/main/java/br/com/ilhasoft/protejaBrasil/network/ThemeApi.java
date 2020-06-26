package br.com.ilhasoft.protejaBrasil.network;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.Theme;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by johncordeiro on 18/10/15.
 */
public interface ThemeApi {

    @GET("themes")
    Call<List<Theme>> listThemes(@Query("lang") String lang);

}

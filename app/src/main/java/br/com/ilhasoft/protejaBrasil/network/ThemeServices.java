package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.Theme;
import retrofit2.Call;

/**
 * Created by johncordeiro on 18/10/15.
 */
public class ThemeServices extends BaseServices<ThemeApi> {

    public ThemeServices(Context context) {
        super(ThemeApi.class, true, context);
    }

    public Call<List<Theme>> listThemes() {
        return service.listThemes(lang);
    }
}

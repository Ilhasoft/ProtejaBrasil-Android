package br.com.ilhasoft.protejaBrasil.network;

import android.content.Context;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.Violation;
import br.com.ilhasoft.protejaBrasil.model.ViolationCategory;
import retrofit2.Call;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class ViolationServices extends BaseServices<ViolationApi> {

    public ViolationServices(Context context) {
        super(ViolationApi.class, true, context);
    }

    public Call<List<Violation>> listViolations() {
        return service.listViolations(lang);
    }

    public Call<List<Violation>> listViolationsByCategory(ViolationCategory.Category category) {
        return service.listViolationsByCategory(category.toString(),lang);
    }

    public Call<List<Violation>> listViolationsByTheme(Theme theme) {
        return service.listViolationsByTheme(theme.getId(),lang);
    }
}

package br.com.ilhasoft.protejaBrasil.loaders;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.Violation;
import br.com.ilhasoft.protejaBrasil.model.ViolationCategory;
import br.com.ilhasoft.protejaBrasil.network.ViolationServices;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class ViolationLoader extends AsyncTaskLoader<List<Violation>> {

    private static final String TAG = "ViolationLoader";

    private ViolationCategory.Category category;
    private Theme theme;

    public ViolationLoader(Context context, ViolationCategory.Category category) {
        super(context);
        this.category = category;
    }

    public ViolationLoader(Context context, Theme theme) {
        super(context);
        this.theme = theme;
    }

    public ViolationLoader(Context context) {
        super(context);
    }

    @Override
    public List<Violation> loadInBackground() {
        try {
            ViolationServices services = new ViolationServices(getContext());

            if(category != null) {
                return services.listViolationsByCategory(category).execute().body();
            } else if(theme != null) {
                return services.listViolationsByTheme(theme).execute().body();
            } else {
                return services.listViolations().execute().body();
            }
        } catch(Exception exception) {
            Log.e(TAG, "loadInBackground ", exception);
        }
        return new ArrayList<>();
    }
}

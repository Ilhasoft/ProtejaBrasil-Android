package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;
import android.util.Log;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.helpers.InternetCrimeConverter;
import br.com.ilhasoft.protejaBrasil.model.InternetCrime;
import br.com.ilhasoft.protejaBrasil.model.view.InternetCrimeViewModel;
import br.com.ilhasoft.protejaBrasil.network.ReportServices;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class SaveInternetCrimeTask extends ProgressTask<InternetCrimeViewModel, Void, InternetCrime> {

    private static final String TAG = "SaveInternetCrime";

    public SaveInternetCrimeTask(Context context) {
        super(context, R.string.load_message_report);
    }

    @Override
    protected InternetCrime doInBackground(InternetCrimeViewModel... params) {
        try {
            InternetCrimeViewModel viewModel = params[0];

            InternetCrimeConverter converter = new InternetCrimeConverter();
            InternetCrime internetCrime = converter.internetCrimeFromViewModel(viewModel);

            ReportServices services = new ReportServices(getContext());
            return services.sendInternetCrime(internetCrime).execute().body();
        } catch(Exception exception) {
            Log.e(TAG, "doInBackground ", exception);
        }
        return null;
    }

}

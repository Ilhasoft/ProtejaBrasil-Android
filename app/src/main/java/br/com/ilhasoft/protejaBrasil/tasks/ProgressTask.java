package br.com.ilhasoft.protejaBrasil.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.StringRes;

/**
 * Created by johncordeiro on 21/10/15.
 */
public abstract class ProgressTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Context context;
    private @StringRes int message;

    private ProgressDialog progressDialog;

    public ProgressTask(Context context, int message) {
        this.context = context;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, null, context.getString(message)
                , true, false);
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
    }

    public Context getContext() {
        return context;
    }
}

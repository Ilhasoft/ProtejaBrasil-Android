package br.com.ilhasoft.protejaBrasil.activities;

import android.content.DialogInterface;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.listener.OnCloseListener;

/**
 * Created by Dielson Sales on 25/02/16.
 */
public abstract class ReportBaseActivity extends TrackedActivity implements OnCloseListener {

    @Override
    public boolean onSupportNavigateUp() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            showAbortAlert();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

    protected void setActionBarWithNavigationUp(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void showAbortAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(ReportBaseActivity.this)
                .setMessage(R.string.message_cancel_report)
                .setNegativeButton(R.string.option_give_up, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onGiveUpReport();
                        finish();
                    }
                })
                .setPositiveButton(R.string.option_cancel, null)
                .create();
        alertDialog.show();
    }

    abstract void onGiveUpReport();

    @Override
    public void onClose() {
        finish();
    }
}

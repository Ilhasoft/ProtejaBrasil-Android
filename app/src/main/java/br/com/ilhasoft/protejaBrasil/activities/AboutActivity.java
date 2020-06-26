package br.com.ilhasoft.protejaBrasil.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ActivityAboutBinding;

/**
 * Created by johncordeiro on 22/10/15.
 */
public class AboutActivity extends TrackedActivity {

    private static final String ANALYTICS_SCREEN_NAME = "About";
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        setupView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setupView() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}

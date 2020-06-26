package br.com.ilhasoft.protejaBrasil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.support.preferences.Preferences;
import br.com.ilhasoft.support.view.BaseActivity;

public class OnboardActivity extends BaseActivity {

    private Preferences preferences;
    private final String onboardKey = "onboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyOnboardDisplay();
        setContentView(R.layout.activity_onboard);
        final Button proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed.setEnabled(false);
                navigateToMain();
            }
        });
    }

    private void verifyOnboardDisplay() {
        preferences = new Preferences(this);
        if (!preferences.getValue(onboardKey, false)) {
            preferences.setValue(onboardKey, true);
            return;
        }

        navigateToMain();
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}

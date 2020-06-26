package br.com.ilhasoft.protejaBrasil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ActivityThemesBinding;
import br.com.ilhasoft.protejaBrasil.fragments.ThemesFragment;
import br.com.ilhasoft.protejaBrasil.model.Theme;

/**
 * Created by john-mac on 5/23/16.
 */
public class ThemeActivity extends AppCompatActivity {

    public static final String EXTRA_THEMES = "themes";

    private ActivityThemesBinding binding;

    public static Intent createIntent(Context context, ArrayList<Theme> themes) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_THEMES, themes);

        Intent intent = new Intent(context, ThemeActivity.class);
        intent.putExtra(EXTRA_THEMES, themes);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_themes);
        setupView();

        if(savedInstanceState == null) {
            ArrayList<Theme> themes = getIntent().getParcelableArrayListExtra(EXTRA_THEMES);

            ThemesFragment themesFragment = ThemesFragment.newInstance(themes);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, themesFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setupView() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

package br.com.ilhasoft.protejaBrasil.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ActivityMainBinding;
import br.com.ilhasoft.protejaBrasil.fragments.MapFragment;
import br.com.ilhasoft.protejaBrasil.fragments.ThemesFragment;
import br.com.ilhasoft.protejaBrasil.listener.OnReportSelectedListener;
import br.com.ilhasoft.protejaBrasil.listener.OnThemesLoadedListener;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.network.ThemeServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johncordeiro on 08/10/15.
 */
public class MainActivity extends TrackedActivity implements OnReportSelectedListener {

    private static final String TAG = "MainActivity";

    public static final int MENU_CLOSE_DELAY_MILLIS = 500;

    private static final String MAP_FRAGMENT_TAG = "map_fragment";

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActivityMainBinding binding;

    private ArrayList<Theme> themes;
    private Set<Theme> selectedThemes;

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupView();

        ThemeServices themeServices = new ThemeServices(this);
        themeServices.listThemes().enqueue(onThemesLoadedCallback);

        if(savedInstanceState == null) {
            addMapFragment(false);
        }
    }

    public EditText getSearchField() {
        return binding.search;
    }

    private void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout
                , toolbar, R.string.label_open_menu, R.string.label_close_menu);

        resetHomeIcon();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.menuNavigation.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return actionBarDrawerToggle;
    }

    public Toolbar getToolbar() {
        return binding.toolbar;
    }

    public void resetHomeIcon() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Nullable
    private Theme getSelectedThemes(MenuItem item) {
        for (Theme theme : themes) {
            if(theme.getId().equals(item.getItemId())) {
                return theme;
            }
        }
        return null;
    }

    public Set<Theme> getSelectedThemes() {
        return selectedThemes;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setupThemes(menu);
        return true;
    }

    private void setupThemes(Menu menu) {
        selectedThemes = new HashSet<>();
        if(themes != null) {
            getMenuInflater().inflate(R.menu.menu_themes, menu);
            for (int i = 0; i < themes.size(); i++) {
                Theme theme = themes.get(i);

                MenuItem menuItem = menu.add(R.id.themesGroup, theme.getId(), Menu.NONE, theme.getTitle());
                menuItem.setCheckable(true);
                MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_NEVER);

                selectedThemes.add(theme);
                menuItem.setChecked(true);

            }
            reloadSelectedThemes();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!actionBarDrawerToggle.onOptionsItemSelected(item)) {
            checkSelectedTheme(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkSelectedTheme(MenuItem item) {
        item.setChecked(!item.isChecked());
        if (item.isChecked()) {
            addThemeToSelectedThemes(item.getItemId());
        } else {
            removeThemeFromSelectedThemes(item.getItemId());
        }
        reloadSelectedThemes();
    }

    private void addThemeToSelectedThemes(int menuItemId) {
        for (Theme theme : themes) {
            if (theme.getId() == menuItemId) {
                selectedThemes.add(theme);
            }
        }
    }

    private void removeThemeFromSelectedThemes(int menuItemId) {
        for (Theme theme : themes) {
            if (theme.getId() == menuItemId) {
                selectedThemes.remove(theme);
            }
        }
    }

    private void reloadSelectedThemes() {
        Log.d(TAG, "Selected themes: " + selectedThemes.toString());
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if(fragment instanceof MapFragment) {
            ((MapFragment)fragment).onThemesSelected(selectedThemes);
        }
    }

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(final MenuItem menuItem) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            binding.drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch(menuItem.getItemId()) {
                        case R.id.action_map:
                            menuItem.setChecked(true);
                            addMapFragment(false);
                            break;
                        case R.id.action_denounce:
                            menuItem.setChecked(true);
                            openDenounceFloatingActions();
                            break;
                        case R.id.action_violation_types:
                            Intent themeIntent = ThemeActivity.createIntent(MainActivity.this, themes);
                            startActivity(themeIntent);
                            break;
                        case R.id.action_opinion:
                            Intent opinionIntent = new Intent(MainActivity.this, OpinionActivity.class);
                            startActivity(opinionIntent);
                            break;
                        case R.id.action_about:
                            Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(aboutIntent);
                            break;
                    }
                }
            }, MENU_CLOSE_DELAY_MILLIS);
            return true;
        }
    };

    private Callback<List<Theme>> onThemesLoadedCallback = new Callback<List<Theme>>() {
        @Override
        public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
            themes = (ArrayList<Theme>) response.body();
            updateFragment();
            invalidateOptionsMenu();
        }

        @Override
        public void onFailure(Call<List<Theme>> call, Throwable throwable) {
            Log.e(TAG, "onFailure ", throwable);
        }
    };

    private void updateFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if(fragment instanceof OnThemesLoadedListener) {
            ((OnThemesLoadedListener)fragment).onThemesLoaded(themes);
        }
    }

    private void addMapFragment(boolean shouldOpenFloatingActionMenu) {
        if (mapFragment != null && mapFragment.isVisible()) {
            // do nothing, map is already on the screen
        } else {
            mapFragment = new MapFragment();
            mapFragment.setShouldOpenFloatingActionMenu(shouldOpenFloatingActionMenu);
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.content, mapFragment, MAP_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void openDenounceFloatingActions() {
        if (mapFragment != null && mapFragment.isVisible()) {
            mapFragment.openFloatingActionMenu();
        } else {
            addMapFragment(true);
        }
    }


    private void addViolationsFragment() {
        ThemesFragment themesFragment = ThemesFragment.newInstance(getThemes());
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.content, themesFragment)
                .commit();
        mapFragment = null;
    }

    @Override
    public void onReportAccessibility() {
        Intent accessibilityReportIntent = new Intent(MainActivity.this, AccessibilityReportActivity.class);
        startActivity(accessibilityReportIntent);
    }

    @Override
    public void onReportInternetCrime(Location location) {
        Intent internetCrimeIntent = new Intent(this, InternetCrimeActivity.class);
        startActivity(internetCrimeIntent);
    }

    @Override
    public void onReportViolationSelected(Location location) {
        Intent reportViolationIntent = new Intent(this, ViolationReportActivity.class);
        reportViolationIntent.putExtra(StepBasedReportActivity.EXTRA_LOCATION, location);
        startActivity(reportViolationIntent);
    }

    @Override
    public void onConfirmAccessibilityReport(Bundle bundle) {
        Intent accessibilityReportIntent = new Intent(MainActivity.this, AccessibilityReportActivity.class);
        accessibilityReportIntent.putExtras(bundle);
        startActivity(accessibilityReportIntent);
    }

    @Override
    public void onCallToDisk100() {
        sendActionReport(AnalyticsAction.CallToDisque100);
    }

    @Override
    public void onCallToDisk180() {
        sendActionReport(AnalyticsAction.CallToDisque180);
    }
}

package br.com.ilhasoft.protejaBrasil.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.droidlabs.mvvm.recyclerview.events.ClickHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.activities.ViolationDetailsActivity;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentThemesBinding;
import br.com.ilhasoft.protejaBrasil.listener.OnThemesLoadedListener;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.view.ThemeViewModel;
import br.com.ilhasoft.support.databinding.helpers.ObservableListHelper;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class ThemesFragment extends TrackedFragment implements OnThemesLoadedListener {

    public static final String EXTRA_THEMES = "themes";
    private static final String ANALYTICS_SCREEN_NAME = "Themes list";

    private FragmentThemesBinding binding;
    private List<Theme> themes;

    public static ThemesFragment newInstance(ArrayList<Theme> themes) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_THEMES, themes);

        ThemesFragment fragment = new ThemesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_themes, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupData();
        setupView();

        List<Theme> themes = getArguments().getParcelableArrayList(EXTRA_THEMES);
        updateThemes(themes);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);
    }

    private void setupData() {
        ThemeViewModel themeViewModel = new ThemeViewModel(onThemeClickListener);
        binding.setThemeViewModel(themeViewModel);
    }

    private void setupView() {
        setHasOptionsMenu(true);
        binding.violationsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public ClickHandler<Theme> onThemeClickListener = new ClickHandler<Theme>() {
        @Override
        public void onClick(Theme object) {
            Log.i("ThemesFragment", "onClick called");

            Intent violationDetailsIntent = new Intent(getContext(), ViolationDetailsActivity.class);
            violationDetailsIntent.putExtra(ViolationDetailsActivity.EXTRA_THEME, object);

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), getTransitionsByTheme(object));
            ActivityCompat.startActivity(getActivity(), violationDetailsIntent, optionsCompat.toBundle());
        }
    };

    @NonNull
    private Pair<View, String>[] getTransitionsByTheme(Theme object) {
        int indexOfTheme = themes.indexOf(object);

        RecyclerView.ViewHolder themeHolder = binding.violationsList.findViewHolderForAdapterPosition(indexOfTheme);

        View icon = themeHolder.itemView.findViewById(R.id.icon);
        View description = themeHolder.itemView.findViewById(R.id.violation_description);

        Pair<View, String> iconPair = Pair.create(icon
                , getString(R.string.transition_theme_icon));

        Pair<View, String> descriptionPair = Pair.create(description
                , getString(R.string.transition_theme_description));

        Pair<View, String> [] views = (Pair<View, String> []) Array.newInstance(Pair.class, 2);
        views[0] = iconPair;
        views[1] = descriptionPair;
        return views;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private TextWatcher onTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable editable) {
            if(themes != null && !themes.isEmpty()) {
                filterThemesByName(editable);
            }
        }
    };

    private void filterThemesByName(Editable editable) {
        if(editable != null && editable.length() > 0) {
            List<Theme> themesFound = new ArrayList<>();
            for (Theme theme : themes) {
                if(theme.getTitle().toLowerCase().contains(editable.toString().toLowerCase())) {
                    themesFound.add(theme);
                }
            }
            updateThemesList(themesFound);
        } else {
            updateThemesList(themes);
        }
    }

    private void updateThemesList(List<Theme> themes) {
        ObservableArrayList<Theme> themesObservableList = binding.getThemeViewModel().themes;

        ObservableListHelper helper = new ObservableListHelper();
        helper.updateObservableArrayList(themes, themesObservableList);
    }

    @Override
    public void onThemesLoaded(List<Theme> themes) {
        updateThemes(themes);
    }

    private void updateThemes(List<Theme> themes) {
        if(themes != null) {
            this.themes = themes;
            updateThemesList(themes);
        }
    }
}

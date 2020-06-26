package br.com.ilhasoft.protejaBrasil.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.droidlabs.mvvm.recyclerview.events.ClickHandler;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.activities.ViolationDetailsActivity;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentViolationsBinding;
import br.com.ilhasoft.protejaBrasil.listener.OnViolationSelectedListener;
import br.com.ilhasoft.protejaBrasil.loaders.ViolationLoader;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.Violation;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationTypeViewModel;
import br.com.ilhasoft.support.databinding.helpers.ObservableListHelper;

/**
 * Created by johncordeiro on 09/10/15.
 */
public class ViolationsFragment extends TrackedFragment implements LoaderManager.LoaderCallbacks<List<Violation>> {

    private static final int LOADER_ID = 101;
    private static final String EXTRA_THEME = "theme";
    private static final String ANALYTICS_SCREEN_NAME = "Violation types list";

    private FragmentViolationsBinding binding;

    private OnViolationSelectedListener onViolationSelectedListener;

    private Theme theme;

    public static ViolationsFragment newInstance(Theme theme) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_THEME, theme);
        ViolationsFragment fragment = new ViolationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_violations, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupData();
        setupView();

        theme = getArguments().getParcelable(EXTRA_THEME);
        loadData(theme);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnViolationSelectedListener) {
            onViolationSelectedListener = (OnViolationSelectedListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof ViolationDetailsActivity) {
            ((ViolationDetailsActivity)getActivity()).setupTheme();
        }
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);
    }

    private void setupData() {
        ViolationTypeViewModel violationTypeViewModel = new ViolationTypeViewModel(onViolationClickList);
        binding.setViolationViewModel(violationTypeViewModel);
        binding.setLoaded(false);
    }

    private void setupView() {
        setHasOptionsMenu(true);
        binding.violationsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.swipeToRefresh.setOnRefreshListener(onRefreshListener);
    }

    public ClickHandler<Violation> onViolationClickList = new ClickHandler<Violation>() {
        @Override
        public void onClick(Violation object) {
            onViolationSelectedListener.onViolationSelected(object);
        }
    };

    private void loadData(Theme theme) {
        if(theme == null) return;

        Loader loader = getLoaderManager().getLoader(LOADER_ID);
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_THEME, theme);

        if(loader != null) {
            getLoaderManager().restartLoader(LOADER_ID, args, this).forceLoad();
        } else {
            getLoaderManager().initLoader(LOADER_ID, args, this).forceLoad();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public Loader<List<Violation>> onCreateLoader(int id, Bundle args) {
        return new ViolationLoader(getContext(), (Theme)args.getParcelable(EXTRA_THEME));
    }

    @Override
    public void onLoadFinished(Loader<List<Violation>> loader, List<Violation> violations) {
        binding.swipeToRefresh.setRefreshing(false);
        binding.setLoaded(true);
        if(violations != null) {
            updateViolations(violations);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Violation>> loader) {}

    private void updateViolations(List<Violation> violations) {
        ObservableArrayList<Violation> violationObservableList = binding.getViolationViewModel().violations;

        ObservableListHelper helper = new ObservableListHelper();
        helper.updateObservableArrayList(violations, violationObservableList);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData(theme);
        }
    };

}

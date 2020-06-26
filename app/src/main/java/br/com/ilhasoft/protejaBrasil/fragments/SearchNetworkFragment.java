package br.com.ilhasoft.protejaBrasil.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.droidlabs.mvvm.recyclerview.events.ClickHandler;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.activities.MainActivity;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentSearchNetworkBinding;
import br.com.ilhasoft.protejaBrasil.helpers.SearchableFragment;
import br.com.ilhasoft.protejaBrasil.listener.TaskListener;
import br.com.ilhasoft.protejaBrasil.model.ProtectionNetwork;
import br.com.ilhasoft.protejaBrasil.model.view.ProtectionNetworkViewModel;
import br.com.ilhasoft.protejaBrasil.tasks.ProtectionNetworkLoadTask;

/**
 * Created by johncordeiro on 22/10/15.
 */
public class SearchNetworkFragment extends Fragment implements SearchableFragment, TaskListener<ProtectionNetwork> {

    private FragmentSearchNetworkBinding binding;
    private ProtectionNetworkViewModel viewModel;

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_network, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        setupData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetHomeIcon();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MainActivity) {
            mainActivity = (MainActivity)activity;
            mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            mainActivity.getToolbar().setOnMenuItemClickListener(onToolbarNavigationClickListener);
        }
    }

    private void resetHomeIcon() {
        if(mainActivity != null) {
            mainActivity.resetHomeIcon();
            mainActivity.getActionBarDrawerToggle().setToolbarNavigationClickListener(null);
            mainActivity.getToolbar().setOnMenuItemClickListener(onToolbarNavigationClickListener);
        }
    }

    private void setupView() {
        binding.networkList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void setupData() {
        viewModel = new ProtectionNetworkViewModel(onClickHandler);
        binding.setProtectionViewModel(viewModel);
    }

    private ClickHandler onClickHandler = new ClickHandler<ProtectionNetwork>() {
        @Override
        public void onClick(ProtectionNetwork object) {
            super.onClick(object);
        }
    };

    @Override
    public void afterTextChanged(String text) {
        new ProtectionNetworkLoadTask(getContext(), this, null).execute(text);
    }

    @Override
    public void onTaskFinished(List<ProtectionNetwork> items) {
        viewModel.list.clear();
        viewModel.list.addAll(items);
    }

    private Toolbar.OnMenuItemClickListener onToolbarNavigationClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            getFragmentManager().popBackStack();
            return false;
        }
    };
}

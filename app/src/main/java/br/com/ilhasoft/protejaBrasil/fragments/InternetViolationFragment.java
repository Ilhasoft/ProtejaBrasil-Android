package br.com.ilhasoft.protejaBrasil.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.databinding.DataBindingUtil;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.activities.InternetCrimeActivity;
import br.com.ilhasoft.protejaBrasil.activities.ViolationDetailsActivity;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentInternetViolationBinding;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.Violation;
import br.com.ilhasoft.support.tabs.CustomTabActivityHelper;

/**
 * Created by john-mac on 1/7/16.
 */
public class InternetViolationFragment extends TrackedFragment {

    private static final String EXTRA_VIOLATION = "violation";
    private static final String ANALYTICS_SCREEN_NAME = "Internet crime detail";

    private static final String URL_SAFERNET = "http://new.safernet.org.br/helpline";

    private FragmentInternetViolationBinding binding;
    private CustomTabActivityHelper tabActivityHelper;

    public static InternetViolationFragment newInstance(Violation violation) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_VIOLATION, violation);
        InternetViolationFragment fragment = new InternetViolationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_internet_violation, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObjects();
        setupData();
        setupView();
    }

    @Override
    public void onStart() {
        super.onStart();
        tabActivityHelper.bindCustomTabsService(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        sendScreenViewReport(ANALYTICS_SCREEN_NAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        tabActivityHelper.unbindCustomTabsService(getActivity());
    }

    private void setupView() {
        binding.help.setOnClickListener(onHelpClickListener);
        binding.report.setOnClickListener(onReportClickListener);
    }

    private void setupObjects() {
        tabActivityHelper = new CustomTabActivityHelper();
        tabActivityHelper.mayLaunchUrl(Uri.parse(URL_SAFERNET), null, null);
    }

    private void setupData() {
        Violation violation = getArguments().getParcelable(EXTRA_VIOLATION);
        binding.setViolation(violation);
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openWebPage();
        }
    };

    private void openWebPage() {
        Theme theme = ((ViolationDetailsActivity)getActivity()).getSelectedTheme();

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(Color.parseColor(theme.getColor()));
        intentBuilder.setShowTitle(true);
        tabActivityHelper.openCustomTab(getActivity(), intentBuilder.build(), Uri.parse(URL_SAFERNET));
    }

    private View.OnClickListener onReportClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent internetCrimeIntent = new Intent(getActivity(), InternetCrimeActivity.class);
            startActivity(internetCrimeIntent);
        }
    };
}

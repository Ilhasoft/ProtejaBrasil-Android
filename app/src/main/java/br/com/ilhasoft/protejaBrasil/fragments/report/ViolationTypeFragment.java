package br.com.ilhasoft.protejaBrasil.fragments.report;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.BR;
import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.FragmentViolationTypeBinding;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.managers.DefaultDataManager;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;
import br.com.ilhasoft.protejaBrasil.network.ThemeServices;
import br.com.ilhasoft.support.databinding.bindings.Bindable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johncordeiro on 17/10/15.
 */
public class ViolationTypeFragment extends BaseReportFragment<ViolationReportViewModel> {

    private static final String TAG = "ViolationTypeFragment";

    private static final int RACIAL_SONDHA_ID = 9;
    private static final int WOMEN_SONDHA_ID = 774;


    private FragmentViolationTypeBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_violation_type, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        loadThemes();
    }

    protected void setupData(ViolationReportViewModel viewModel) {
        binding.setViewModel(viewModel);
        binding.getViewModel().reportTheme.setOnBindableChangedListener(onSubtypeChangeListener);
        binding.setThemeId(BR.theme);
    }

    @Override
    public boolean validateForm() {
        return validateSpinner(binding.themesSpinner, binding.getViewModel().reportTheme)
                && ((!isRacialTheme() && !isViolenceAgainstWomanTheme()) || validateSpinner(binding.subtypesSpinner, binding.getViewModel().subtypes));
    }

    private boolean isRacialTheme() {
        return ((Theme) binding.getViewModel().reportTheme.get()).getSondhaId() == RACIAL_SONDHA_ID;
    }

    private boolean isViolenceAgainstWomanTheme() {
        return ((Theme) binding.getViewModel().reportTheme.get()).getSondhaId() == WOMEN_SONDHA_ID;
    }

    private void loadThemes() {
        ThemeServices themeServices = new ThemeServices(getContext());
        themeServices.listThemes().enqueue(onThemesLoadedListener);
    }

    private Callback<List<Theme>> onThemesLoadedListener = new Callback<List<Theme>>() {
        @Override
        public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
            List<Theme> themes = response.body();
            binding.setThemes(themes);
        }

        @Override
        public void onFailure(Call<List<Theme>> call, Throwable throwable) {
            Log.e(TAG, "onFailure ", throwable);
        }
    };

    private Bindable.OnBindableChangedListener<Parcelable> onSubtypeChangeListener = new Bindable.OnBindableChangedListener<Parcelable>() {
        @Override
        public void onBindableChanged(Parcelable object) {
            if (object != null) {
                Theme theme = (Theme) object;
                List<ResponseObject> subtypes = new ArrayList<>();
                String subtitle = "";
                if (isRacialTheme()) {
                    subtypes = DefaultDataManager.getRacialSubtypes(getContext());
                    subtitle = getString(R.string.title_question_racial_subtype);
                } else if (isViolenceAgainstWomanTheme()) {
                    subtypes = DefaultDataManager.getViolenceAgainsWomenOptions(getContext());
                    subtitle = getString(R.string.title_question_women_subtype);
                }
                binding.setSubtypes(subtypes);
                binding.setSubtypesTitle(subtitle);
                setupSubtypesVisibilityForTheme(theme);
            }
        }
    };

    private void setupSubtypesVisibilityForTheme(Theme theme) {
        int visibility = (theme.getSondhaId() == RACIAL_SONDHA_ID || theme.getSondhaId() == WOMEN_SONDHA_ID) ? View.VISIBLE : View.GONE;
        binding.subtypesSpinner.setVisibility(visibility);
        binding.subtypeQuestion.setVisibility(visibility);
    }

    private void setupViews() {
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.questionVictimProfile);
        ReportFlowHelper.redifyAsterisk(getActivity(), binding.subtypeQuestion);
    }

}

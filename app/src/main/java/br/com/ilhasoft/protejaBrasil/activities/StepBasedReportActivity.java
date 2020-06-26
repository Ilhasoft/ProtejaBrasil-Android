package br.com.ilhasoft.protejaBrasil.activities;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.databinding.ActivityReportBinding;
import br.com.ilhasoft.protejaBrasil.fragments.report.BaseReportFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.MultipleStepsReport;

/**
 * Created by johncordeiro on 17/10/15.
 */
public abstract class StepBasedReportActivity extends ReportBaseActivity implements MultipleStepsReport {

    public static final String EXTRA_LOCATION = "location";

    private ActivityReportBinding binding;

    private List<BaseReportFragment> fragmentSteps;

    protected Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        setupView();
        location = getIntent().getParcelableExtra(EXTRA_LOCATION);

        if(savedInstanceState == null) {
            startViolationReport();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenViewReport(getScreenName());
        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSupportFragmentManager().removeOnBackStackChangedListener(onBackStackChangedListener);
    }

    private void setupView() {
        showNextButton();
        binding.cancelReport.setOnClickListener(onCancelReportClickListener);
        binding.next.setOnClickListener(onNextClickListener);
        binding.report.setOnClickListener(onReportClickListener);

        setActionBarWithNavigationUp(binding.toolbar);
    }

    private void startViolationReport() {
        fragmentSteps = setupReportSteps();

        binding.setProgressValue(calculateProgress(1));
        nextStep();
    }

    private void nextStep() {
        BaseReportFragment currentReportFragment = getCurrentReportFragment();
        int indexOfNextFragment = getIndexOfNextFragment(currentReportFragment);

        BaseReportFragment reportFragment = fragmentSteps.get(indexOfNextFragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left
                        , android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.content, reportFragment);
        if(indexOfNextFragment > 0) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    protected abstract String getScreenName();

    protected abstract List<BaseReportFragment> setupReportSteps();

    protected abstract void saveViolationReport();

    private int getIndexOfNextFragment(BaseReportFragment currentReportFragment) {
        return currentReportFragment != null
                    && fragmentSteps.indexOf(currentReportFragment) >= 0 ? fragmentSteps.indexOf(currentReportFragment)+1 : 0;
    }

    private BaseReportFragment getCurrentReportFragment() {
        return (BaseReportFragment)getSupportFragmentManager().findFragmentById(R.id.content);
    }

    private int calculateProgress(int currentStep) {
        return (100 * currentStep)/fragmentSteps.size();
    }

    private View.OnClickListener onNextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getCurrentReportFragment().validateForm()) {
                nextStep();
            } else {
                showValidationErrorToast();
            }
        }
    };

    private View.OnClickListener onReportClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (getCurrentReportFragment().validateForm()) {
                saveViolationReport();
            } else {
                showValidationErrorToast();
            }
        }
    };

    private void showValidationErrorToast() {
        Toast.makeText(StepBasedReportActivity.this, R.string.error_required_fields, Toast.LENGTH_LONG).show();
    }

    private View.OnClickListener onCancelReportClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAbortAlert();
        }
    };

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            int progress = calculateProgress(fragmentSteps.indexOf(getCurrentReportFragment()) + 1);
            binding.setProgressValue(progress);
            selectCorrectButton();
        }
    };

    @Override
    public void hideRequiredFieldsWarning() {
        binding.requiredFieldsWarning.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void showRequiredFieldsWarning() {
        binding.requiredFieldsWarning.getRoot().setVisibility(View.VISIBLE);
    }

    /**
     * Shows the "Report" button only during the last step of the report.
     */
    private void selectCorrectButton() {
        if (fragmentSteps.indexOf(getCurrentReportFragment()) < fragmentSteps.size() - 1) {
            showNextButton();
        } else {
            showReportButton();
        }
    }

    private void showNextButton() {
        binding.report.setVisibility(View.GONE);
        binding.next.setVisibility(View.VISIBLE);
    }

    private void showReportButton() {
        binding.next.setVisibility(View.GONE);
        binding.report.setVisibility(View.VISIBLE);
    }

}

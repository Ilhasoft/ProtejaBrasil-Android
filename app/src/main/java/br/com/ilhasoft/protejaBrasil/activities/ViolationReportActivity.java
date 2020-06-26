package br.com.ilhasoft.protejaBrasil.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.fragments.report.BaseReportFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.MultipleStepsReport;
import br.com.ilhasoft.protejaBrasil.fragments.report.OffenderInfoFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ProtocolFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.VictimInfoFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ViolationAddressFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ViolationDescriptionFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ViolationInfoFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ViolationSubjectGenderFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ViolationSubjectInfoFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ViolationTypeFragment;
import br.com.ilhasoft.protejaBrasil.model.SubjectType;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.ViolationReport;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;
import br.com.ilhasoft.protejaBrasil.tasks.SaveViolationReportTask;

/**
 * Created by johncordeiro on 17/10/15.
 */
public class ViolationReportActivity extends StepBasedReportActivity implements MultipleStepsReport {

    private static final String ANALYTICS_SCREEN_NAME = "Violation report";

    private ViolationReportViewModel violationReportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendActionReport(AnalyticsAction.StartViolationReport);
    }

    protected List<BaseReportFragment> setupReportSteps() {
        violationReportViewModel = new ViolationReportViewModel();

        List<BaseReportFragment> fragmentSteps = new ArrayList<>();
        fragmentSteps.add(BaseReportFragment.newInstance(ViolationTypeFragment.class, violationReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(VictimInfoFragment.class, violationReportViewModel, true));
        fragmentSteps.add(ViolationAddressFragment.newInstance(violationReportViewModel, location, SubjectType.Victim, true));
        fragmentSteps.add(ViolationSubjectInfoFragment.newInstance(violationReportViewModel, SubjectType.Victim, false));
        fragmentSteps.add(ViolationSubjectGenderFragment.newInstance(violationReportViewModel, SubjectType.Victim, true));
        fragmentSteps.add(BaseReportFragment.newInstance(ViolationInfoFragment.class, violationReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(ViolationDescriptionFragment.class, violationReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(OffenderInfoFragment.class, violationReportViewModel, true));
        fragmentSteps.add(ViolationAddressFragment.newInstance(violationReportViewModel, location, SubjectType.Offender, true));
        fragmentSteps.add(ViolationSubjectInfoFragment.newInstance(violationReportViewModel, SubjectType.Offender, false));
        fragmentSteps.add(ViolationSubjectGenderFragment.newInstance(violationReportViewModel, SubjectType.Offender, true));
        return fragmentSteps;
    }

    @Override
    protected String getScreenName() {
        return ANALYTICS_SCREEN_NAME;
    }

    protected void saveViolationReport() {
        SaveViolationReportTask saveViolationReportTask = new SaveViolationReportTask(this) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    sendActionReport(AnalyticsAction.FinishViolationReport);
                    addProtocolFragment(result);
                } else {
                    Toast.makeText(ViolationReportActivity.this, R.string.message_report_error, Toast.LENGTH_SHORT).show();
                }
            }
        };
        saveViolationReportTask.execute(violationReportViewModel);
    }

    private void addProtocolFragment(String protocol) {
        ProtocolFragment protocolFragment = setupProtocolFragment(protocol);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(android.R.id.content, protocolFragment)
                .commit();
    }

    private ProtocolFragment setupProtocolFragment(String protocol) {
        final Theme theme = (Theme) violationReportViewModel.reportTheme.get();
        int diskNumber;
        @StringRes Integer diskNumberStringRes;

        if (theme.getSondhaId() == ViolationReport.VIOLATION_AGAINST_WOMEN_ID) {
            diskNumber = 180;
            diskNumberStringRes = R.string.disk_180;
        } else {
            diskNumber = 100;
            diskNumberStringRes = R.string.disk_100;
        }

        return ProtocolFragment.newInstance(protocol
                , getString(diskNumberStringRes), getString(R.string.protocol_info, diskNumber));
    }

    /**
     * Sends the GiveUpViolationReport action to Google Analytics
     */
    @Override
    void onGiveUpReport() {
        sendActionReport(AnalyticsAction.GiveUpViolationReport);
    }
}

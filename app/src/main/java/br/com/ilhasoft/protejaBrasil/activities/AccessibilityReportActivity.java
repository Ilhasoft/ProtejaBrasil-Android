package br.com.ilhasoft.protejaBrasil.activities;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.fragments.report.AccessibilityInstitutionFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.AccessibilityLocationFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.AccessibilityVictimFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.AccessibilityVictimGenderFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.AccessibilityVictimInfoFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.AccessibilityViolationFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.BaseReportFragment;
import br.com.ilhasoft.protejaBrasil.fragments.report.ProtocolFragment;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;
import br.com.ilhasoft.protejaBrasil.tasks.SaveAccessibilityReportTask;

/**
 * Created by ilhasoft on 02/03/16.
 */
public class AccessibilityReportActivity extends StepBasedReportActivity {

    private final static String TAG = "AccessibilityReport";
    private static final String ANALYTICS_SCREEN_NAME = "No accessibility report";
    private static final int COUNCIL_NUMBER = 100;

    private AccessibilityReportViewModel accessibilityReportViewModel;

    @Override
    protected void onStart() {
        super.onStart();
        sendActionReport(AnalyticsAction.StartNoAccessibilityReport);
    }

    @Override
    protected String getScreenName() {
        return ANALYTICS_SCREEN_NAME;
    }

    @Override
    protected List<BaseReportFragment> setupReportSteps() {
        accessibilityReportViewModel = new AccessibilityReportViewModel();

        List<BaseReportFragment> fragmentSteps = new ArrayList<>();
        fragmentSteps.add(BaseReportFragment.newInstance(AccessibilityLocationFragment.class, accessibilityReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(AccessibilityViolationFragment.class, accessibilityReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(AccessibilityInstitutionFragment.class, accessibilityReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(AccessibilityVictimFragment.class, accessibilityReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(AccessibilityVictimInfoFragment.class, accessibilityReportViewModel, true));
        fragmentSteps.add(BaseReportFragment.newInstance(AccessibilityVictimGenderFragment.class, accessibilityReportViewModel, true));
        return fragmentSteps;
    }

    @Override
    protected void saveViolationReport() {
        SaveAccessibilityReportTask saveViolationReportTask = new SaveAccessibilityReportTask(this) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result != null) {
                    sendActionReport(AnalyticsAction.FinishViolationReport);
                    addProtocolFragment(result);
                } else {
                    Toast.makeText(AccessibilityReportActivity.this, R.string.message_report_error, Toast.LENGTH_SHORT).show();
                }
            }
        };
        saveViolationReportTask.execute(accessibilityReportViewModel);
    }

    private void addProtocolFragment(String protocol) {
        ProtocolFragment protocolFragment = ProtocolFragment.newInstance(protocol
                , getString(R.string.disk_100), getString(R.string.protocol_info, COUNCIL_NUMBER));
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(android.R.id.content, protocolFragment)
                .commit();
    }

    @Override
    void onGiveUpReport() {
        sendActionReport(AnalyticsAction.GiveUpNoAccessibilityReport);
    }
}

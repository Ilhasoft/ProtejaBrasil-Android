package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;

import java.lang.ref.WeakReference;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.managers.ReportManager;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;

/**
 * Created by dielsonsales on 11/03/16.
 */
public class SaveAccessibilityReportTask extends ProgressTask<AccessibilityReportViewModel, Void, String> {

    WeakReference<Context> contextWeakReference;

    public SaveAccessibilityReportTask(Context context) {
        super(context, R.string.message_wait_a_moment);
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(AccessibilityReportViewModel... params) {
        AccessibilityReportViewModel viewModel = params[0];
        if (contextWeakReference.get() != null) {
            try {
                String result = ReportManager.reportAccessibilityViolation(contextWeakReference.get(), viewModel);

                String requestResult = ReportFlowHelper.groupCharactersByPattern(ReportFlowHelper.RESULT_REGEX, result);
                return ReportFlowHelper.groupCharactersByPattern(ReportFlowHelper.DIGIT_REGEX, requestResult);
            } catch(Exception exception) {
                return null;
            }
        }
        return null;
    }
}

package br.com.ilhasoft.protejaBrasil.tasks;

import android.content.Context;
import android.util.Log;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.helpers.ReportFlowHelper;
import br.com.ilhasoft.protejaBrasil.managers.ReportManager;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class SaveViolationReportTask extends ProgressTask<ViolationReportViewModel, Void, String> {

    private static final String TAG = "SaveViolationReport";

    public SaveViolationReportTask(Context context) {
        super(context, R.string.load_message_report);
    }

    @Override
    protected String doInBackground(ViolationReportViewModel... params) {
        try {
            ViolationReportViewModel viewModel = params[0];
            String result = ReportManager.reportRightViolation(getContext(), viewModel);

            String requestResult = ReportFlowHelper.groupCharactersByPattern(ReportFlowHelper.RESULT_REGEX, result);
            return ReportFlowHelper.groupCharactersByPattern(ReportFlowHelper.DIGIT_REGEX, requestResult);
        } catch(Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "doInBackground ", exception);
        }
        return null;
    }
}

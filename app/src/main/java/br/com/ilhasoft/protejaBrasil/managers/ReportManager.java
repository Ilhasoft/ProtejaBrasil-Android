package br.com.ilhasoft.protejaBrasil.managers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import br.com.ilhasoft.protejaBrasil.helpers.CallHelper;
import br.com.ilhasoft.protejaBrasil.helpers.IOUtils;
import br.com.ilhasoft.protejaBrasil.helpers.ViolationReportConverter;
import br.com.ilhasoft.protejaBrasil.model.City;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.model.ViolationReport;
import br.com.ilhasoft.protejaBrasil.model.view.AccessibilityReportViewModel;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;
import br.com.ilhasoft.protejaBrasil.network.ReportServices;
import br.com.ilhasoft.support.databinding.bindings.BindableField;

/**
 * Created by john-mac on 1/7/16.
 */
public class ReportManager {

    private static final String TAG = "ReportManager";
    private static final String REPORT_CALL_PHONE = "100";
    private static final String WOMEN_REPORT_CALL_PHONE = "180";
    private static final String ASSET_REPORT_RIGHTS = "report.xml";
    private static final String ASSET_REPORT_ACCESSIBILITY = "accessibility_report.xml";

    public static void reportToDisk100(Fragment fragment) {
        CallHelper callHelper = new CallHelper(fragment);
        callHelper.callPhone(REPORT_CALL_PHONE);
    }

    public static void reportToDisk180(Fragment fragment) {
        CallHelper callHelper = new CallHelper(fragment);
        callHelper.callPhone(WOMEN_REPORT_CALL_PHONE);
    }

    @WorkerThread
    public static String reportRightViolation(Context context, ViolationReportViewModel violationReportViewModel)
            throws IOException {
        ViolationReportConverter converter = new ViolationReportConverter();
        ViolationReport violationReport = converter.violationReportFromViewModel(violationReportViewModel);
        String sondhaThemeId = getValueOrBlank(violationReport.getTheme().getSondhaId());

        String reportXml = IOUtils.loadFileFromAssets(context, ASSET_REPORT_RIGHTS);
        reportXml = String.format(reportXml
                , getViolationSondhaId(violationReport)
                , getValueOrBlank(violationReport.getTheme().getTitle())
                , getValueOrBlank(violationReport.getSubtypeId())
                , getValueOrBlank(violationReport.getSubtypeDescription())

                , getValueOrBlank(violationReport.getFrequency())

                , getValueOrBlank(violationReport.getVictimName())
                , getValueOrBlank(violationReport.getVictimLocation())
                , getValueOrBlank(violationReport.getVictimAddressState().getInitials())
                , getValueOrBlank(violationReport.getVictimAddressCity().getName())
                , getValueOrBlank(violationReport.getVictimAddressStreet())

                , getValueOrBlank(violationReport.getOffenderType())
                , getValueOrBlank(violationReport.getOffenderName())
                , getValueOrBlank(violationReport.getOffenderAddressState().getInitials())
                , getValueOrBlank(violationReport.getOffenderAddressCity().getName())
                , getValueOrBlank(violationReport.getOffenderAddressStreet())

                , getValueOrBlank(violationReport.getViolationDescription())
                , getValueOrBlank(violationReport.getViolationLocal())

                , getValueOrBlank(violationReport.getInstitutionActivated())
                , getValueOrBlank(violationReport.getVictimSex())
                , getValueOrBlank(violationReport.getVictimSexOption())
                , getValueOrBlank(violationReport.getVictimAgeGroup())
                , getValueOrBlank(violationReport.getVictimEthnicity())
                , getValueOrBlank(violationReport.getOffenderSex())
                , getValueOrBlank(violationReport.getOffenderSexOption())
                , getValueOrBlank(violationReport.getOffenderAgeGroup())
                , getValueOrBlank(violationReport.getOffenderEthnicity())

        );

        Log.i("ReportManager", "reportRightViolation: " + reportXml);

        ReportServices reportServices = new ReportServices(context);
        return reportServices.sendViolationReport(reportXml, sondhaThemeId).execute().body().string();
    }

    public static String reportAccessibilityViolation(Context context, AccessibilityReportViewModel violationReportViewModel)
            throws IOException {
        String reportXml = IOUtils.loadFileFromAssets(context, ASSET_REPORT_ACCESSIBILITY);

        State offenderState = (State) violationReportViewModel.offenderAddress.state.get();
        City offenderCity = (City) violationReportViewModel.offenderAddress.city.get();

        State victimState = (State) violationReportViewModel.victimAddress.state.get();
        City victimCity = (City) violationReportViewModel.victimAddress.city.get();

        reportXml = String.format(reportXml
                , getValueOrBlankForResponse(violationReportViewModel.offenderType)
                , getValueOrBlank(violationReportViewModel.offenderName.get())
                , getValueOrBlank(violationReportViewModel.offenderAddress.address.get())
                , getValueOrBlank(offenderState.getInitials())
                , getValueOrBlank(offenderCity.getCode())

                , getValueOrBlankForResponse(violationReportViewModel.violationType)
                , getValueOrBlankForResponse(violationReportViewModel.violation)
                , getValueOrBlank(violationReportViewModel.institutionActivated.get())
                , getValueOrBlank(violationReportViewModel.violationDescription.get())

                , getValueOrBlank(violationReportViewModel.victimName.get())
                , getValueOrBlank(victimState.getInitials())
                , getValueOrBlank(victimCity.getCode())
                , getValueOrBlank(violationReportViewModel.victimAddress.address.get())

                , getValueOrBlankForResponse(violationReportViewModel.victimAgeGroup)
                , getValueOrBlankForResponse(violationReportViewModel.victimEthnicity)
                , getValueOrBlankForResponse(violationReportViewModel.victimGender)
                , getValueOrBlankForResponse(violationReportViewModel.victimSexOption)
                , getValueOrBlank(violationReportViewModel.otherViolation.get())
        );

        ReportServices reportServices = new ReportServices(context);
        return reportServices.sendAccessibilityReport(reportXml).execute().body().string();
    }

    private static String getValueOrBlankForResponse(BindableField bindableField) {
        if (bindableField.exists()) {
            ResponseObject responseObject = (ResponseObject) bindableField.get();
            return getValueOrBlank(responseObject.getId());
        }
        return "";
    }

    private static String getValueOrBlank(Object object) {
        return object != null ?
                String.valueOf(object) : "";
    }

    private static int getViolationSondhaId(ViolationReport violationReport) {
        if (violationReport.getTheme().getSondhaId() != ViolationReport.VIOLATION_AGAINST_WOMEN_ID)
            return violationReport.getTheme().getSondhaId();

        return violationReport.getSubtypeId();
    }

}

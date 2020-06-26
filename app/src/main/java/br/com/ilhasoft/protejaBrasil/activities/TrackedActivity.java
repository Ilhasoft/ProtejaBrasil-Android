package br.com.ilhasoft.protejaBrasil.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import br.com.ilhasoft.protejaBrasil.Application;

/**
 * Created by dielsonsales on 10/03/16.
 */
public class TrackedActivity extends AppCompatActivity {

    private static String CATEGORY_VIOLATION = "Violation report";
    private static String CATEGORY_INTERNET_CRIME = "Internet crime report";
    private static String CATEGORY_NO_ACCESSIBILITY = "No accessibility report";
    private static String CATEGORY_DISQUE_100 = "Disque 100 report";
    private static String CATEGORY_DISQUE_180 = "Disque 180 report";
    private static String CATEGORY_FEEDBACK = "Feedback";
    private static String ACTION_START_REPORT = "Start report";
    private static String ACTION_FINISH_REPORT = "Finish report";
    private static String ACTION_GIVE_UP_REPORT = "Give up report";
    private static String ACTION_CALL = "Call";
    private static String ACTION_SEND_FEEDBACK = "Send feedback";
    private static int DEFAULT_REPORT_VALUE = 1;

    enum AnalyticsAction {
        StartViolationReport,
        FinishViolationReport,
        GiveUpViolationReport,
        StartInternetCrimeReport,
        FinishInternetCrimeReport,
        GiveUpInternetCrimeReport,
        StartNoAccessibilityReport,
        FinishNoAccessibilityReport,
        GiveUpNoAccessibilityReport,
        CallToDisque100,
        CallToDisque180,
        SendFeedback;

        @Override
        public String toString() {
            switch (this) {
                case StartViolationReport:        return "Start violation report";
                case FinishViolationReport:       return "Finish violation report";
                case GiveUpViolationReport:       return "Give up violation report";
                case StartInternetCrimeReport:    return "Start internet crime report";
                case FinishInternetCrimeReport:   return "Finish internet crime report";
                case GiveUpInternetCrimeReport:   return "Give up internet crime report";
                case StartNoAccessibilityReport:  return "Start no accessibility report";
                case FinishNoAccessibilityReport: return "Finish no accessibility report";
                case GiveUpNoAccessibilityReport: return "Give up no accessibility report";
                case CallToDisque100:             return "Call to Disque 100";
                case CallToDisque180:             return "Call to Disque 180";
                case SendFeedback:                return "Send feedback";
                default:                          throw new IllegalArgumentException();
            }
        }
    }

    protected void sendActionReport(AnalyticsAction action) {
        Tracker tracker = ((Application) getApplication()).getTracker();
        if (tracker != null) {
            HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder();
            switch (action) {
                case StartViolationReport:
                    builder.setCategory(CATEGORY_VIOLATION)
                            .setAction(ACTION_START_REPORT);
                    break;
                case FinishViolationReport:
                    builder.setCategory(CATEGORY_VIOLATION)
                            .setAction(ACTION_FINISH_REPORT);
                    break;
                case GiveUpViolationReport:
                    builder.setCategory(CATEGORY_VIOLATION)
                            .setAction(ACTION_GIVE_UP_REPORT);
                    break;
                case StartInternetCrimeReport:
                    builder.setCategory(CATEGORY_INTERNET_CRIME)
                            .setAction(ACTION_START_REPORT);
                    break;
                case FinishInternetCrimeReport:
                    builder.setCategory(CATEGORY_INTERNET_CRIME)
                            .setAction(ACTION_FINISH_REPORT);
                    break;
                case GiveUpInternetCrimeReport:
                    builder.setCategory(CATEGORY_INTERNET_CRIME)
                            .setAction(ACTION_GIVE_UP_REPORT);
                    break;
                case StartNoAccessibilityReport:
                    builder.setCategory(CATEGORY_NO_ACCESSIBILITY)
                            .setAction(ACTION_START_REPORT);
                    break;
                case FinishNoAccessibilityReport:
                    builder.setCategory(CATEGORY_NO_ACCESSIBILITY)
                            .setAction(ACTION_FINISH_REPORT);
                    break;
                case GiveUpNoAccessibilityReport:
                    builder.setCategory(CATEGORY_NO_ACCESSIBILITY)
                            .setAction(ACTION_GIVE_UP_REPORT);
                    break;
                case CallToDisque100:
                    builder.setCategory(CATEGORY_DISQUE_100)
                            .setAction(ACTION_CALL);
                    break;
                case CallToDisque180:
                    builder.setCategory(CATEGORY_DISQUE_180)
                            .setAction(ACTION_CALL);
                    break;
                case SendFeedback:
                    builder.setCategory(CATEGORY_FEEDBACK)
                            .setAction(ACTION_SEND_FEEDBACK);
            }
            builder.setLabel(action.toString());
            builder.setValue(DEFAULT_REPORT_VALUE);
            tracker.send(builder.build());
        }
    }

    protected void sendScreenViewReport(String analyticsScreenName) {
        final Tracker tracker = ((Application) getApplication()).getTracker();
        if (tracker != null) {
            tracker.setScreenName(analyticsScreenName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
}

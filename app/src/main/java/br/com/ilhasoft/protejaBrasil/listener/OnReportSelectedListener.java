package br.com.ilhasoft.protejaBrasil.listener;

import android.location.Location;
import android.os.Bundle;

/**
 * Created by johncordeiro on 18/10/15.
 */
public interface OnReportSelectedListener {

    void onReportAccessibility();
    void onReportInternetCrime(Location location);
    void onReportViolationSelected(Location location);
    void onConfirmAccessibilityReport(Bundle accessibilityResult);
    void onCallToDisk100();
    void onCallToDisk180();
}

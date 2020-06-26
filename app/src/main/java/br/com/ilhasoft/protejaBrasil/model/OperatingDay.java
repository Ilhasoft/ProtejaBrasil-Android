package br.com.ilhasoft.protejaBrasil.model;

import androidx.annotation.IntRange;

import java.util.Calendar;

/**
 * Created by johncordeiro on 22/10/15.
 */
public enum OperatingDay {

    sunday(Calendar.SUNDAY),
    monday(Calendar.MONDAY),
    tuesday(Calendar.TUESDAY),
    wednesday(Calendar.WEDNESDAY),
    thursday(Calendar.THURSDAY),
    friday(Calendar.FRIDAY),
    saturday(Calendar.SATURDAY);

    private int dayOfWeek;

    OperatingDay(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @IntRange(from = Calendar.SUNDAY, to = Calendar.SATURDAY)
    public static OperatingDay getByDayOfWeek(int dayOfWeek) {
        for (OperatingDay operatingDay : values()) {
            if(operatingDay.dayOfWeek == dayOfWeek) {
                return operatingDay;
            }
        }
        return sunday;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }
}

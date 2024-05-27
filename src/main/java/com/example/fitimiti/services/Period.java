package com.example.fitimiti.services;
import java.time.LocalDate;

public enum Period {
    ONE_DAY("1d", 1),
    LAST_WEEK("7d", 7),
    LAST_MONTH("1m", 30),
    LAST_3_MONTHS("3m", 90),
    LAST_YEAR("1y", 365),
    ALL_TIME("all", Integer.MAX_VALUE);

    private final String code;
    private final int days;

    Period(String code, int days) {
        this.code = code;
        this.days = days;
    }

    public static LocalDate getStartDateForPeriod(String periodCode) {
        for (Period period : Period.values()) {
            if (period.code.equals(periodCode)) {
                return LocalDate.now().minusDays(period.days);
            }
        }
        return LocalDate.now();
    }
}

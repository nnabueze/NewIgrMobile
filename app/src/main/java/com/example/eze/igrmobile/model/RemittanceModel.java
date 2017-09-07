package com.example.eze.igrmobile.model;

/**
 * Created by EZE on 9/7/2017.
 */

public class RemittanceModel {
    private String lastMonthRemite;
    private String currentMonthRemite;
    private String lastMonth;
    private String currentMonth;

    public String getLastMonthRemite() {
        return lastMonthRemite;
    }

    public void setLastMonthRemite(String lastMonthRemite) {
        this.lastMonthRemite = lastMonthRemite;
    }

    public String getCurrentMonthRemite() {
        return currentMonthRemite;
    }

    public void setCurrentMonthRemite(String currentMonthRemite) {
        this.currentMonthRemite = currentMonthRemite;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }
}

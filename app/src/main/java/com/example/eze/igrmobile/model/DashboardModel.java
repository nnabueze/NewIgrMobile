package com.example.eze.igrmobile.model;

/**
 * Created by EZE on 8/15/2017.
 */

public class DashboardModel {
    public String lastMonth;
    public String currentMonth;
    public String yestarday;
    public String today;
    public String billerName;

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

    public String getYestarday() {
        return yestarday;
    }

    public void setYestarday(String yestarday) {
        this.yestarday = yestarday;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }
}

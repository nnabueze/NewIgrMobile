package com.example.eze.igrmobile.model;

/**
 * Created by EZE on 8/16/2017.
 */

public class Auth {
    private String token;
    private String lastMonth;
    private String currentMonth;
    private String yestarday;
    private String today;
/*    private String id;
    private String image;*/
    private String name;
    private String billerId;
    private String lastMonthRemitted;
    private String currentMonthRemitted;
    private String yestardayRemitted;
    private String todayRemitted;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

/*    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getLastMonthRemitted() {
        return lastMonthRemitted;
    }

    public void setLastMonthRemitted(String lastMonthRemitted) {
        this.lastMonthRemitted = lastMonthRemitted;
    }

    public String getCurrentMonthRemitted() {
        return currentMonthRemitted;
    }

    public void setCurrentMonthRemitted(String currentMonthRemitted) {
        this.currentMonthRemitted = currentMonthRemitted;
    }

    public String getYestardayRemitted() {
        return yestardayRemitted;
    }

    public void setYestardayRemitted(String yestardayRemitted) {
        this.yestardayRemitted = yestardayRemitted;
    }

    public String getTodayRemitted() {
        return todayRemitted;
    }

    public void setTodayRemitted(String todayRemitted) {
        this.todayRemitted = todayRemitted;
    }
}

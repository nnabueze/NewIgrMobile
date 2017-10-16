package com.example.eze.igrmobile.model;

/**
 * Created by USER on 10/15/2017.
 */

public class RemittanceListModel {
    private String id;
    private String amount;
    private String remittedate;
    private String name;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemittedate() {
        return remittedate;
    }

    public void setRemittedate(String remittedate) {
        this.remittedate = remittedate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

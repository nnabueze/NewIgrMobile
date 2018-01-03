package com.ercasng.eze.igrmobile.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EZE on 8/21/2017.
 */

public class Mda {
    private String name;
    private String amount;

    public Mda(String name, String amount){
        this.name = name;
        this.amount = amount;
    }

    public Mda(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public static List<Mda> prepareMdaData(){
        List<Mda> mdaList =new ArrayList<>();
        Mda mda = new Mda("Internal Revenue", "100");
        mdaList.add(mda);

        mda = new Mda("Internal Revenue1", "100");
        mdaList.add(mda);

        mda = new Mda("Internal Revenue2", "100");
        mdaList.add(mda);

        mda = new Mda("Internal Revenue3", "100");
        mdaList.add(mda);

        mda = new Mda("Internal Revenue4", "100");
        mdaList.add(mda);

        return mdaList;
    }
}

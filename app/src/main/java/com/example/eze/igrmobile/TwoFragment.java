package com.example.eze.igrmobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by USER on 10/11/2017.
 */

public class TwoFragment extends Fragment {
    TextView mTextView, currentMonth, yestarday, today;
    public TwoFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        mTextView =(TextView) v.findViewById(R.id.lastMonth);
        currentMonth =(TextView) v.findViewById(R.id.currentMonth);
        yestarday = (TextView) v.findViewById(R.id.yestarday);
        today =(TextView) v.findViewById(R.id.today);
        setTextParam();
        return v;
    }

    public void setTextParam(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mTextView.setText(numberFormat(preferences.getString("lastMonth",null).toString()));
        currentMonth.setText(numberFormat(preferences.getString("currentMonth",null).toString()));
        yestarday.setText(numberFormat(preferences.getString("yestarday",null).toString()));
        today.setText(numberFormat(preferences.getString("today",null).toString()));

    }

    private String numberFormat(String number){
        double num = Double.parseDouble(number);
        DecimalFormat money = new DecimalFormat("###,###,###,###");
        String formattedText = "₦" + money.format(num);

        return formattedText;
    }
}

package com.ercasng.eze.igrmobile;

import android.content.SharedPreferences;
import android.graphics.Color;
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
    TextView mTextView, currentMonth, yestarday, today, indictator, indicator2, indicator3;
    SharedPreferences preferences;
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
        indictator = (TextView) v.findViewById(R.id.indicator);
        indicator2 = (TextView) v.findViewById(R.id.indicator2);
        indicator3 = (TextView) v.findViewById(R.id.indicator3);
        setTextParam();
        return v;
    }

    public void setTextParam(){
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mTextView.setText(numberFormat(preferences.getString("last_month_remitted",null).toString()));
        currentMonth.setText(numberFormat(preferences.getString("current_month_remitted",null).toString()));
        yestarday.setText(numberFormat(preferences.getString("yestarday_remitted",null).toString()));
        today.setText(numberFormat(preferences.getString("today_remitted",null).toString()));

        currentIndicator();
        todayIndicator();
        yesterdayIndicator();
    }

    private void yesterdayIndicator() {
        double yesterdayAmount = Double.parseDouble(preferences.getString("yestarday_remitted",null));
        double currentAmount = Double.parseDouble(preferences.getString("current_month_remitted",null));

        if (yesterdayAmount > currentAmount){
            //Increase = New Number - Original Number
            double increase = yesterdayAmount - currentAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageIncrease = increase / currentAmount* 100;

            indicator3.setText(String.format("%.2f", percentageIncrease)+"%");
            indicator3.setVisibility(View.VISIBLE);
            indicator3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_18dp, 0, 0, 0);
            indicator3.setTextColor(Color.parseColor("#2E7D32"));
        }

        if (currentAmount > yesterdayAmount){
            //Increase = New Number - Original Number
            double decrease = yesterdayAmount - currentAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageDecrease = decrease / currentAmount * 100;

            indicator3.setText(String.format("%.2f", percentageDecrease)+"%");
            indicator3.setVisibility(View.VISIBLE);
            indicator3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_18dp, 0, 0, 0);
            indicator3.setTextColor(Color.parseColor("#C62828"));
        }
    }

    private void todayIndicator() {
        double yesterdayAmount = Double.parseDouble(preferences.getString("yestarday_remitted",null));
        double todayAmount = Double.parseDouble(preferences.getString("today_remitted",null));

        if (todayAmount > yesterdayAmount){
            //Increase = New Number - Original Number
            double increase = todayAmount - yesterdayAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageIncrease = increase / yesterdayAmount* 100;

            indicator2.setText(String.format("%.2f", percentageIncrease)+"%");
            indicator2.setVisibility(View.VISIBLE);
            indicator2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_18dp, 0, 0, 0);
            indicator2.setTextColor(Color.parseColor("#2E7D32"));
        }

        if (yesterdayAmount > todayAmount){
            //Increase = New Number - Original Number
            double decrease = todayAmount - yesterdayAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageDecrease = decrease / yesterdayAmount * 100;

            indicator2.setText(String.format("%.2f", percentageDecrease)+"%");
            indicator2.setVisibility(View.VISIBLE);
            indicator2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_18dp, 0, 0, 0);
            indicator2.setTextColor(Color.parseColor("#C62828"));
        }
    }

    private void currentIndicator() {
        //checking increase
        double lastAmount = Double.parseDouble(preferences.getString("last_month_remitted",null));
        double currentAmount = Double.parseDouble(preferences.getString("current_month_remitted",null));
        if (currentAmount > lastAmount){
            //Increase = New Number - Original Number
            double increase = currentAmount - lastAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageIncrease = increase / lastAmount* 100;

            indictator.setText(String.format("%.2f", percentageIncrease)+"%");
            indictator.setVisibility(View.VISIBLE);
            indictator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_18dp, 0, 0, 0);
            indictator.setTextColor(Color.parseColor("#2E7D32"));
        }

        if (lastAmount > currentAmount){
            //Increase = New Number - Original Number
            double decrease = currentAmount - lastAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageDecrease = decrease / lastAmount * 100;

            indictator.setText(String.format("%.2f", percentageDecrease)+"%");
            indictator.setVisibility(View.VISIBLE);
            indictator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_18dp, 0, 0, 0);
            indictator.setTextColor(Color.parseColor("#C62828"));
        }
    }

    private String numberFormat(String number){
        double num = Double.parseDouble(number);
        DecimalFormat money = new DecimalFormat("###,###,###,###");
        String formattedText = "₦" + money.format(num);

        return formattedText;
    }
}

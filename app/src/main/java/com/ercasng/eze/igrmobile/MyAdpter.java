package com.ercasng.eze.igrmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ercasng.eze.igrmobile.AnimationDir.MyAnimation;
import com.ercasng.eze.igrmobile.model.Mda;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by EZE on 8/21/2017.
 */

public class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewHolder> {
    private List<Mda> mdaList;
    private LayoutInflater inflater;

    public MyAdpter(Context context, List<Mda> objectlist) {
        this.mdaList = objectlist;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mdaList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mda mda = mdaList.get(position);
//        holder.title1.setText(mda.getName());
//        holder.title2.setText(mda.getAmount());
        holder.setData(mda, position);
        MyAnimation.animateHome(holder);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tab1;
        private TextView tab2;
        private int position;
        private Mda mda;

        public MyViewHolder(View itemView) {
            super(itemView);
            tab1 = (TextView) itemView.findViewById(R.id.text1);
            tab2 = (TextView) itemView.findViewById(R.id.text2);
        }

        public void setData(Mda mda, int position) {
            this.tab1.setText(mda.getName());
            this.tab2.setText(numberFormat(mda.getAmount()));
            this.position = position;
            this.mda = mda;
        }

        private String numberFormat(String number){
            double num = Double.parseDouble(number);
            DecimalFormat money = new DecimalFormat("###,###,###,###");
            String formattedText = "₦" + money.format(num);

            return formattedText;
        }
    }
}

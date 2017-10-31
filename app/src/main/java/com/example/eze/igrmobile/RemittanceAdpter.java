package com.example.eze.igrmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eze.igrmobile.model.RemittanceListModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by USER on 10/31/2017.
 */

public class RemittanceAdpter extends RecyclerView.Adapter<RemittanceAdpter.MyViewHolder>  {
    private List<RemittanceListModel> remittanceListModels;
    private LayoutInflater inflater;

    public RemittanceAdpter(Context context, List<RemittanceListModel> objectlist) {
        this.remittanceListModels = objectlist;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_remittance, parent, false);
        RemittanceAdpter.MyViewHolder holder = new RemittanceAdpter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RemittanceListModel listModel = remittanceListModels.get(position);
        holder.setData(listModel, position);
    }

    @Override
    public int getItemCount() {
        return remittanceListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tab1, tab2, tab3, tab4;

        public MyViewHolder(View itemView) {
            super(itemView);
            tab1 = (TextView) itemView.findViewById(R.id.remittance_id);
            tab2 = (TextView) itemView.findViewById(R.id.remittance_name);
            tab3 = (TextView) itemView.findViewById(R.id.remittance_amount);
            tab4 = (TextView) itemView.findViewById(R.id.remittance_status);
        }
        public void setData(RemittanceListModel listModel, int position) {
            this.tab1.setText(listModel.getId());
            this.tab2.setText(listModel.getName());
            this.tab3.setText(listModel.getAmount());
            this.tab4.setText(listModel.getStatus());
        }

        private String numberFormat(String number){
            double num = Double.parseDouble(number);
            DecimalFormat money = new DecimalFormat("###,###,###,###");
            String formattedText = "₦" + money.format(num);

            return formattedText;
        }
    }
}

package com.example.eze.igrmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eze.igrmobile.model.Mda;

import java.util.List;

/**
 * Created by EZE on 8/21/2017.
 */

public class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewHolder> {
    private List<Mda> objectlist;
    private LayoutInflater inflater;

    public MyAdpter(Context context, List<Mda> objectlist) {
        this.objectlist = objectlist;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mda current = objectlist.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return objectlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title1, title2;
        private int position;
        private Mda current;

        public MyViewHolder(View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.text1);
            title2 = (TextView) itemView.findViewById(R.id.text2);
        }

        public void setData(Mda current, int position) {
            this.title1.setText(current.getName());
            this.title2.setText(current.getAmount());
            this.current = current;
            this.position = position;
        }
    }
}

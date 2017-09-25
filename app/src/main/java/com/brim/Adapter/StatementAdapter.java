package com.brim.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.FotterFragmnet.Activity;
import com.brim.Pojo.StateSetGet;
import com.brim.R;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.GetDate;

import java.util.ArrayList;

/**
 * Created by su on 25/9/17.
 */

public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.ViewHolder> {
    Context context;
    ArrayList<StateSetGet> list;
    Activity activity;
    int total_pages;


    public StatementAdapter(Context context, ArrayList<StateSetGet> stateList, Activity activity,int total_pages) {

        this.context=context;
        list=stateList;
        this.activity=activity;
        this.total_pages=total_pages;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.statement_adapter_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txt_date.setText(DateFormatConvertion.yyyymmdd_to_mmdd(list.get(position).getFrom_date())+" - "+GetDate.getNextMonth(list.get(position).getFrom_date()));

        holder.txt_bal.setText("$"+list.get(position).getBalance());
        holder.txt_due_date.setText(DateFormatConvertion.yyyymmdd_to_mmmddyyyy(list.get(position).getDue_date()));
        holder.txt_mini_due.setText("$"+list.get(position).getMinimum_payment());

        if (activity.page_no<total_pages){

            activity.page_no=activity.page_no+1;

            activity.getStatement();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_bal,txt_due_date,txt_mini_due,txt_date;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_date=(TextView) itemView.findViewById(R.id.txt_date);
            txt_bal=(TextView) itemView.findViewById(R.id.txt_bal);
            txt_due_date=(TextView) itemView.findViewById(R.id.txt_due_date);
            txt_mini_due=(TextView) itemView.findViewById(R.id.txt_mini_due);
        }
    }

    public void update(ArrayList<StateSetGet> uplist){

        list.addAll(uplist);
    }
}

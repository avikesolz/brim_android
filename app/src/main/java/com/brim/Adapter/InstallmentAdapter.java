package com.brim.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brim.Pojo.InstallmentSetGet;
import com.brim.R;
import com.brim.SetUpInstallments;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by su on 12/9/17.
 */

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.ViewHolder> {

    ArrayList<InstallmentSetGet> list;
    Context mContext;
    SetUpInstallments setUpInstallments;

    int posi=-1;

    public InstallmentAdapter(SetUpInstallments setUpInstallments, ArrayList<InstallmentSetGet> list) {

        this.list=list;
        mContext=setUpInstallments;
        this.setUpInstallments=setUpInstallments;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.installment_adapter_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txt_plan.setText(""+list.get(position).getNum_month()+" months");
        holder.txt_rate.setText("Interest: "+list.get(position).getRate()+"%");
        holder.txt_amount.setText("$"+list.get(position).getAmount()+" / months");

        if (posi==position){

            holder.check.setImageResource(R.drawable.selected_oval);

        }else{
            holder.check.setImageResource(R.drawable.oval);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpInstallments.plan=list.get(position).getPlan();
                setUpInstallments.posi=position;
                posi=position;
                notifyDataSetChanged();

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_plan,txt_rate,txt_amount,include_rate;
        ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_plan=(TextView)itemView.findViewById(R.id.txt_plan);
            txt_rate=(TextView)itemView.findViewById(R.id.txt_rate);
            txt_amount=(TextView)itemView.findViewById(R.id.txt_amount);
            include_rate=(TextView)itemView.findViewById(R.id.include_rate);

            check=(ImageView) itemView.findViewById(R.id.check);
        }
    }
}

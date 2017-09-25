package com.brim.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brim.Font.AxiformaBook;
import com.brim.Font.AxiformaRegular;
import com.brim.Installment;
import com.brim.Pojo.InstallSetGet;
import com.brim.R;
import com.brim.Utils.ConvertLocal;
import com.brim.Utils.DateDifference;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.GetDate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 14/9/17.
 */

public class InstallmentTransAdapter extends RecyclerView.Adapter<InstallmentTransAdapter.ViewHolder> {

    Context mContext;
    ArrayList<InstallSetGet> list;


    public InstallmentTransAdapter(Installment installment, ArrayList<InstallSetGet> list) {
        mContext=installment;
        this.list=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(list.get(position).getIamge()!=null )
            Picasso.with(mContext)
                    .load(list.get(position).getIamge())
                    .into(holder.icon);

        holder.TXT_Name.setText(list.get(position).getName());
        holder.TXT_Type.setText(list.get(position).getCate_name());

        holder.TXT_PRICE1.setText("$"+ ConvertLocal.priceWithDecimal(Double.parseDouble(list.get(position).getAmount())));


    /*    try {
            if(list.get(position).getTransaction().getJSONObject("forex").getDouble("foreign_amount")>0)
                holder.LL_R_1.setVisibility(View.VISIBLE);
            else
                holder.LL_R_1.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        String diff=DateDifference.dateDifference(list.get(position).getDate(), GetDate.getDate());

        if (diff.equals("0"))
            holder.TXT_Status.setText("Today");
        else if(diff.equals("-1"))
            holder.TXT_Status.setText("Yesterday");
        else
            holder.TXT_Status.setText(DateFormatConvertion.yyyymmdd_to_mmmddyyyy(list.get(position).getDate()));





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon,LL_R_1;
        AxiformaBook TXT_Name,TXT_PRICE1;
        AxiformaRegular TXT_Type,TXT_Status;

        public ViewHolder(View itemView) {
            super(itemView);
            icon=(ImageView)itemView.findViewById(R.id.IMG1);
            LL_R_1=(ImageView)itemView.findViewById(R.id.LL_R_1);
            LL_R_1.setVisibility(View.GONE);
            TXT_Name=(AxiformaBook)itemView.findViewById(R.id.TXT_Name);
            TXT_PRICE1=(AxiformaBook)itemView.findViewById(R.id.TXT_PRICE1);
            TXT_Type=(AxiformaRegular)itemView.findViewById(R.id.TXT_Type);
            TXT_Status=(AxiformaRegular)itemView.findViewById(R.id.TXT_Status);

        }
    }
}

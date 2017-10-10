package com.brim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.Pojo.TransactionListData;
import com.brim.R;
import com.brim.Redeem;
import com.brim.RedemptionOffer;
import com.brim.Utils.DateDifference;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.GetDate;

import org.json.JSONException;

import java.util.LinkedList;

/**
 * Created by su on 15/9/17.
 */

public class RedemptionAdapter extends RecyclerView.Adapter<RedemptionAdapter.ViewHolder> {

    LinkedList<TransactionListData> List;
    Context mContext;
    RedemptionOffer redemptionOffer;
    int multiplier;
    String name;
    float rewards_bal;
    String redmp_Id;


    public RedemptionAdapter(Context baseContext, LinkedList<TransactionListData> recentTransactionList, RedemptionOffer redemptionOffer,int multiplier,String name,float rewards_bal,String redmp_Id) {

        mContext=baseContext;
        List=recentTransactionList;
        this.redemptionOffer=redemptionOffer;
        this.multiplier=multiplier;
        this.name=name;
        this.rewards_bal=rewards_bal;
        this.redmp_Id=redmp_Id;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.redemption_adaper_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.TXT_amount.setText("$ "+List.get(position).getItemObject().getString("amount"));
            holder.TXT_Pts.setText(List.get(position).getItemObject().getString("points")+" pts");


            String diff= DateDifference.dateDifference(List.get(position).getItemObject().getString("transaction_date"), GetDate.getDate());

            if (diff.equals("0"))
                holder.TXT_date.setText("Today");
            else if(diff.equals("-1"))
                holder.TXT_date.setText("Yesterday");
            else {
                String date=DateFormatConvertion.yyyymmdd_to_mmdd(List.get(position).getItemObject().getString("transaction_date"));
                holder.TXT_date.setText(date);
            }

            if(List.get(position).getItemObject().getBoolean("eligible_for_redemption")) {

                if (List.get(position).getItemObject().getBoolean("redemption_flag")) {
                    holder.button_redeem.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.button_redeem.setText("Redeemed");

                } else {
                    holder.button_redeem.setText("Redeem");
                    holder.button_redeem.setBackgroundResource(R.drawable.mold_rectangle_blue_white);
                }
            }else{
                holder.button_redeem.setVisibility(View.GONE);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(List.get(position).getItemObject().getBoolean("redemption_flag")==false) {

                        Intent intent=new Intent(mContext, Redeem.class);
                        intent.putExtra("transaction_id",List.get(position).getItemObject().getString("id"));
                        intent.putExtra("transaction_date",List.get(position).getItemObject().getString("transaction_date"));
                        intent.putExtra("points",List.get(position).getItemObject().getInt("points"));
                        intent.putExtra("amount",List.get(position).getItemObject().getInt("amount"));
                        intent.putExtra("cate_name",List.get(position).getItemObject().getJSONObject("category").getString("name"));
                        intent.putExtra("name",name);
                        intent.putExtra("multiplier",multiplier);
                        intent.putExtra("rewards_bal",rewards_bal);
                        intent.putExtra("redmp_Id",redmp_Id);
                        mContext.startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        if(List.size()-1==position) {

            if (redemptionOffer != null) {

                redemptionOffer.page_no++;
                redemptionOffer.getTransactions();

            }
        }
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TXT_amount,TXT_date,TXT_Pts,button_redeem;

        public ViewHolder(View itemView) {
            super(itemView);

            TXT_amount=(TextView)itemView.findViewById(R.id.TXT_amount);
            TXT_date=(TextView)itemView.findViewById(R.id.TXT_date);
            TXT_Pts=(TextView)itemView.findViewById(R.id.TXT_Pts);
            button_redeem=(TextView)itemView.findViewById(R.id.button_redeem);
        }
    }

    public void updateList(LinkedList<TransactionListData> upListData ){

        List.addAll(upListData);
    }
}

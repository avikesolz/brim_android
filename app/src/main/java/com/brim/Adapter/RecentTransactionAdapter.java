package com.brim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brim.Font.AxiformaBook;
import com.brim.Font.AxiformaRegular;
import com.brim.FotterFragmnet.Activity;
import com.brim.FotterFragmnet.DashBoard;
import com.brim.Installment;
import com.brim.Pojo.TransactionListData;
import com.brim.R;
import com.brim.Utils.ConvertLocal;
import com.brim.TransactionDetails;
import com.brim.Utils.DateDifference;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.GetDate;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.LinkedList;

/**
 * Created by apple on 22/08/17.
 */

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecentTransactionAdapter.RCViewHolder> {

    Context mContext;
    public LinkedList<TransactionListData> ListData;
    DashBoard dashBoard;
    Activity activity;

    public RecentTransactionAdapter(Context activity, LinkedList<TransactionListData> recentTransactionList,DashBoard dashBoard ) {
        this.mContext=activity;
        this.ListData=recentTransactionList;
        this.dashBoard=dashBoard;
    }

    public RecentTransactionAdapter(FragmentActivity activity, LinkedList<TransactionListData> recentTransactionList, Activity activity1) {

        this.mContext=activity;
        this.ListData=recentTransactionList;
        this.activity=activity1;
    }




    @Override
    public RecentTransactionAdapter.RCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new RCViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentTransactionAdapter.RCViewHolder holder, int position) {
        final TransactionListData item=ListData.get(position);

        if(item.getImage()!=null )
        Picasso.with(mContext)
                .load(item.getImage())
                .into(holder.icon);

        try {
            holder.TXT_Name.setText(item.getItemObject().getString("description"));

            if (item.getImage()!=null)
            holder.TXT_Type.setText(item.getItemObject().getJSONObject("category").getString("name"));

            if(dashBoard!=null) {

                holder.TXT_PRICE1.setText("$"+ ConvertLocal.priceWithDecimal(Double.parseDouble(item.getItemObject().getString("amount"))));

            }else if(activity!=null) {

                if (activity.tab_status.equals("TRANS")) {
                    holder.TXT_PRICE1.setText("$"+ ConvertLocal.priceWithDecimal(Double.parseDouble(item.getItemObject().getString("amount"))));

                } else if (activity.tab_status.equals("INSTALL")) {

                    if (item.getPrice()!=null)
                    holder.TXT_PRICE1.setText("$"+ ConvertLocal.priceWithDecimal(Double.parseDouble(item.getPrice())));

                }
            }


       /*     if(item.getItemObject().getString("status").equals("posted"))
            {
                if(item.getItemObject().getString("transaction_date").equals(item.getItemObject().getString("posted_date")))
                    holder.TXT_Status.setText("Today");
                else
                    holder.TXT_Status.setText(item.getItemObject().getString("posted_date"));

            }else
            holder.TXT_Status.setText(item.getItemObject().getString("status"));*/


            String diff= DateDifference.dateDifference(item.getItemObject().getString("transaction_date"), GetDate.getDate());

            if (diff.equals("0"))
                holder.TXT_Status.setText("Today");
            else if(diff.equals("-1"))
                holder.TXT_Status.setText("Yesterday");
            else
                holder.TXT_Status.setText(DateFormatConvertion.yyyymmdd_to_mmmddyyyy(item.getItemObject().getString("transaction_date")));


            if(item.getItemObject().getJSONObject("forex").getDouble("foreign_amount")>0)
                holder.LL_R_1.setVisibility(View.VISIBLE);
            else
                holder.LL_R_1.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(ListData.size()-1==position){

            if(dashBoard!=null) {

                dashBoard.page_no++;
                dashBoard.CallToAddView();

            }else if(activity!=null) {

                if (activity.tab_status.equals("TRANS")) {
                    activity.page_no++;
                    activity.getTransactions();
                } else if (activity.tab_status.equals("INSTALL")) {
                    activity.page_no++;
                    activity.getInstallments();
                }
            }

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=null;

                if(dashBoard!=null) {

                    intent=new Intent(mContext, TransactionDetails.class);
                    try {
                        intent.putExtra("description",item.getItemObject().getString("description"));
                        intent.putExtra("transaction_id",item.getItemObject().getString("id"));
                        intent.putExtra("transaction_date",item.getItemObject().getString("transaction_date"));
                        intent.putExtra("posted_date",item.getItemObject().getString("posted_date"));
                        intent.putExtra("amount",item.getItemObject().getString("amount"));
                        intent.putExtra("points",item.getItemObject().getString("points"));
                        intent.putExtra("category_image",item.getImage());
                        intent.putExtra("category_name",item.getItemObject().getJSONObject("category").getString("name"));

                        intent.putExtra("eligible_installment_status",item.getItemObject().getBoolean("eligible_for_installment_pay"));
                        intent.putExtra("installment_status",item.getItemObject().getBoolean("installment_flag"));

                        intent.putExtra("foreign_currency",item.getItemObject().getJSONObject("forex").getString("foreign_currency"));
                        intent.putExtra("foreign_amount",item.getItemObject().getJSONObject("forex").getLong("foreign_amount"));
                        intent.putExtra("conversion_rate",item.getItemObject().getJSONObject("forex").getLong("conversion_rate"));
                        intent.putExtra("savings",item.getItemObject().getJSONObject("forex").getLong("savings"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(activity!=null) {

                    if (activity.tab_status.equals("TRANS")) {

                        intent=new Intent(mContext, TransactionDetails.class);
                        try {
                            intent.putExtra("description",item.getItemObject().getString("description"));
                            intent.putExtra("transaction_id",item.getItemObject().getString("id"));
                            intent.putExtra("transaction_date",item.getItemObject().getString("transaction_date"));
                            intent.putExtra("posted_date",item.getItemObject().getString("posted_date"));
                            intent.putExtra("amount",item.getItemObject().getString("amount"));
                            intent.putExtra("points",item.getItemObject().getString("points"));
                            intent.putExtra("category_image",item.getImage());
                            intent.putExtra("category_name",item.getItemObject().getJSONObject("category").getString("name"));

                            intent.putExtra("eligible_installment_status",item.getItemObject().getBoolean("eligible_for_installment_pay"));
                            intent.putExtra("installment_status",item.getItemObject().getBoolean("installment_flag"));

                            intent.putExtra("foreign_currency",item.getItemObject().getJSONObject("forex").getString("foreign_currency"));
                            intent.putExtra("foreign_amount",item.getItemObject().getJSONObject("forex").getLong("foreign_amount"));
                            intent.putExtra("conversion_rate",item.getItemObject().getJSONObject("forex").getLong("conversion_rate"));
                            intent.putExtra("savings",item.getItemObject().getJSONObject("forex").getLong("savings"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (activity.tab_status.equals("INSTALL")) {

                        intent=new Intent(mContext, Installment.class);

                        intent.putExtra("id",item.getId());
                        intent.putExtra("amount",item.getPrice());
                        intent.putExtra("total_number_of_payments",item.getTotal_number_of_payments());
                        intent.putExtra("number_of_payments_made",item.getNumber_of_payments_made());
                        intent.putExtra("remaining_balance",item.getRemaining_balance());
                        intent.putExtra("category_image",item.getImage());


                        try {

                            intent.putExtra("description",item.getItemObject().getString("description"));
                            intent.putExtra("foreign_currency",item.getItemObject().getJSONObject("forex").getString("foreign_currency"));
                            intent.putExtra("foreign_amount",item.getItemObject().getJSONObject("forex").getLong("foreign_amount"));
                            intent.putExtra("conversion_rate",item.getItemObject().getJSONObject("forex").getLong("conversion_rate"));
                            intent.putExtra("savings",item.getItemObject().getJSONObject("forex").getLong("savings"));
                            intent.putExtra("category_name",item.getItemObject().getJSONObject("category").getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }

                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class RCViewHolder extends RecyclerView.ViewHolder {
        ImageView icon,LL_R_1;
        AxiformaBook TXT_Name,TXT_PRICE1;
        AxiformaRegular TXT_Type,TXT_Status;
        public RCViewHolder(View itemView) {
            super(itemView);
            icon=(ImageView)itemView.findViewById(R.id.IMG1);
            LL_R_1=(ImageView)itemView.findViewById(R.id.LL_R_1);
            TXT_Name=(AxiformaBook)itemView.findViewById(R.id.TXT_Name);
            TXT_PRICE1=(AxiformaBook)itemView.findViewById(R.id.TXT_PRICE1);
            TXT_Type=(AxiformaRegular)itemView.findViewById(R.id.TXT_Type);
            TXT_Status=(AxiformaRegular)itemView.findViewById(R.id.TXT_Status);

        }
    }

    public void updateList(LinkedList<TransactionListData> upListData ){

        ListData.addAll(upListData);
    }
}

package com.brim.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.Pojo.TransactionListData;
import com.brim.R;
import com.brim.Utils.DateFormatConvertion;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by ritam on 19/09/17.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {


    LinkedList<TransactionListData> RecentTransactionList;
    Context context;

    public TransactionAdapter(LinkedList<TransactionListData> recentTransactionList, Context context) {
        RecentTransactionList = recentTransactionList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        try {
            holder.Amount.setText("$" + RecentTransactionList.get(position).getItemObject().getString("amount"));
            holder.Points.setText(RecentTransactionList.get(position).getItemObject().getString("points") + " pts");
            holder.TypeAndDate.setText(RecentTransactionList.get(position).getItemObject().getJSONObject("category").getString("name") + "," +
                    DateFormatConvertion.yyyymmdd_to_mmdd(RecentTransactionList.get(position).getItemObject().getJSONObject("category").getString("transaction_date")));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return RecentTransactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Amount, TypeAndDate, Points;

        public ViewHolder(View itemView) {
            super(itemView);

            Amount = (TextView) itemView.findViewById(R.id.tv_amount);
            TypeAndDate = (TextView) itemView.findViewById(R.id.tv_typeanddate);
            Points = (TextView) itemView.findViewById(R.id.tv_points);

        }
    }

    public void updateList(LinkedList<TransactionListData> upListData) {

        RecentTransactionList.addAll(upListData);
    }

}

package com.brim.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brim.AppContant.AppConstant;
import com.brim.FotterFragmnet.Budget;
import com.brim.Pojo.BudgetSetGet;
import com.brim.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by su on 19/9/17.
 */

public class MyBudgetAdapter extends RecyclerView.Adapter<MyBudgetAdapter.ViewHolder> {

    Context context;
    ArrayList<BudgetSetGet> list=null;

    public MyBudgetAdapter(Context context, ArrayList<BudgetSetGet> budgetList) {

        this.context=context;
        this.list=budgetList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_adapter_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        try {
            Picasso.with(context)
                    .load(AppConstant.IMAGE_URL+list.get(position).getCategory().getString("id")+ ".png")
                    .into(holder.image);


            holder.determinateBar.getProgressDrawable().setColorFilter(Color.parseColor("#"+list.get(position).getCategory().getString("color")), PorterDuff.Mode.SRC_IN);

            holder.determinateBar.setMax(list.get(position).getMax_amount());

            holder.determinateBar.setProgress(list.get(position).getTotal_amount());

            holder.txt_name.setText(list.get(position).getCategory().getString("name")+": $"
                    +list.get(position).getMax_amount());

            holder.txt_outof.setText("$"+list.get(position).getTotal_amount()+" out of $"
                    +list.get(position).getMax_amount());

            if(list.get(position).getMax_amount()>list.get(position).getTotal_amount())
                holder.txt_left.setText("$"+(list.get(position).getMax_amount()-list.get(position).getTotal_amount())+" left");
            else
                holder.txt_left.setText("$"+(list.get(position).getTotal_amount()-list.get(position).getMax_amount())+" over");



        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar determinateBar;
        TextView txt_outof,txt_left,txt_name;
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);

            determinateBar=(ProgressBar) itemView.findViewById(R.id.determinateBar);

            image=(ImageView) itemView.findViewById(R.id.image);
            txt_name=(TextView) itemView.findViewById(R.id.txt_name);
            txt_outof=(TextView) itemView.findViewById(R.id.txt_outof);
            txt_left=(TextView) itemView.findViewById(R.id.txt_left);
        }
    }
}

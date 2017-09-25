package com.brim.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brim.Pojo.SpendSetGet;
import com.brim.R;
import com.brim.ViewHelper.ColorCircleDrawable;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by su on 20/9/17.
 */

public class MySpendAdapter extends RecyclerView.Adapter<MySpendAdapter.ViewHolder> {

    Context context;
    ArrayList<SpendSetGet> List=null;

    public MySpendAdapter(Context context, ArrayList<SpendSetGet> spendList) {

        this.context=context;
        this.List=spendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.spend_adapter_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {

            //Log.d("percentange","::::"+List.get(position).getPercent());
            holder.color_dot.setBackground(new ColorCircleDrawable(Color.parseColor("#"+List.get(position).getCategory().getString("color"))));
            holder.txt_name.setText(List.get(position).getCategory().getString("name")+" ("+List.get(position).getPercent()+"%)");
            holder.txt_amount.setText("$"+List.get(position).getTotal_amount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView color_dot;
        TextView txt_name,txt_amount;

        public ViewHolder(View itemView) {
            super(itemView);
            color_dot=(ImageView) itemView.findViewById(R.id.color_dot);
            txt_name=(TextView) itemView.findViewById(R.id.txt_name);
            txt_amount=(TextView) itemView.findViewById(R.id.txt_amount);
        }
    }
}

package com.brim.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.FotterFragmnet.Reward;
import com.brim.Pojo.HistorySetGet;
import com.brim.R;
import com.brim.Utils.DateFormatConvertion;

import java.util.ArrayList;

/**
 * Created by su on 14/9/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    Context context;
    ArrayList<HistorySetGet> hisList;
    Reward reward;


    public HistoryAdapter(Context context, ArrayList<HistorySetGet> hisList, Reward reward) {

        this.context=context;
        this.hisList=hisList;
        this.reward=reward;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txt_name.setText(hisList.get(position).getDescription()+" ($"+hisList.get(position).getAmount()+")");
        holder.txt_bal.setText(hisList.get(position).getPoints());
        holder.txt_date.setText(hisList.get(position).getTransaction_type()+"-"
                + DateFormatConvertion.yyyymmdd_to_mmdd(hisList.get(position).getTransaction_date()));

        if(hisList.get(position).getTransaction_type().equals("redeem")){
            holder.txt_pts.setText("Saved "+hisList.get(position).getRedeem_points_saved()+" pts");

        }else{
            holder.txt_pts.setText("Earn "+hisList.get(position).getEarn_multiplier()+" pts");

        }

        if(position==hisList.size()-1){
            reward.page_no=reward.page_no+1;
            reward.getHistory();
        }

    }

    @Override
    public int getItemCount() {
        return hisList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_bal,txt_date,txt_pts;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_name=(TextView) itemView.findViewById(R.id.txt_name);
            txt_bal=(TextView) itemView.findViewById(R.id.txt_bal);
            txt_date=(TextView) itemView.findViewById(R.id.txt_date);
            txt_pts=(TextView) itemView.findViewById(R.id.txt_pts);

        }
    }

    public  void updateList(ArrayList<HistorySetGet> hisList) {
        this.hisList.addAll(hisList);
    }

}

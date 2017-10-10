package com.brim.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brim.FamilyCards;
import com.brim.Pojo.FamilyCardsSetGet;
import com.brim.ProfileDetails;
import com.brim.R;
import com.brim.UpgradeLimit;

import java.util.ArrayList;

/**
 * Created by su on 5/10/17.
 */

public class FamilyCardsAdapter extends RecyclerView.Adapter<FamilyCardsAdapter.ViewHolder> {

    FamilyCards familyCards;
    ArrayList<FamilyCardsSetGet> list=null;

    public FamilyCardsAdapter(FamilyCards familyCards, ArrayList<FamilyCardsSetGet> list) {

        this.familyCards=familyCards;
        this.list=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.familycard_adapter_view,parent,false);

        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        int height= (int) (familyCards.getWindow().getWindowManager().getDefaultDisplay().getHeight()/3.5);
        int width= (int) (familyCards.getWindow().getWindowManager().getDefaultDisplay().getWidth()/2.5);


        LinearLayout.LayoutParams Params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        holder.card_view.setLayoutParams(Params);

        RelativeLayout.LayoutParams Params_rl=new RelativeLayout.LayoutParams(width,100);
        Params_rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        holder.date_block.setLayoutParams(Params_rl);

        holder.txt_name.setText(list.get(position).getFirst_name()+" "+list.get(position).getLast_name());

        holder.txt_creditlimit.setText("CREDIT LIMIT : $"+list.get(position).getCredit_limit());

        holder.txt_card_no.setText(list.get(position).getLast_4_digit());

        holder.button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(familyCards, ProfileDetails.class);
                intent.putExtra("from","family");
                intent.putExtra("card_id",list.get(position).getId());
                familyCards.startActivity(intent);
            }
        });

        holder.button_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(familyCards, UpgradeLimit.class);
                intent.putExtra("from","family");
                intent.putExtra("limit",list.get(position).getCredit_limit());
                intent.putExtra("card_id",list.get(position).getId());

                familyCards.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout card_view,date_block;
        TextView txt_name,button_update,txt_creditlimit,button_limit,txt_card_no;

        public ViewHolder(View itemView) {
            super(itemView);
            card_view=(RelativeLayout) itemView.findViewById(R.id.card_view);
            date_block=(RelativeLayout) itemView.findViewById(R.id.date_block);

            txt_name=(TextView) itemView.findViewById(R.id.txt_name);
            button_update=(TextView) itemView.findViewById(R.id.button_update);
            txt_creditlimit=(TextView) itemView.findViewById(R.id.txt_creditlimit);
            button_limit=(TextView) itemView.findViewById(R.id.button_limit);
            txt_card_no=(TextView) itemView.findViewById(R.id.txt_card_no);
        }
    }
}

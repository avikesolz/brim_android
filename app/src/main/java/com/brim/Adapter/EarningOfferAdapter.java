package com.brim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brim.OfferDetails;
import com.brim.Pojo.OfferSetGet;
import com.brim.R;
import com.brim.RedemptionOffer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by su on 13/9/17.
 */

public class EarningOfferAdapter extends RecyclerView.Adapter<EarningOfferAdapter.ViewHolder> {

    Context mCintext;
    ArrayList<OfferSetGet> ListData;

    public EarningOfferAdapter(Context baseActivity, ArrayList<OfferSetGet> offerList) {

        this.mCintext=baseActivity;
        ListData=offerList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_offer_adapter_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            Picasso.with(mCintext)
                    .load(ListData.get(position).getContent().getJSONObject("merchant").getString("image"))
                    .fit()
                    .into(holder.image);

            holder.txt_name.setText(ListData.get(position).getContent().getJSONObject("merchant").getString("name"));

/*
                    Html.fromHtml("<font color='#0'> Earn </font>");
*/

            if (ListData.get(position).getType().equals("earning_offer")) {

                String s=  ListData.get(position).getContent().getString("multiplier");
                holder.txt_point.setText("Earn " + s + "x pts when you shop");
            } else {

                int s=  ListData.get(position).getContent().getInt("multiplier");

                holder.txt_point.setText("Save " + (100-s) + "% on redemption rate");
                holder.txt_point.setTextColor(mCintext.getResources().getColor(R.color.black));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ListData.get(position).getType().equals("earning_offer")){

                    Intent intent = new Intent(mCintext, OfferDetails.class);
                    try {
                        intent.putExtra("OfferId",ListData.get(position).getContent().getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mCintext.startActivity(intent);

                }
                else if(ListData.get(position).getType().equals("redemption_offer")){

                    Intent intent=new Intent(mCintext, RedemptionOffer.class);
                    try {
                        intent.putExtra("id",ListData.get(position).getContent().getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mCintext.startActivity(intent);
                }
                else if(ListData.get(position).getType().equals("generic_insight")){

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView txt_name,txt_point;
        public ViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.image);
            txt_name=(TextView)itemView.findViewById(R.id.txt_name);
            txt_point=(TextView)itemView.findViewById(R.id.txt_point);

        }
    }
}

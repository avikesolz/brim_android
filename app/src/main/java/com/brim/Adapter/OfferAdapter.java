package com.brim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brim.OfferDetails;
import com.brim.Pojo.OfferSetGet;
import com.brim.R;
import com.brim.RedemptionOffer;
import com.brim.Utils.DateDifference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by apple on 17/08/17.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    Context mCintext;
    ArrayList<OfferSetGet> ListData;
    int view_type=0;


    public OfferAdapter(Context baseActivity, ArrayList<OfferSetGet> offerList) {
        this.mCintext=baseActivity;
        ListData=offerList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null;

        if (viewType == 1){

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        }
        else if (viewType == 2){

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.redemption_item, parent, false);
        }
        else if (viewType == 3){

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_item, parent, false);
        }
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position1) {

        final int position = position1 % ListData.size();


        if (view_type == 1) {
            try {
                Picasso.with(mCintext)
                        .load(ListData.get(position).getContent().getJSONObject("merchant").getString("image"))
                        .into(holder.image);

                holder.offer_txt.setText("Earn "+ListData.get(position).getContent().getString("multiplier")+"x pts");
                holder.txt_details.setText("Earn "+ListData.get(position).getContent().getString("multiplier")+
                        "x pts when you shop at "+ListData.get(position).getContent().getJSONObject("merchant").getString("name")
                        +" using this card");


                String dd= DateDifference.dateDifference(ListData.get(position).getContent().getString("end_date"),ListData.get(position).getContent().getString("start_date"));
                holder.txt_lefttime.setText(dd+" DAYS LEFT");

                holder.txt_transaction.setText(ListData.get(position).getContent().getString("total_transactions")+" TRANSACTIONS");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (view_type == 2) {

            try {
                Picasso.with(mCintext)
                        .load(ListData.get(position).getContent().getJSONObject("merchant").getString("image"))
                        .into(holder.image);

                holder.offer_txt.setText("Save "+(100-ListData.get(position).getContent().getInt("multiplier"))+"% on redemption");
                holder.TXT_Marchent.setText(ListData.get(position).getContent().getJSONObject("merchant").getString("name"));
                holder.txt_details.setText("Save "+(100-ListData.get(position).getContent().getInt("multiplier"))+
                        "% on redemption rate for any "+ListData.get(position).getContent().getJSONObject("merchant").getString("name")
                        +" purchase");

                holder.TXT_Marchent.setText(ListData.get(position).getContent().getJSONObject("merchant").getString("name"));

                //DateDifference.dateDifference(ListData.get(position).getContent().getString("start_date"),ListData.get(position).getContent().getString("end_date"));

                String dd=DateDifference.dateDifference(ListData.get(position).getContent().getString("end_date"),ListData.get(position).getContent().getString("start_date"));
                holder.txt_lefttime.setText(dd+" DAYS LEFT");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (view_type == 3) {


            try {
                holder.TXT_Marchent.setText(ListData.get(position).getContent().getString("title"));
                holder.txt_details.setText(ListData.get(position).getContent().getString("body"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        holder.button_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListData.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int position = position1 % ListData.size();

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
    public int getItemViewType(int position1) {

        int position = position1 % ListData.size();

        if(ListData.get(position).getType().equals("earning_offer"))
            view_type=1;
        else if(ListData.get(position).getType().equals("redemption_offer"))
            view_type=2;
        else if(ListData.get(position).getType().equals("generic_insight"))
            view_type=3;


        return view_type;
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView offer_txt,TXT_Marchent,txt_details,txt_transaction,txt_lefttime;
        ImageView image,button_cross;

        public ViewHolder(View itemView) {
            super(itemView);

            button_cross=(ImageView) itemView.findViewById(R.id.button_cross);
            image=(ImageView) itemView.findViewById(R.id.image);
            TXT_Marchent=(TextView) itemView.findViewById(R.id.TXT_Marchent);
            txt_details=(TextView) itemView.findViewById(R.id.txt_details);

            if(view_type==1) {

                offer_txt = (TextView) itemView.findViewById(R.id.offer_txt);
                txt_lefttime = (TextView) itemView.findViewById(R.id.txt_lefttime);
                txt_transaction = (TextView) itemView.findViewById(R.id.txt_transaction);

            }else if(view_type==2){

                offer_txt = (TextView) itemView.findViewById(R.id.offer_txt);
                txt_lefttime = (TextView) itemView.findViewById(R.id.txt_lefttime);

            }


        }
    }

}

package com.brim.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brim.Pojo.TravelSetGet;
import com.brim.R;
import com.brim.TravelNotice;
import com.brim.UpdateTravel;
import com.brim.Utils.DateFormatConvertion;

import java.util.ArrayList;

/**
 * Created by su on 3/10/17.
 */

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {

    TravelNotice travelNotice;
    public ArrayList<TravelSetGet> list;

    public TravelAdapter(TravelNotice travelNotice, ArrayList<TravelSetGet> list) {

        this.travelNotice=travelNotice;
        this.list=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_adapter_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_place.setText(list.get(position).getLocation());

        holder.txt_date.setText(DateFormatConvertion.yyyymmdd_to_mmdd(list.get(position).getDeparture_date())+" - "
        +DateFormatConvertion.yyyymmdd_to_mmmddyyyy(list.get(position).getArrival_date()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(travelNotice, UpdateTravel.class);
                intent.putExtra("from","update_travel");
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("location",list.get(position).getLocation());
                intent.putExtra("arrival",list.get(position).getArrival_date());
                intent.putExtra("departure",list.get(position).getDeparture_date());
                travelNotice.startActivity(intent);
            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(travelNotice, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(travelNotice);
                }
                builder.setTitle("Delete")
                        .setMessage("Do you want to delete this travel notice?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                travelNotice.delete(list.get(position).getId(),position);

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_place,txt_date;
        RelativeLayout button_delete;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_place=(TextView) itemView.findViewById(R.id.txt_place);
            txt_date=(TextView) itemView.findViewById(R.id.txt_date);
            button_delete=(RelativeLayout) itemView.findViewById(R.id.button_delete);
        }
    }
}

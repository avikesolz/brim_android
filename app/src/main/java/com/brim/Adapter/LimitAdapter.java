package com.brim.Adapter;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.R;
import com.brim.UpgradeLimit;

import java.util.ArrayList;

/**
 * Created by su on 10/10/17.
 */

public class LimitAdapter extends RecyclerView.Adapter<LimitAdapter.ViewHolder> {

    Dialog dialog;
    ArrayList<String> list;
    UpgradeLimit upgradeLimit;

    public LimitAdapter(Dialog dialog, ArrayList<String> listLimit, UpgradeLimit upgradeLimit) {

        this.dialog=dialog;
        this.list=listLimit;
        this.upgradeLimit=upgradeLimit;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.province_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_province.setText("$"+list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upgradeLimit.txt_crelimit.setText("$"+list.get(position));
                dialog.cancel();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_province;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_province=(TextView) itemView.findViewById(R.id.txt_province);

        }
    }
}

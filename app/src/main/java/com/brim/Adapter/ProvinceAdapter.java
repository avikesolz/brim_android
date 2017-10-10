package com.brim.Adapter;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.AddUser;
import com.brim.Pojo.SpinnerSetGet;
import com.brim.ProfileDetails;
import com.brim.R;

import java.util.ArrayList;

/**
 * Created by su on 23/9/17.
 */

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {

    Dialog dialog;
    ArrayList<SpinnerSetGet> list;
    ProfileDetails profileDetails;
    AddUser addUser;

    public ProvinceAdapter(Dialog dialog,   ArrayList<SpinnerSetGet> list,  ProfileDetails profileDetails) {

        this.dialog=dialog;
        this.list=list;
        this.profileDetails=profileDetails;
    }

    public ProvinceAdapter(Dialog dialog, ArrayList<SpinnerSetGet> list, AddUser addUser) {

        this.dialog=dialog;
        this.list=list;
        this.addUser=addUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.province_view, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_province.setText(list.get(position).getProvice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (profileDetails!=null) {
                    profileDetails.updateTxt(list.get(position).getCode());
                    dialog.cancel();
                }else if (addUser!=null){
                    addUser.updateTxt(list.get(position).getCode());
                    dialog.cancel();
                }
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

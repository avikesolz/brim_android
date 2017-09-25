package com.brim.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.Pojo.AddressDetailsSetGet;
import com.brim.R;

import java.util.ArrayList;

/**
 * Created by ritam on 18/09/17.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder>{


    ArrayList<AddressDetailsSetGet> AllAddressValue;
    Context context;

    public AddressListAdapter(ArrayList<AddressDetailsSetGet> allAddressValue, Context context) {
        AllAddressValue = allAddressValue;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_address, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.Address.setText(AllAddressValue.get(position).getStreetAddress());
        holder.City.setText(AllAddressValue.get(position).getCity());

    }

    @Override
    public int getItemCount() {
        return AllAddressValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

TextView Address,City;

        public ViewHolder(View itemView) {
            super(itemView);

            Address = (TextView) itemView.findViewById(R.id.tv_address);
            City = (TextView) itemView.findViewById(R.id.tv_city);
        }
    }
}

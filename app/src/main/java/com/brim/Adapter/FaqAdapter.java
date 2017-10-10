package com.brim.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brim.FaqDetails;
import com.brim.Help;
import com.brim.Pojo.FaqSetGet;
import com.brim.R;

import java.util.ArrayList;

/**
 * Created by su on 3/10/17.
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    ArrayList<FaqSetGet> list;
    Help help;

    public FaqAdapter(Help help, ArrayList<FaqSetGet> list) {
        this.help=help;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_adapter_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_qust.setText(list.get(position).getQust());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(help, FaqDetails.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("qust",list.get(position).getQust());
                help.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_qust;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_qust=(TextView) itemView.findViewById(R.id.txt_qust);
        }
    }
}

package com.brim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.brim.Font.AxiformaBook;
import com.brim.Font.AxiformaMedium;
import com.brim.Font.AxiformaRegular;
import com.brim.MakePayment;
import com.brim.R;
import com.brim.Utils.ConvertLocal;
import com.brim.Utils.DateFormatConvertion;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by su on 4/9/17.
 */

public class CardAdapter extends PagerAdapter{

    LayoutInflater inflate;
    int size=0;
    JSONObject Object;
    Context context;


    public CardAdapter(Context context, String s, int i) {
        inflate=LayoutInflater.from(context);
        size=i;
        try {
            Object=new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.context=context;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView =null;

        if(position==0) {
            itemView = inflate.inflate(R.layout.card_details_view, container, false);

            try {


                ((AxiformaBook) itemView.findViewById(R.id.TXT_Balance)).setText("$" + ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("current_balance"))));
                ((AxiformaMedium) itemView.findViewById(R.id.TXT_1)).setText("$" + ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("credit_available"))));
                ((AxiformaMedium) itemView.findViewById(R.id.TXT_2)).setText("$" + ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("minimum_payment"))));
                ((AxiformaMedium) itemView.findViewById(R.id.TXT_3)).setText("$" + ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("last_payment_amount"))));
                ((AxiformaRegular) itemView.findViewById(R.id.TXT_Min_Pay_Due_Date)).setText("due " + DateFormatConvertion.yyyymmdd_to_mmmddyyyy(Object.getString("minimum_payment_due_date")));
                ((AxiformaRegular) itemView.findViewById(R.id.TXT_LAST_PAID)).setText("paid " + DateFormatConvertion.yyyymmdd_to_mmmddyyyy(Object.getString("last_payment_date")));
                ((AxiformaRegular) itemView.findViewById(R.id.TXT_Limit)).setText("limit $" + ConvertLocal.priceWithOutDecimal(Double.parseDouble(Object.getString("credit_limit"))));


                if (Object.has("current_points_balance_in_dollars")) {
                    ((AxiformaRegular) itemView.findViewById(R.id.TXT_PointL)).setText("$" + ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("current_points_balance_in_dollars"))));
                    ((AxiformaMedium) itemView.findViewById(R.id.TXT_4)).setText(ConvertLocal.priceWithOutDecimal(Double.parseDouble(Object.getString("current_points_balance"))) + " pts");
                } else {
                    ((AxiformaMedium) itemView.findViewById(R.id.TXT_4)).setText("NA");
                    ((AxiformaRegular) itemView.findViewById(R.id.TXT_PointL)).setText("");
                }
                ((LinearLayout)itemView.findViewById(R.id.button_payment)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, MakePayment.class));
                    }
                });

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(position==1) {
            itemView = inflate.inflate(R.layout.card_view, container, false);

            try {

                ((AxiformaBook) itemView.findViewById(R.id.txt_card_no)).setText(Object.getString("last_4_digit"));
                ((AxiformaBook) itemView.findViewById(R.id.txt_exp_date)).setText(Object.getString("expiration_date"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

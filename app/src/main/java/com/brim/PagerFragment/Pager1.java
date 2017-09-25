package com.brim.PagerFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Font.AxiformaBook;
import com.brim.Font.AxiformaMedium;
import com.brim.Font.AxiformaRegular;
import com.brim.Font.SFNFTextView;
import com.brim.R;
import com.brim.Utils.ConvertLocal;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.Loger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Pager1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Pager1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Pager1 newInstance(String param1, String param2) {
        Pager1 fragment = new Pager1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new HTTP_Get(AppConstant.BaseUrl) {


            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONObject Object=new JSONObject(Response);
                    ((AxiformaBook)view.findViewById(R.id.TXT_Balance)).setText("$"+ ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("current_balance"))));
                    ((AxiformaMedium)view.findViewById(R.id.TXT_1)).setText("$"+ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("credit_available"))));
                    ((AxiformaMedium)view.findViewById(R.id.TXT_2)).setText("$"+ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("minimum_payment"))));
                    ((AxiformaRegular)view.findViewById(R.id.TXT_Min_Pay_Due_Date)).setText("due "+ DateFormatConvertion.yyyymmdd_to_mmmddyyyy(Object.getString("minimum_payment_due_date")));
                    ((AxiformaRegular)view.findViewById(R.id.TXT_Limit)).setText("limit $"+ ConvertLocal.priceWithOutDecimal(Double.parseDouble(Object.getString("credit_limit"))));


                    if(Object.has("current_points_balance_in_dollars")) {
                        ((AxiformaRegular) view.findViewById(R.id.TXT_PointL)).setText("$" + ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("current_points_balance_in_dollars"))));
                        ((AxiformaMedium) view.findViewById(R.id.TXT_4)).setText("$" + ConvertLocal.priceWithDecimal(Double.parseDouble(Object.getString("current_points_balance"))));
                    }else {
                        ((AxiformaMedium) view.findViewById(R.id.TXT_4)).setText("NA");
                        ((AxiformaRegular) view.findViewById(R.id.TXT_PointL)).setText("");
                    }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            protected void OnErrorApi(String Error) {
                Loger.MSG("@@ ON Erroe",Error);
            }

            @Override
            protected void OnHttPError(String HttpError) {

            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager1, container, false);
    }


}

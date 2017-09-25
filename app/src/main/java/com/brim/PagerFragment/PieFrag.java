package com.brim.PagerFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.FotterFragmnet.Budget;
import com.brim.R;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.GetDate;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by su on 20/9/17.
 */

public class PieFrag extends Fragment {

    Spinner spinner_date;
    RelativeLayout button_type;
    TextView txt_grand_total;

    Budget budget;
    String Object;
    boolean spinner_status=false;
    List<String> listDates;


    private PieChartView chart;
    private PieChartData data;

    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;



    AppProgerssDialog appProgerssDialog;
    NetworkChecking networkChecking;


    public PieFrag(Budget budget, String object) {
        this.budget=budget;
        this.Object=object;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pie_graph_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appProgerssDialog=new AppProgerssDialog(getActivity());
        networkChecking=new NetworkChecking(getActivity());

        spinner_date=(Spinner)view.findViewById(R.id.spinner_date);
        button_type=(RelativeLayout)view.findViewById(R.id.button_type);
        txt_grand_total=(TextView) view.findViewById(R.id.txt_grand_total);


        chart = (PieChartView) view.findViewById(R.id.pie_chart);


        listDates = new ArrayList<String>();
        listDates.add(DateFormatConvertion.yyyymm_to_mmyyyy(GetDate.getMonth()));
        listDates.add(DateFormatConvertion.yyyymm_to_mmyyyy(GetDate.getPrevMonth()));
        listDates.add(DateFormatConvertion.yyyymm_to_mmyyyy(GetDate.getPrev2Month()));

        final ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getActivity(), R.layout.white_spinner_view, listDates);

        // Drop down layout style - list view with radio button
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_date.setAdapter(daysAdapter);



        button_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.d("spinner status1","::::"+spinner_status);

                spinner_date.performClick();

            }
        });



        spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               // Log.d("Spinner","-------------Selected");

                //Log.d("spinner status into","::::"+spinner_status);


                if(spinner_status==true) {


                       if (networkChecking.isConnectingToInternet() == true) {
                            getSpend(DateFormatConvertion.mmyyyy_to_yyyymm(listDates.get(i)));
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                        }

               }else{
                    generateData(Object);  //*******************************************Pie Chart Draw Method***************************

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void generateData(String Obj){

        //Log.d("spinner status2","::::"+spinner_status);

        spinner_status=true;


        JSONObject jsonObject;
        JSONArray jsonArray=null;
        Float grand_total;
        Float sub_total;
        String color;
        try {
            jsonObject=new JSONObject(Obj);
            jsonArray=jsonObject.getJSONArray("results");

            grand_total=Float.parseFloat(jsonObject.getString("grand_total"));

            txt_grand_total.setText("$"+jsonObject.getString("grand_total"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int numValues = jsonArray.length();


        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {

            try {
                sub_total=Float.parseFloat(jsonArray.getJSONObject(i).getString("total_amount"));
                color="#"+jsonArray.getJSONObject(i).getJSONObject("category").getString("color");

                //Log.d("color code",":::"+color);

                SliceValue sliceValue = new SliceValue(sub_total, Color.parseColor(color));
                //SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());

                values.add(sliceValue);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);


        chart.setChartRotationEnabled(false);
        chart.setPieChartData(data);

    }

    public void getSpend(String date) {

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.SPEND_SUMMARY+"?"+ AppConstant.CARDID+"="+ BrimApplication.getInstnace().GetCardId()+"&year_month="+date) {
            @Override
            protected void OnSucess(String response) {

                try {
                    JSONObject obj=new JSONObject(response);

                    generateData(obj.toString());

                    budget.updateSpendList(obj.toString());

                    appProgerssDialog.Dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();

                    appProgerssDialog.Dismiss();

                }


            }

            @Override
            protected void OnErrorApi(String Error) {

                appProgerssDialog.Dismiss();


            }

            @Override
            protected void OnHttPError(String HttpError) {

                appProgerssDialog.Dismiss();


            }
        };

    }

}

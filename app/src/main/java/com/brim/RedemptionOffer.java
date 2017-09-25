package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.EarningOfferAdapter;
import com.brim.Adapter.RecentTransactionAdapter;
import com.brim.Adapter.RedemptionAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.FotterFragmnet.Activity;
import com.brim.Pojo.OfferSetGet;
import com.brim.Pojo.TransactionListData;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.DateDifference;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RedemptionOffer extends AppCompatActivity {

    TextView header_name,txt_name,txt_mesg,txt_lefttime,txt_transaction_header;
    ImageView back_button,image;
    Spinner spinner_days;
    RelativeLayout button_days;
    RecyclerView rv_redemption;


    String redmp_Id;
    String name;
    int multiplier;
    float reward_bal;


    public int page_no=1,size=10;
    String tans_filter="all",date="60",sear_text="",install_filter="";
    boolean spinner_status=false;
    LinkedList<TransactionListData> RecentTransactionList;
    RedemptionAdapter redemptionAdapter=null;




    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redemption_offer);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        header_name= (TextView) findViewById(R.id.header_name);
        txt_name= (TextView) findViewById(R.id.txt_name);
        txt_mesg= (TextView) findViewById(R.id.txt_mesg);
        txt_lefttime= (TextView) findViewById(R.id.txt_lefttime);
        txt_transaction_header= (TextView) findViewById(R.id.txt_transaction_header);
        back_button= (ImageView) findViewById(R.id.back_button);
        image= (ImageView) findViewById(R.id.image);
        back_button= (ImageView) findViewById(R.id.back_button);

        spinner_days=(Spinner)findViewById(R.id.spinner_days);
        button_days=(RelativeLayout)findViewById(R.id.button_days);

        rv_redemption= (RecyclerView) findViewById(R.id.rv_redemption);
        rv_redemption.setLayoutManager(new LinearLayoutManager(this));

        redmp_Id=getIntent().getExtras().getString("id");
        Log.d("Redemption_Id","::::"+getIntent().getExtras().getString("id"));


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //*************************************Days Spinner Set Up*********************************************************
        List<String> listDays = new ArrayList<String>();
        listDays.add("7 Days");
        listDays.add("14 Days");
        listDays.add("30 Days");
        listDays.add("60 Days");

        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(this, R.layout.white_spinner_view, listDays);

        // Drop down layout style - list view with radio button
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_days.setAdapter(daysAdapter);
        spinner_days.setSelection(3);


        button_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner_days.performClick();
            }
        });

        spinner_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spinner_status==true) {

                    if (i == 0) {
                        page_no = 1;
                        redemptionAdapter=null;

                        date = "7";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(RedemptionOffer.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 1) {
                        page_no = 1;
                        redemptionAdapter=null;

                        date = "14";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(RedemptionOffer.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 2) {
                        page_no = 1;
                        redemptionAdapter=null;

                        date = "30";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(RedemptionOffer.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 3) {
                        page_no = 1;
                        redemptionAdapter=null;

                        date = "60";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(RedemptionOffer.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getCardDetails(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.CARDS) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONObject Object=new JSONObject(Response);

                    reward_bal=Float.parseFloat(Object.getString("current_points_balance"));

                    getRedemption();

                    //appProgerssDialog.Dismiss();
                }catch (JSONException e)
                {
                    e.printStackTrace();
                    appProgerssDialog.Dismiss();

                }

            }

            @Override
            protected void OnErrorApi(String Error) {
                Loger.MSG("@@ ON Error",""+Error);
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnHttPError(String HttpError) {
                appProgerssDialog.Dismiss();

            }
        };
    }



    private void getRedemption(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.REDEEM_OFFER+"/"+redmp_Id+"?card_id="+BrimApplication.getInstnace().GetCardId()){

            @Override
            protected void OnSucess(String Response) {


                try {
                    JSONObject jsonObject=new JSONObject(Response);

                    if(jsonObject!=null) {

                        name=jsonObject.getJSONObject("merchant").getString("name");

                        Picasso.with(getBaseContext())
                                .load(jsonObject.getJSONObject("merchant").getString("image"))
                                .fit()
                                .into(image);

                        header_name.setText(jsonObject.getJSONObject("merchant").getString("name")+" Redemption Offer");
                        txt_name.setText(jsonObject.getJSONObject("merchant").getString("name"));

                        txt_mesg.setText("Save "+(100-jsonObject.getInt("multiplier")+
                                "% on redemption rate for any "+jsonObject.getJSONObject("merchant").getString("name")
                                +" purchase"));

                        multiplier=jsonObject.getInt("multiplier");

                        String dd=DateDifference.dateDifference(jsonObject.getString("end_date"),jsonObject.getString("start_date"));

                        txt_lefttime.setText(dd+" DAYS LEFT");

                        txt_transaction_header.setText("TRANSACTION AT "+jsonObject.getJSONObject("merchant").getString("name"));

                        getTransactions();

                        //appProgerssDialog.Dismiss();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    appProgerssDialog.Dismiss();

                }


            }

            @Override
            protected void OnErrorApi(String Error) {
                Loger.MSG("@@ ON Erroe",""+Error);

                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnHttPError(String HttpError) {

                appProgerssDialog.Dismiss();

            }
        };

    }

    public void getTransactions() {
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRANSACTION+ BrimApplication.getInstnace().GetCardId()+"/transactions?card_filter=&sort=-transaction_date"
                +"&installment_filter="+"&merchant_filter="+name
                +"&transaction_filter="+"&date_filter="+date+"&q="
                +"&page_number="+page_no+"&page_size="+size) {
            @Override
            protected void OnSucess(String Response) {


                RecentTransactionList=new LinkedList<>();

                try {
                    JSONObject JSobject=new JSONObject(Response);
                    JSONArray transactions=JSobject.getJSONArray("transactions");

                    if (transactions!=null) {

                        if(transactions.length()>0){

                            spinner_status=true;

                            for (int i = 0; i < transactions.length(); i++) {
                                TransactionListData datat = new TransactionListData();
                                datat.setItemObject(transactions.getJSONObject(i));
                                datat.setImage(AppConstant.IMAGE_URL + transactions.getJSONObject(i).getJSONObject("category").getString("id") + ".png");

                                //Log.d("Image url","::::"+AppConstant.IMAGE_URL+transactions.getJSONObject(i).getJSONObject("category").getString("id")+".png");

                                RecentTransactionList.add(datat);

                            }

                            if (redemptionAdapter == null) {

                                redemptionAdapter = new RedemptionAdapter(RedemptionOffer.this, RecentTransactionList, RedemptionOffer.this,multiplier,name,reward_bal,redmp_Id);
                                rv_redemption.setAdapter(redemptionAdapter);
                            } else {

                                redemptionAdapter.updateList(RecentTransactionList);
                                redemptionAdapter.notifyDataSetChanged();

                            }

                        }
                    }
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

    @Override
    protected void onResume() {
        super.onResume();

        if(networkChecking.isConnectingToInternet()==true) {
            page_no=1;
            date="60";
            redemptionAdapter=null;

            getCardDetails();
        }else{
            Toast.makeText(this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
    }
}

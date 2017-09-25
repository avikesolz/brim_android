package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.ApiHelper.HTTP_Get;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Utils.AppAlert;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Redeem extends AppCompatActivity {

    TextView txt_points,txt_date,txt_max_point,txt_save,txt_remaining,button_continue;
    EditText et_cust_amount;
    View border4,border5;
    LinearLayout view_1;
    RelativeLayout view_2,view_3;
    ImageView button_cross,back_button;

    TextView txt_name,txt_cate_name,txt_balance,txt_date2;
    TextView txt_credited,txt_rewards,txt_used_points,txt_points_left,button_approve,button_conti;


    float max_amount,max_points,remaining_points;
    float amount,rewards_bal;

    String trans_id,redmp_Id;

    AppProgerssDialog appProgerssDialog;
    NetworkChecking networkChecking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        appProgerssDialog=new AppProgerssDialog(this);
        networkChecking=new NetworkChecking(this);

        txt_points= (TextView) findViewById(R.id.txt_points);
        txt_date= (TextView) findViewById(R.id.txt_date);
        txt_max_point= (TextView) findViewById(R.id.txt_max_point);
        txt_save= (TextView) findViewById(R.id.txt_save);
        txt_remaining= (TextView) findViewById(R.id.txt_remaining);
        button_continue= (TextView) findViewById(R.id.button_continue);
        et_cust_amount= (EditText) findViewById(R.id.et_cust_amount);

        border5=findViewById(R.id.border5);
        border4=findViewById(R.id.border4);

        view_1= (LinearLayout) findViewById(R.id.view_1);
        view_1.setVisibility(View.VISIBLE);


        view_2= (RelativeLayout) findViewById(R.id.view_2);
        view_2.setVisibility(View.GONE);

        view_3= (RelativeLayout) findViewById(R.id.view_3);
        view_3.setVisibility(View.GONE);

        txt_name= (TextView) findViewById(R.id.txt_name);
        txt_cate_name= (TextView) findViewById(R.id.txt_cate_name);
        txt_balance= (TextView) findViewById(R.id.txt_balance);
        txt_date2= (TextView) findViewById(R.id.txt_date2);


        txt_credited= (TextView) findViewById(R.id.txt_credited);
        txt_rewards= (TextView) findViewById(R.id.txt_rewards);
        txt_used_points= (TextView) findViewById(R.id.txt_used_points);
        txt_points_left= (TextView) findViewById(R.id.txt_points_left);

        txt_name.setText(getIntent().getExtras().getString("name"));
        txt_cate_name.setText(getIntent().getExtras().getString("cate_name"));
        txt_date2.setText(DateFormatConvertion.yyyymmdd_to_mmdd(getIntent().getExtras().getString("transaction_date")));
        txt_balance.setText("$"+getIntent().getExtras().getInt("amount"));

        txt_credited.setText("$"+getIntent().getExtras().getInt("amount"));

        rewards_bal=getIntent().getExtras().getFloat("rewards_bal");
        redmp_Id=getIntent().getExtras().getString("redmp_Id");
        trans_id=getIntent().getExtras().getString("transaction_id");

        txt_rewards.setText(""+rewards_bal);

        button_approve= (TextView) findViewById(R.id.button_approve);
        button_conti= (TextView) findViewById(R.id.button_conti);

        back_button= (ImageView) findViewById(R.id.back_button);
        button_cross= (ImageView) findViewById(R.id.button_cross);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        button_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_1.setVisibility(View.VISIBLE);
                view_2.setVisibility(View.GONE);


            }
        });


        txt_points.setText(""+rewards_bal+" pts");
        txt_max_point.setText("$"+getIntent().getExtras().getInt("amount"));
        txt_date.setText(DateFormatConvertion.yyyymmdd_to_mmmddyyyy(getIntent().getExtras().getString("transaction_date")));

        amount=(float) getIntent().getExtras().getInt("amount");
        max_amount= (float) (getIntent().getExtras().getInt("multiplier")*getIntent().getExtras().getInt("amount"));
        Log.d("Max points","::::::::::::"+max_amount);
        max_points=max_amount/100;

        et_cust_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                border4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                border5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        et_cust_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editable.toString().equals("")) {

                    float input_amount = Float.parseFloat(editable.toString());
                    Log.d("input amount", "::::" + input_amount);
                    if (input_amount <= max_amount) {
                        float save = (float) ((input_amount / getIntent().getExtras().getInt("multiplier") - (input_amount) / 100));
                        txt_save.setText("$" + new DecimalFormat("##.##").format(save));
                        txt_remaining.setText("$"+new DecimalFormat("##.##").format((max_points-save)));

                        remaining_points=(float) (rewards_bal-Float.parseFloat(et_cust_amount.getText().toString()));

                    } else {

                        AppAlert appAlert=new AppAlert(Redeem.this);
                        appAlert.Error("You should enter proper amount");

                        et_cust_amount.setText("");
                        remaining_points=0;

                        txt_save.setText("");
                        txt_remaining.setText("");

                    }
                }else if(editable.toString().equals("")){

                    remaining_points=0;

                    txt_save.setText("");
                    txt_remaining.setText("");
                }

            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_cust_amount.getText().toString().equals("")){

                    Toast.makeText(Redeem.this,"Enter amount",Toast.LENGTH_SHORT).show();
                }else{

                    view_1.setVisibility(View.GONE);
                    view_2.setVisibility(View.VISIBLE);

                    txt_used_points.setText("-"+et_cust_amount.getText().toString());
                    txt_points_left.setText(""+remaining_points);

                }
            }
        });

        button_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(networkChecking.isConnectingToInternet()==true) {

                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("card_id",BrimApplication.getInstnace().GetCardId());
                        jsonObject.put("transaction_id",trans_id);
                        jsonObject.put("redemption_offer_id",redmp_Id);
                        jsonObject.put("points",et_cust_amount.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    redeemApprove(jsonObject.toString());
                }else{
                    Toast.makeText(Redeem.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void redeemApprove(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_POST_AUTH(ApiConstant.REWARDS,obj) {


            @Override
            protected void OnSucess(String Response) {
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnErrorApi(String Error) {
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnHttPError(String HttpError) {
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnResponseCode(int code) {

               // Log.d("response_code",""+code);
                appProgerssDialog.Dismiss();

                if(code==200){
                    view_3.setVisibility(View.VISIBLE);
                    view_2.setVisibility(View.GONE);
                }else{

                    new AppAlert(Redeem.this).Error("Something Wrong");

                }

            }
        };
    }


}

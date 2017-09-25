package com.brim;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.CardAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.ApiHelper.HTTP_POST;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Font.AxiformaBookEditText;
import com.brim.Utils.AppAlert;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakePayment extends AppCompatActivity {

    ImageView back_button;
    TextView button_debit,button_points,button_continue;
    LinearLayout view_1,view_2;
    TextView txt_avai_credit,txt_date,txt_min_amount,txt_state_bal,txt_curnt_bal,txt_max_amount,txt_cust_point;
    RelativeLayout cust_amt_tab1,crnt_amt_tab,state_bal_tab,curnt_bal_tab,cust_amt_tab2,max_amount_tab;
    View border2,border3,border4,border5,border6,border7,border8;
    EditText et_cust_amount,et_cust_amount2;

    AppProgerssDialog appProgerssDialog;

    String tab_status="debit";
    String card_id="";
    String amount="";
    String points="";

    NetworkChecking networkChecking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        appProgerssDialog=new AppProgerssDialog(this);

        networkChecking=new NetworkChecking(this);


        back_button= (ImageView) findViewById(R.id.back_button);
        button_debit= (TextView) findViewById(R.id.button_debit);
        button_points= (TextView) findViewById(R.id.button_points);
        button_continue= (TextView) findViewById(R.id.button_continue);

        view_1= (LinearLayout) findViewById(R.id.view_1);
        view_2= (LinearLayout) findViewById(R.id.view_2);

        txt_avai_credit= (TextView) findViewById(R.id.txt_avai_credit);
        txt_date= (TextView) findViewById(R.id.txt_date);
        txt_min_amount= (TextView) findViewById(R.id.txt_min_amount);
        txt_state_bal= (TextView) findViewById(R.id.txt_state_bal);
        txt_curnt_bal= (TextView) findViewById(R.id.txt_curnt_bal);
        txt_max_amount= (TextView) findViewById(R.id.txt_max_amount);
        txt_cust_point= (TextView) findViewById(R.id.txt_cust_point);

        et_cust_amount= (EditText) findViewById(R.id.et_cust_amount);
        et_cust_amount2= (EditText) findViewById(R.id.et_cust_amount2);

        cust_amt_tab1= (RelativeLayout) findViewById(R.id.cust_amt_tab1);
        crnt_amt_tab= (RelativeLayout) findViewById(R.id.crnt_amt_tab);
        state_bal_tab= (RelativeLayout) findViewById(R.id.state_bal_tab);
        curnt_bal_tab= (RelativeLayout) findViewById(R.id.curnt_bal_tab);
        cust_amt_tab2= (RelativeLayout) findViewById(R.id.cust_amt_tab2);
        max_amount_tab= (RelativeLayout) findViewById(R.id.max_amount_tab);

        border2=findViewById(R.id.border2);
        border3=findViewById(R.id.border3);
        border4=findViewById(R.id.border4);
        border5=findViewById(R.id.border5);
        border6=findViewById(R.id.border6);
        border7=findViewById(R.id.border7);
        border8=findViewById(R.id.border8);

        cust_amt_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                border2.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border3.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border4.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border5.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border6.setBackgroundColor(getResources().getColor(R.color.BorderGray));

                cust_amt_tab1.setBackgroundColor(getResources().getColor(R.color.fadeBlue));
                crnt_amt_tab.setBackgroundColor(getResources().getColor(R.color.white));
                state_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));
                curnt_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));

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

                amount=et_cust_amount.getText().toString();

            }
        });

        crnt_amt_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                border2.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border3.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border4.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border5.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border6.setBackgroundColor(getResources().getColor(R.color.BorderGray));

                cust_amt_tab1.setBackgroundColor(getResources().getColor(R.color.white));
                crnt_amt_tab.setBackgroundColor(getResources().getColor(R.color.fadeBlue));
                crnt_amt_tab.setBackgroundColor(getResources().getColor(R.color.fadeBlue));
                state_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));
                curnt_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));

                amount=txt_min_amount.getText().toString();
            }
        });

        state_bal_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                border2.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border3.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border4.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border5.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border6.setBackgroundColor(getResources().getColor(R.color.BorderGray));


                crnt_amt_tab.setBackgroundColor(getResources().getColor(R.color.white));
                cust_amt_tab1.setBackgroundColor(getResources().getColor(R.color.white));
                state_bal_tab.setBackgroundColor(getResources().getColor(R.color.fadeBlue));
                curnt_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));

                amount=txt_state_bal.getText().toString();

            }
        });

        curnt_bal_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                border2.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border3.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border4.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border5.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border6.setBackgroundColor(getResources().getColor(R.color.lightBlue));

                crnt_amt_tab.setBackgroundColor(getResources().getColor(R.color.white));
                cust_amt_tab1.setBackgroundColor(getResources().getColor(R.color.white));
                state_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));
                curnt_bal_tab.setBackgroundColor(getResources().getColor(R.color.fadeBlue));

                amount=txt_curnt_bal.getText().toString();
            }
        });

        //*************************************************2nd Tab View**************************************

        cust_amt_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                border2.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border7.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border8.setBackgroundColor(getResources().getColor(R.color.BorderGray));

                cust_amt_tab2.setBackgroundColor(getResources().getColor(R.color.fadeBlue));
                max_amount_tab.setBackgroundColor(getResources().getColor(R.color.white));


            }
        });

        et_cust_amount2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                    amount = et_cust_amount2.getText().toString();
                if(!amount.equals("")) {
                    points = String.valueOf(Long.parseLong(amount) * 100);
              }

            }
        });

        max_amount_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                border2.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border7.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                border8.setBackgroundColor(getResources().getColor(R.color.lightBlue));

                cust_amt_tab2.setBackgroundColor(getResources().getColor(R.color.white));
                max_amount_tab.setBackgroundColor(getResources().getColor(R.color.fadeBlue));

                amount=txt_max_amount.getText().toString();

            }
        });




        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tab_status="debit";
                amount="";
                et_cust_amount.setText("");


                button_debit.setBackgroundResource(R.drawable.mold_rectangle_blue_left);
                button_points.setBackgroundResource(R.drawable.mold_rectangle_bule_white_right);

                button_debit.setTextColor(getResources().getColor(R.color.white));
                button_points.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                view_1.setVisibility(View.VISIBLE);
                view_2.setVisibility(View.GONE);


                border2.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border3.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border4.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border5.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border6.setBackgroundColor(getResources().getColor(R.color.BorderGray));

                crnt_amt_tab.setBackgroundColor(getResources().getColor(R.color.white));
                cust_amt_tab1.setBackgroundColor(getResources().getColor(R.color.white));
                state_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));
                curnt_bal_tab.setBackgroundColor(getResources().getColor(R.color.white));

                View v = MakePayment.this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(MakePayment.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        button_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tab_status="points";
                amount="";
                et_cust_amount2.setText("");


                button_debit.setBackgroundResource(R.drawable.mold_rectangle_blue_white_left);
                button_points.setBackgroundResource(R.drawable.mold_rectangle_blue_right);

                button_points.setTextColor(getResources().getColor(R.color.white));
                button_debit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                view_1.setVisibility(View.GONE);
                view_2.setVisibility(View.VISIBLE);


                border2.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border7.setBackgroundColor(getResources().getColor(R.color.BorderGray));
                border8.setBackgroundColor(getResources().getColor(R.color.BorderGray));

                cust_amt_tab2.setBackgroundColor(getResources().getColor(R.color.white));
                max_amount_tab.setBackgroundColor(getResources().getColor(R.color.white));

                View v = MakePayment.this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(MakePayment.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(amount.equals("")){
                    Toast.makeText(MakePayment.this,"Enter or Select amount",Toast.LENGTH_SHORT).show();
                }else{

                    if(tab_status.equals("debit")) {

                        JSONObject object=new JSONObject();
                        try {
                            object.put("card_id",card_id);
                            object.put("amount",amount);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String Body=object.toString();

                        if(networkChecking.isConnectingToInternet()==true) {

                            continuePayment( ApiConstant.PAYMENT_INTERAC,Body);
                        }else{
                            Toast.makeText(MakePayment.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }


                    }else if(tab_status.equals("points")){

                        JSONObject object=new JSONObject();
                        try {
                            object.put("card_id",card_id);
                            object.put("points",points);
                         //   object.put("cash_equivalent",points);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String Body=object.toString();

                        if(networkChecking.isConnectingToInternet()==true) {

                            continuePayment( ApiConstant.PAYMENT_POINTS,Body);
                        }else{
                            Toast.makeText(MakePayment.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }
        });

        if(networkChecking.isConnectingToInternet()==true) {
            getCardDetails();
        }else{
            Toast.makeText(this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }

    }

    public void  getCardDetails(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();


        new HTTP_Get(ApiConstant.CARDS) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONObject Object=new JSONObject(Response);


                    if(Object!=null) {

                        if (Object.length() > 0) {

                            card_id=Object.getString("id");
                            txt_avai_credit.setText("$"+Object.getString("credit_available"));
                            txt_date.setText(dateFormatChange(Object.getString("last_payment_date")));
                            txt_min_amount.setText(Object.getString("minimum_payment"));
                            txt_state_bal.setText(Object.getString("current_statement_balance"));
                            txt_curnt_bal.setText(Object.getString("current_balance"));

                            txt_cust_point.setText(Object.getString("current_points_balance")+" pts");
                            points=Object.getString("current_points_balance");
                            txt_max_amount.setText(Object.getString("current_points_balance_in_dollars"));

                            appProgerssDialog.Dismiss();


                        }
                    }


                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            protected void OnErrorApi(String Error) {
                Loger.MSG("@@ ON Erroe",Error);
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnHttPError(String HttpError) {
                appProgerssDialog.Dismiss();

            }
        };
    }

    public String dateFormatChange(String date){
        String inputDateStr=date;
        String outputDateStr="";

        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");
            Date d1 = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(d1);

            Log.d("date Change",":::"+outputDateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  outputDateStr;
    }

    public void continuePayment(String url,String Body ){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();


        new HTTP_POST_AUTH(url, Body) {
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

                if (code==200){

                    Toast.makeText(MakePayment.this,"Payment successfully completed",Toast.LENGTH_SHORT).show();

                    finish();
                }else{

                    //Toast.makeText(MakePayment.this,"Something Wrong",Toast.LENGTH_SHORT).show();

                    new AppAlert(MakePayment.this).Error("Something Wrong");


                }

            }
        };

    }
}

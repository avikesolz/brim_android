package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.brim.Adapter.FaqAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.Pojo.FaqSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Settings_Notification extends AppCompatActivity {

    ImageView back_button;
    Switch bt_purchases,bt_rewards,bt_pay_due,bt_cre_limit;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    boolean purchases_status,rewards_status,payDue_status,credit_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__notification);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        bt_purchases= (Switch) findViewById(R.id.bt_purchases);
        bt_rewards= (Switch) findViewById(R.id.bt_rewards);
        bt_pay_due= (Switch) findViewById(R.id.bt_pay_due);
        bt_cre_limit= (Switch) findViewById(R.id.bt_cre_limit);

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(networkChecking.isConnectingToInternet()==true) {
            getSettings();

        }else{
            Toast.makeText(Settings_Notification.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }


        bt_purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(networkChecking.isConnectingToInternet()==true) {

                    JSONObject jsonObject=new JSONObject();
                    try {
                        if (purchases_status){

                            purchases_status = false;
                            jsonObject.put("notif_purchase",false);

                        }else {

                            purchases_status = true;
                            jsonObject.put("notif_purchase",true);

                        }

                        jsonObject.put("notif_reward",rewards_status);
                        jsonObject.put("notif_payment_due",payDue_status);
                        jsonObject.put("notif_credit_limit",credit_status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.v("jsonObject:::",jsonObject.toString());

                    setSettings(jsonObject.toString());

                }else{
                    Toast.makeText(Settings_Notification.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }

            }
        });

        bt_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(networkChecking.isConnectingToInternet()==true) {

                    JSONObject jsonObject=new JSONObject();
                    try {
                        if (rewards_status){

                            rewards_status = false;
                            jsonObject.put("notif_reward",rewards_status);

                        }else {

                            rewards_status = true;
                            jsonObject.put("notif_reward",rewards_status);

                        }

                        jsonObject.put("notif_purchase",purchases_status);
                        jsonObject.put("notif_payment_due",payDue_status);
                        jsonObject.put("notif_credit_limit",credit_status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.v("jsonObject:::",jsonObject.toString());

                    setSettings(jsonObject.toString());

                }else{
                    Toast.makeText(Settings_Notification.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_pay_due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(networkChecking.isConnectingToInternet()==true) {

                    JSONObject jsonObject=new JSONObject();
                    try {
                        if (payDue_status){

                            payDue_status = false;
                            jsonObject.put("notif_payment_due",payDue_status);

                        }else {

                            payDue_status = true;
                            jsonObject.put("notif_payment_due",payDue_status);

                        }

                        jsonObject.put("notif_purchase",purchases_status);
                        jsonObject.put("notif_reward",rewards_status);

                        jsonObject.put("notif_credit_limit",credit_status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.v("jsonObject:::",jsonObject.toString());

                    setSettings(jsonObject.toString());

                }else{
                    Toast.makeText(Settings_Notification.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }

            }
        });

        bt_cre_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(networkChecking.isConnectingToInternet()==true) {

                    JSONObject jsonObject=new JSONObject();
                    try {
                        if (credit_status){

                            credit_status = false;
                            jsonObject.put("notif_credit_limit",credit_status);

                        }else {

                            credit_status = true;
                            jsonObject.put("notif_credit_limit",credit_status);


                        }

                        jsonObject.put("notif_purchase",purchases_status);
                        jsonObject.put("notif_reward",rewards_status);
                        jsonObject.put("notif_payment_due",payDue_status);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.v("jsonObject:::",jsonObject.toString());

                    setSettings(jsonObject.toString());

                }else{
                    Toast.makeText(Settings_Notification.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getSettings(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.SETTINGS) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONObject jsonObject=new JSONObject(Response);

                    purchases_status=jsonObject.getBoolean("notif_purchase");
                    rewards_status=jsonObject.getBoolean("notif_reward");
                    payDue_status=jsonObject.getBoolean("notif_payment_due");
                    credit_status=jsonObject.getBoolean("notif_credit_limit");

                    if (purchases_status)
                        bt_purchases.setChecked(true);
                    else
                        bt_purchases.setChecked(false);

                    if (rewards_status)
                        bt_rewards.setChecked(true);
                    else
                        bt_rewards.setChecked(false);

                    if (payDue_status)
                        bt_pay_due.setChecked(true);
                    else
                        bt_pay_due.setChecked(false);

                    if (credit_status)
                        bt_cre_limit.setChecked(true);
                    else
                        bt_cre_limit.setChecked(false);


                }catch (JSONException e) {
                    e.printStackTrace();

                }
                appProgerssDialog.Dismiss();


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

    public void setSettings(String obj){

        new HTTP_PATCH_AUTH(ApiConstant.SETTINGS,obj) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);

                    if(jsonObject.getString("status").equals("ok")){

                   /*     if (purchases_status) {
                            bt_purchases.setChecked(true);
                        }else {
                            bt_purchases.setChecked(false);
                        }


                        if (rewards_status) {
                            bt_rewards.setChecked(true);
                        }
                        else {
                            bt_rewards.setChecked(false);
                        }



                        if (payDue_status) {
                            bt_pay_due.setChecked(true);
                        }
                        else {
                            bt_pay_due.setChecked(false);
                        }



                        if (credit_status) {
                            bt_cre_limit.setChecked(true);
                        }
                        else {
                            bt_cre_limit.setChecked(false);
                        }*/
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected void OnErrorApi(String Error) {

            }

            @Override
            protected void OnHttPError(String HttpError) {

            }

            @Override
            protected void OnResponseCode(int code) {

            }
        };
    }

}

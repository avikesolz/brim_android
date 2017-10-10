package com.brim;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.LimitAdapter;
import com.brim.ApiHelper.HTTP_GET_AUTH;
import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpgradeLimit extends AppCompatActivity {

    TextView txt_limit,button_update,txt_limit_header,txt_header,button_ChngPin;
    public TextView txt_crelimit;
    ImageView back_button;
    LinearLayout ll_primary,ll_family;

    ArrayList<String> listLimit=null;
    String limit="";

    AppProgerssDialog appProgerssDialog;
    NetworkChecking networkChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_limit);

        appProgerssDialog=new AppProgerssDialog(this);
        networkChecking=new NetworkChecking(this);

        button_ChngPin= (TextView) findViewById(R.id.button_ChngPin);
        txt_limit= (TextView) findViewById(R.id.txt_limit);
        txt_crelimit = (TextView) findViewById(R.id.txt_crelimit);
        button_update= (TextView) findViewById(R.id.button_update);
        txt_header= (TextView) findViewById(R.id.txt_header);
        txt_limit_header= (TextView) findViewById(R.id.txt_limit_header);
        ll_family= (LinearLayout) findViewById(R.id.ll_family);
        ll_primary= (LinearLayout) findViewById(R.id.ll_primary);

        ll_family.setVisibility(View.GONE);
        ll_primary.setVisibility(View.GONE);

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().getExtras().getString("from").equals("family")){

            ll_family.setVisibility(View.VISIBLE);
            txt_limit_header.setText("UPDATE CARD INFORMATION");
            txt_header.setText("Update Family Card");
            txt_limit.setText(getIntent().getExtras().getString("limit"));


        }else{

            ll_primary.setVisibility(View.VISIBLE);
            txt_limit_header.setText("CREDIT LIMIT OPTION");
            txt_header.setText("Credit Limit");



            if (networkChecking.isConnectingToInternet() == true) {

                getLimits();

            } else {
                Toast.makeText(UpgradeLimit.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }


        }


        ll_primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog=new Dialog(UpgradeLimit.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_province_layout);

                int height= (int) (getWindow().getWindowManager().getDefaultDisplay().getHeight()/2.2);
                int width= (int) (getWindow().getWindowManager().getDefaultDisplay().getWidth()/1.3);

                dialog.getWindow().setLayout(width,height);
                dialog.setCanceledOnTouchOutside(false);

                RecyclerView rv_province=(RecyclerView) dialog.findViewById(R.id.rv_province);
                rv_province.setHasFixedSize(true);
                rv_province.setLayoutManager(new LinearLayoutManager(UpgradeLimit.this));

                if(listLimit.size()>0) {
                    LimitAdapter limitAdapter = new LimitAdapter(dialog, listLimit, UpgradeLimit.this);
                    rv_province.setAdapter(limitAdapter);
                }

                dialog.show();

            }
        });


        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().getExtras().getString("from").equals("family")){

                    limit=txt_limit.getText().toString();

                }else{

                    limit= txt_crelimit.getText().toString().substring(1, txt_crelimit.getText().toString().length());

                }

                if (limit.equals("")){


                    if(getIntent().getExtras().getString("from").equals("family")){

                        txt_limit.setHint("Enter Limit");
                        txt_limit.setHintTextColor(Color.RED);
                    }else{

                        txt_crelimit.setHint("Select Limit");
                        txt_crelimit.setHintTextColor(Color.RED);
                    }
                }else {

                    if (networkChecking.isConnectingToInternet() == true) {

                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("credit_limit", limit);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(getIntent().getExtras().getString("from").equals("family")){

                            updateLimit(jsonObject.toString());

                        }else{

                            updatePrimaryLimit(jsonObject.toString());

                        }

                    } else {
                        Toast.makeText(UpgradeLimit.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_ChngPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(UpgradeLimit.this,ChangePin.class);
                intent.putExtra("card",getIntent().getExtras().getString("card_id"));
                startActivity(intent);
            }
        });

    }

    public void updatePrimaryLimit(String obj){
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_POST_AUTH(ApiConstant.CARDS+"/"+ BrimApplication.getInstnace().GetCardId()+"/limit",obj){

            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(UpgradeLimit.this,"Credit Limit updated",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(UpgradeLimit.this,"Something wrong",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                appProgerssDialog.Dismiss();

            }
        };



    }

    public void updateLimit(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();


              new HTTP_PATCH_AUTH(ApiConstant.CARDS+"/"+ BrimApplication.getInstnace().GetCardId()+"/"+ApiConstant.FAMILY_CARDS+"/"
                      +getIntent().getExtras().getString("card_id")+"/limit", obj) {
                @Override
                protected void OnSucess(String Response) {

                    try {
                        JSONObject jsonObject=new JSONObject(Response);
                        if(jsonObject.getString("status").equals("ok")){
                            Toast.makeText(UpgradeLimit.this,"Credit Limit updated",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(UpgradeLimit.this,"Something wrong",Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
                    appProgerssDialog.Dismiss();

                }
            };
    }

    public void getLimits(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();


        new HTTP_GET_AUTH(ApiConstant.TRANSACTION+BrimApplication.getInstnace().GetCardId()+"/limit/option") {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONArray jsonArray=new JSONArray(Response);

                    if(jsonArray.length()>0){

                        listLimit=new ArrayList<>();

                        for (int i=0;i<jsonArray.length();i++){
                            listLimit.add(jsonArray.getString(i));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

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
            protected void OnResponseCode(int Code) {
                appProgerssDialog.Dismiss();

            }
        };
    }

}

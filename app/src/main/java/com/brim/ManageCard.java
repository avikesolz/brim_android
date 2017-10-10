package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManageCard extends AppCompatActivity {

    ImageView back_button;
    TextView txt_card_no,txt_exp_date,txt_limit,txt_member;
    RelativeLayout button_adduser,button_inc_limit,button_chngPin,button_balTransfer;

    AppProgerssDialog appProgerssDialog;
    NetworkChecking networkChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_card);

        appProgerssDialog=new AppProgerssDialog(this);
        networkChecking=new NetworkChecking(this);

        txt_card_no= (TextView) findViewById(R.id.txt_card_no);
        txt_exp_date= (TextView) findViewById(R.id.txt_exp_date);
        txt_limit= (TextView) findViewById(R.id.txt_limit);
        txt_member= (TextView) findViewById(R.id.txt_member);

        back_button= (ImageView) findViewById(R.id.back_button);

        button_adduser= (RelativeLayout) findViewById(R.id.button_adduser);
        button_inc_limit= (RelativeLayout) findViewById(R.id.button_inc_limit);
        button_chngPin= (RelativeLayout) findViewById(R.id.txt_crelimit);
        button_balTransfer= (RelativeLayout) findViewById(R.id.button_balTransfer);

        button_balTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManageCard.this,BalanceTransfer.class);
                //intent.putExtra("card",BrimApplication.getInstnace().GetCardId());
                startActivity(intent);
            }
        });


        button_chngPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ManageCard.this,ChangePin.class);
                intent.putExtra("card",BrimApplication.getInstnace().GetCardId());
                startActivity(intent);

            }
        });


        button_inc_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ManageCard.this, UpgradeLimit.class);
                intent.putExtra("from","primary");
                intent.putExtra("limit", "");
                intent.putExtra("card_id","");

                startActivity(intent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txt_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageCard.this,FamilyCards.class));
            }
        });


        button_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManageCard.this,AddUser.class);
                startActivity(intent);
            }
        });

        if(networkChecking.isConnectingToInternet()==true) {

            getCardDetails();

        }else{
            Toast.makeText(ManageCard.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
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

                    txt_limit.setText("$"+Object.getString("credit_limit"));
                    txt_card_no.setText(Object.getString("last_4_digit"));
                    txt_exp_date.setText(Object.getString("expiration_date"));

                }catch (JSONException e)
                {
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

    public void getFamilyCardDetails(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.CARDS+"/"+ BrimApplication.getInstnace().GetCardId()+"/family_cards") {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONArray jsonArray=new JSONArray(Response);
                    txt_member.setText(""+jsonArray.length());
                    appProgerssDialog.Dismiss();


                }catch (JSONException e)
                {
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


    @Override
    protected void onResume() {
        super.onResume();

        if(networkChecking.isConnectingToInternet()==true) {

            getFamilyCardDetails();

        }else{
            Toast.makeText(ManageCard.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
    }
}

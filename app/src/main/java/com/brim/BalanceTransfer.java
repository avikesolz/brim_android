package com.brim;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

public class BalanceTransfer extends AppCompatActivity {


    ImageView back_button;
    TextView button_update;
    EditText et_crtPin,et_conPin,et_newPin;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_transfer);


        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        et_crtPin= (EditText) findViewById(R.id.et_crtPin);
        et_conPin= (EditText) findViewById(R.id.et_conPin);
        et_newPin= (EditText) findViewById(R.id.et_newPin);

        button_update= (TextView) findViewById(R.id.button_update);

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_crtPin.getText().toString().equals("")){
                    et_crtPin.setText("");
                    et_crtPin.setHint("Enter Card Number");
                    et_crtPin.setHintTextColor(Color.RED);
                }else{

                    if(et_crtPin.getText().toString().length()<16){
                        et_crtPin.setText("");
                        et_crtPin.setHint("Card Number should be 16 Digits");
                        et_crtPin.setHintTextColor(Color.RED);
                    }else{

                        if(et_newPin.getText().toString().equals("")){
                            et_newPin.setText("");

                            et_newPin.setHint("Enter Name");
                            et_newPin.setHintTextColor(Color.RED);
                        }else{

                                if(et_conPin.getText().toString().equals("")){
                                    et_conPin.setText("");
                                    et_conPin.setHint("Enter Amount");
                                    et_conPin.setHintTextColor(Color.RED);
                                }else{

                                    if(et_conPin.getText().toString().equals("0")){
                                        et_conPin.setText("");

                                        et_conPin.setHint("Amount greater than 0");
                                        et_conPin.setHintTextColor(Color.RED);
                                    }else{


                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("payee_card_number",et_crtPin.getText());
                                                jsonObject.put("payee_name",et_newPin.getText());
                                                jsonObject.put("amount",et_conPin.getText());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if(networkChecking.isConnectingToInternet()==true) {
                                                balTransfer(jsonObject.toString());
                                            }else{
                                                Toast.makeText(BalanceTransfer.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                                            }


                                    }
                                }

                            }

                    }
                }

            }
        });
    }

    public void balTransfer(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_POST_AUTH(ApiConstant.TRANSACTION+ BrimApplication.getInstnace().GetCardId()+"/transfer", obj){

            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(BalanceTransfer.this,"Balance Transfer Completed",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(BalanceTransfer.this,"Something wrong",Toast.LENGTH_SHORT).show();

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

}

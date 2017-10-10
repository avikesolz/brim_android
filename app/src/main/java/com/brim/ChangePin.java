package com.brim;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePin extends AppCompatActivity {

    ImageView back_button;
    TextView button_update;
    EditText et_crtPin,et_conPin,et_newPin;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chnage_pin);

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
                    et_crtPin.setHint("Enter Current Pin");
                    et_crtPin.setHintTextColor(Color.RED);
                }else{

                    if(et_crtPin.getText().toString().length()<6){
                        et_crtPin.setText("");
                        et_crtPin.setHint("Your Pin should be minimum Six Digits");
                        et_crtPin.setHintTextColor(Color.RED);
                    }else{

                        if(et_newPin.getText().toString().equals("")){
                            et_newPin.setText("");

                            et_newPin.setHint("Enter New Pin");
                            et_newPin.setHintTextColor(Color.RED);
                        }else{

                            if(et_newPin.getText().toString().length()<6){
                                et_newPin.setText("");

                                et_newPin.setHint("Your Pin should be minimum Six Digits");
                                et_newPin.setHintTextColor(Color.RED);
                            }else{

                                if(et_conPin.getText().toString().equals("")){
                                    et_conPin.setText("");

                                    et_conPin.setHint("Enter Confirm Pin");
                                    et_conPin.setHintTextColor(Color.RED);
                                }else{

                                    if(et_conPin.getText().toString().length()<6){
                                        et_conPin.setText("");

                                        et_conPin.setHint("Your Pin should be minimum Six Digits");
                                        et_conPin.setHintTextColor(Color.RED);
                                    }else{

                                        if (et_newPin.getText().toString().equals(et_conPin.getText().toString())){

                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("current_pin",et_crtPin.getText());
                                                jsonObject.put("new_pin",et_newPin.getText());
                                                jsonObject.put("confirm_new_pin",et_conPin.getText());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if(networkChecking.isConnectingToInternet()==true) {
                                                updatePassword(jsonObject.toString());
                                            }else{
                                                Toast.makeText(ChangePin.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                                            }

                                        }else{
                                            Toast.makeText(ChangePin.this,"Confrim Pin does not match with new Pin",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }

                            }
                        }

                    }
                }

            }
        });
    }

    public void updatePassword(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_PATCH_AUTH(ApiConstant.TRANSACTION+getIntent().getExtras().getString("card")+"/pin", obj) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(ChangePin.this,"Changed Pin successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(ChangePin.this,"Something wrong",Toast.LENGTH_SHORT).show();

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

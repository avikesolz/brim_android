package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.ApiHelper.HTTP_POST;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Font.AxiformaBookEditText;
import com.brim.Utils.AppAlert;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class RemoveAccount extends AppCompatActivity {

    TextView txt_name;
    CardView button_proceed;
    LinearLayout button_remove;


    AppProgerssDialog progressDialog;
    NetworkChecking networkChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_account);

        progressDialog=new AppProgerssDialog(this);
        progressDialog.SetMessage("Please Wait...");
        progressDialog.SetTitle("Login");

        networkChecking=new NetworkChecking(this);


        txt_name= (TextView) findViewById(R.id.txt_name);
        button_proceed= (CardView) findViewById(R.id.button_proceed);
        button_remove= (LinearLayout) findViewById(R.id.button_remove);

        txt_name.setText("Welcome, "+ BrimApplication.getInstnace().GetName());

        button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BrimApplication.getInstnace().SetAuthToken("");
                BrimApplication.getInstnace().SetCardId("");
                BrimApplication.getInstnace().SetUserId("");
                BrimApplication.getInstnace().SetEmail("");
                BrimApplication.getInstnace().SetPass("");
                BrimApplication.getInstnace().SetName("");
                BrimApplication.getInstnace().SetPin("");
                BrimApplication.getInstnace().SetPassType("");

                Paper.clear(RemoveAccount.this);

                startActivity(new Intent(RemoveAccount.this, LoginActivity.class));
                finishAffinity();

            }
        });

        button_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(networkChecking.isConnectingToInternet()) {

                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("email",BrimApplication.getInstnace().GetEmail());
                        jsonObject.put("password",BrimApplication.getInstnace().GetPass());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    new HTTP_POST(ApiConstant.LOGIN, jsonObject.toString()) {
                        @Override
                        protected void OnSucess(String Response) {
                            progressDialog.Dismiss();
                            try {
                                JSONObject OBject = new JSONObject(Response);

                                if(OBject.has("code") && OBject.getString("code").equals("invalid_credentials"))
                                {
                                    new AppAlert(RemoveAccount.this).Error(OBject.getString("message"));
                                }else  if(OBject.has("code") && OBject.getString("code").equals("validation_failed")){
                                    new AppAlert(RemoveAccount.this).Error(OBject.getJSONObject("errors").getJSONObject("email").getString("message"));
                                }
                                else {
                                    BrimApplication.getInstnace().SetAuthToken(OBject.getString("auth_token"));
                                    BrimApplication.getInstnace().SetUserId(OBject.getString("id"));
                                    //BrimApplication.getInstnace().SetEmail(((AxiformaBookEditText) findViewById(R.id.EDX_UserName)).getText().toString());
                                    //BrimApplication.getInstnace().SetPass(((AxiformaBookEditText) findViewById(R.id.Edx_Password)).getText().toString());

                             /*       if(BrimApplication.getInstnace().GetPassType().equals("password")){

                                        startActivity(new Intent(RemoveAccount.this, PasswordActivity.class));
                                    }
                                    else if(BrimApplication.getInstnace().GetPassType().equals("pin"))  {

                                        startActivity(new Intent(RemoveAccount.this, FinalPinActivity.class));

                                    }
                                    else if(BrimApplication.getInstnace().GetPassType().equals("pattern"))  {

                                        startActivity(new Intent(RemoveAccount.this, PatternLockFinalScreen.class));

                                    }else{
                                        startActivity(new Intent(RemoveAccount.this, ChooseYourLoginWithOption.class));

                                    }
                                    finishAffinity();*/

                                    Log.d("Passkey-Type","-----------------"+BrimApplication.getInstnace().GetPassType());

                                    if(BrimApplication.getInstnace().GetPassType().equals("password")){

                                        Intent intent=new Intent(RemoveAccount.this, PasswordActivity.class);
                                        intent.putExtra("from","Autologin");
                                        startActivity(intent);

                                    }   else if(BrimApplication.getInstnace().GetPassType().equals("pin"))  {

                                        Intent intent=new Intent(RemoveAccount.this, FinalPinActivity.class);
                                        intent.putExtra("from","Autologin");
                                        startActivity(intent);

                                    }
                                    else if(BrimApplication.getInstnace().GetPassType().equals("pattern"))  {

                                        Intent intent=new Intent(RemoveAccount.this, PatternLockFinalScreen.class);
                                        intent.putExtra("from","Autologin");
                                        startActivity(intent);

                                    }else if(BrimApplication.getInstnace().GetPassType().equals("touch"))  {

                                        Intent intent=new Intent(RemoveAccount.this, FingerPrintFinalActivity.class);
                                        intent.putExtra("from","Autologin");
                                        startActivity(intent);

                                    }else{

                                        Intent intent=new Intent(RemoveAccount.this, ChooseYourLoginWithOption.class);
                                        intent.putExtra("from","login");
                                        startActivity(intent);

                                    }
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        protected void OnErrorApi(String Error) {
                            progressDialog.Dismiss();
                            new AppAlert(RemoveAccount.this).Error(Error);
                        }

                        @Override
                        protected void OnHttPError(String HttpError) {
                            progressDialog.Dismiss();

                        }
                    };

                }else{
                    Toast.makeText(RemoveAccount.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

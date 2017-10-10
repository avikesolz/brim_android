package com.brim;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.brim.ApiHelper.HTTP_GET_AUTH;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    NetworkChecking networkChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        networkChecking=new NetworkChecking(this);


        if (BrimApplication.getInstnace().GetAuthToken().equals("")){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();

                }
            },500);

        }else{

            if(BrimApplication.getInstnace().GetUserId().equals("")){

                startActivity(new Intent(SplashActivity.this,RemoveAccount.class));
                finish();

            }else {

                if (networkChecking.isConnectingToInternet() == true) {
                    sessionCheck();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }

        }



    }

    public void sessionCheck(){


        new HTTP_GET_AUTH(ApiConstant.ME) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);

                    BrimApplication.getInstnace().SetName(jsonObject.getString("first_name"));

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
            protected void OnResponseCode(int Code) {
                if(Code==200){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            /*startActivity(new Intent(SplashActivity.this,BaseActivity.class));
                            finish();*/

                            Log.d("Passkey-Type","-----------------"+BrimApplication.getInstnace().GetPassType());

                            if(BrimApplication.getInstnace().GetPassType().equals("password")){

                                Intent intent=new Intent(SplashActivity.this, PasswordActivity.class);
                                intent.putExtra("from","Autologin");
                                startActivity(intent);

                            }   else if(BrimApplication.getInstnace().GetPassType().equals("pin"))  {

                                Intent intent=new Intent(SplashActivity.this, FinalPinActivity.class);
                                intent.putExtra("from","Autologin");
                                startActivity(intent);

                            }
                            else if(BrimApplication.getInstnace().GetPassType().equals("pattern"))  {

                                Intent intent=new Intent(SplashActivity.this, PatternLockFinalScreen.class);
                                intent.putExtra("from","Autologin");
                                startActivity(intent);

                            }else if(BrimApplication.getInstnace().GetPassType().equals("touch"))  {

                                Intent intent=new Intent(SplashActivity.this, FingerPrintFinalActivity.class);
                                intent.putExtra("from","login");
                                startActivity(intent);

                            }else{

                                Intent intent=new Intent(SplashActivity.this, ChooseYourLoginWithOption.class);
                                intent.putExtra("from","login");
                                startActivity(intent);

                            }
                            finish();

                        }
                    },500);
                }else{
                    BrimApplication.getInstnace().SetAuthToken("");
                    BrimApplication.getInstnace().SetCardId("");
                    BrimApplication.getInstnace().SetUserId("");
                    BrimApplication.getInstnace().SetEmail("");
                    BrimApplication.getInstnace().SetPass("");
                    BrimApplication.getInstnace().SetName("");
                    BrimApplication.getInstnace().SetPin("");
                    BrimApplication.getInstnace().SetPassType("");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                            finish();

                        }
                    },500);
                }
            }
        };

    }
}

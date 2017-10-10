package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.brim.AppContant.BrimApplication;

public class PasswordActivity extends AppCompatActivity {

    LinearLayout Button_Submit;
    EditText PasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Button_Submit = (LinearLayout) findViewById(R.id.ll_submit);
        PasswordText = (EditText) findViewById(R.id.et_password);
/*
        if (getIntent().getExtras()!=null) {

            Log.d("from", "-------------------****-----------------" + getIntent().getExtras().getString("from"));
        }*/


        Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (PasswordText.getText().toString().trim().equals(BrimApplication.getInstnace().GetPass())){

                    if(getIntent().getExtras().getString("from").equals("login")) {

                        //Log.d("from","--------------------------login");


                        BrimApplication.getInstnace().SetPassType("password");
                        startActivity(new Intent(PasswordActivity.this, BaseActivity.class));
                        finish();

                    }else  if(getIntent().getExtras().getString("from").equals("account")) {

                        //Log.d("from","--------------------------account");
                        Intent intent=new Intent(PasswordActivity.this, ChooseYourLoginWithOption.class);
                        intent.putExtra("from","account");
                        startActivity(intent);

                        finish();

                    }
                    else  if(getIntent().getExtras().getString("from").equals("newsetup")) {
                        //Log.d("from","--------------------------newsetup");

                        BrimApplication.getInstnace().SetPassType("password");

                        finish();
                    }

                }else{
                    Toast.makeText(PasswordActivity.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getExtras().getString("from").equals("Autologin")) {

            finish();

        } else if(getIntent().getExtras().getString("from").equals("account")) {

            finish();

        }else  if(getIntent().getExtras().getString("from").equals("login")) {
            Intent intent=new Intent(PasswordActivity.this, ChooseYourLoginWithOption.class);
            intent.putExtra("from","login");
            startActivity(intent);
            finish();

        }else  if(getIntent().getExtras().getString("from").equals("newsetup")) {

            Intent intent=new Intent(PasswordActivity.this, ChooseYourLoginWithOption.class);
            intent.putExtra("from","account");
            startActivity(intent);
            finish();

        }
    }

}

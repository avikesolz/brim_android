package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brim.AppContant.BrimApplication;

public class ChooseYourLoginWithOption extends AppCompatActivity {

    RelativeLayout Button_Pattern,Button_Password,Button_Pin,Button_Touch;
    TextView txt_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_login_with_option);

        txt_username= (TextView) findViewById(R.id.txt_username);

        txt_username.setText("Welcome, "+ BrimApplication.getInstnace().GetName()+"!");

        Button_Pattern = (RelativeLayout) findViewById(R.id.rl_pattern);

        Button_Password = (RelativeLayout) findViewById(R.id.rl_password);

        Button_Pin = (RelativeLayout) findViewById(R.id.rl_pin);

        Button_Touch = (RelativeLayout) findViewById(R.id.rl_touch);


        Button_Pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(ChooseYourLoginWithOption.this, PatternLock.class);
                if(getIntent().getExtras().getString("from").equals("login")){

                    intent.putExtra("from","login");

                }  else  if(getIntent().getExtras().getString("from").equals("account")) {

                    intent.putExtra("from","newsetup");

                }

                startActivity(intent);

                finish();


            }
        });


        Button_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(ChooseYourLoginWithOption.this, PasswordActivity.class);
                if(getIntent().getExtras().getString("from").equals("login")){

                    intent.putExtra("from","login");

                } else  if(getIntent().getExtras().getString("from").equals("account")) {

                    intent.putExtra("from","newsetup");

                }
                startActivity(intent);

                finish();

            }
        });

        Button_Pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseYourLoginWithOption.this, PinActivity.class);
                if(getIntent().getExtras().getString("from").equals("login")){

                    intent.putExtra("from","login");

                } else  if(getIntent().getExtras().getString("from").equals("account")) {

                    intent.putExtra("from","newsetup");

                }
                startActivity(intent);

                finish();

            }
        });

        Button_Touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseYourLoginWithOption.this, FingerPrintActivity.class);
                if(getIntent().getExtras().getString("from").equals("login")){

                    intent.putExtra("from","login");

                } else  if(getIntent().getExtras().getString("from").equals("account")) {

                    intent.putExtra("from","newsetup");

                }
                startActivity(intent);

                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

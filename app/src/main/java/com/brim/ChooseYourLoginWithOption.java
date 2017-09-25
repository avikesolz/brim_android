package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brim.AppContant.BrimApplication;

public class ChooseYourLoginWithOption extends AppCompatActivity {

    RelativeLayout Button_Pattern,Button_Password,Button_Pin;
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


        Button_Pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ChooseYourLoginWithOption.this,PatternLock.class));
                //finish();

            }
        });


        Button_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ChooseYourLoginWithOption.this,PasswordActivity.class));
               // finish();

            }
        });

        Button_Pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ChooseYourLoginWithOption.this,PinActivity.class));
               // finish();

            }
        });

    }
}

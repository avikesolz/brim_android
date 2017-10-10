package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class FingerPrintActivity extends AppCompatActivity {

    LinearLayout Button_Fiingering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);

        Button_Fiingering = (LinearLayout) findViewById(R.id.ll_fingerprint);

        Button_Fiingering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(FingerPrintActivity.this, FingerPrintFinalActivity.class);

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

        Intent intent=new Intent(FingerPrintActivity.this,ChooseYourLoginWithOption.class);

        if(getIntent().getExtras().getString("from").equals("login")){

            intent.putExtra("from","login");

        } else  if(getIntent().getExtras().getString("from").equals("newsetup")) {

            intent.putExtra("from","account");

        }

        startActivity(intent);

        finish();
    }
}

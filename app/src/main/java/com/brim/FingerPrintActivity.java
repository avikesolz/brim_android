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

                startActivity(new Intent(FingerPrintActivity.this, FingerPrintFinalActivity.class));

            }
        });

    }
}

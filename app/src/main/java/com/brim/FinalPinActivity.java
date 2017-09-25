package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;

public class FinalPinActivity extends AppCompatActivity {


    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;


    LinearLayout Button_Submit;

    String TempPin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_pin);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view_final);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots_final);

        Button_Submit = (LinearLayout) findViewById(R.id.ll_submit_pin_final);
        /////////////////////////////////////////////////////////////////////////////////////////////
        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        mPinLockView.setPinLength(4);


        Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TempPin != null) {

                    if (TempPin.length() == 4) {

                        if (BrimApplication.getInstnace().GetPin().equals(TempPin)){

                            startActivity(new Intent(FinalPinActivity.this, BaseActivity.class));

                            finishAffinity();

                        }else {

                            Toast.makeText(FinalPinActivity.this,"Wrong Pin",Toast.LENGTH_SHORT).show();

                        }


                    }else {

                        Toast.makeText(FinalPinActivity.this,"Fill up all fields",Toast.LENGTH_SHORT).show();

                    }

                }else if(TempPin==null){

                    Toast.makeText(FinalPinActivity.this,"Please enter confirm Pin",Toast.LENGTH_SHORT).show();

                }


            }
        });


    }


    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {

            if (pin.length() == 4) {


                TempPin = pin;


            }else {

                TempPin = null;

            }


        }

        @Override
        public void onEmpty() {

        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

            if (pinLength < 4){

                TempPin = null;

            }

        }
    };

}

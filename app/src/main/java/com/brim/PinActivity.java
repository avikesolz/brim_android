package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;

public class PinActivity extends AppCompatActivity {


    PinLockView mPinLockView;
    IndicatorDots mIndicatorDots;


    TextView HeaderText;

    LinearLayout Button_Submit;

    String TempPin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BrimApplication.getInstnace().GetPin().equals("")){

            setContentView(R.layout.activity_pin);


            mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
            mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

            HeaderText = (TextView) findViewById(R.id.tv_header);
            Button_Submit = (LinearLayout) findViewById(R.id.ll_submit_pin);
            /////////////////////////////////////////////////////////////////////////////////////////////
            mPinLockView.attachIndicatorDots(mIndicatorDots);
            mPinLockView.setPinLockListener(mPinLockListener);
            mPinLockView.setPinLength(4);


            Button_Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (TempPin != null) {

                        if (TempPin.length() == 4) {

                            BrimApplication.getInstnace().SetPin(TempPin);

                            startActivity(new Intent(PinActivity.this, FinalPinActivity.class));

                        }

                    }


                }
            });

        }else {
            startActivity(new Intent(PinActivity.this,FinalPinActivity.class));
        }


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


package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


        Log.d("from","-----------------------"+getIntent().getExtras().getString("from"));

        Log.d("PIN","-----------------------"+BrimApplication.getInstnace().GetPin());

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

                            BrimApplication.getInstnace().SetPassType("pin");

                            if(getIntent().getExtras().getString("from").equals("login")) {

                                startActivity(new Intent(FinalPinActivity.this, BaseActivity.class));
                                finishAffinity();


                            }else  if(getIntent().getExtras().getString("from").equals("Autologin")) {

                                startActivity(new Intent(FinalPinActivity.this, BaseActivity.class));
                                finishAffinity();


                            }else  if(getIntent().getExtras().getString("from").equals("account")) {

                                Intent intent=new Intent(FinalPinActivity.this, ChooseYourLoginWithOption.class);
                                intent.putExtra("from","account");
                                startActivity(intent);
                                finish();


                            }else  if(getIntent().getExtras().getString("from").equals("newsetup")) {

                                finish();

                            }


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(FinalPinActivity.this, PinActivity.class);

        if(getIntent().getExtras().getString("from").equals("login")) {

            intent.putExtra("from","login");
            startActivity(intent);

        }else  if(getIntent().getExtras().getString("from").equals("newsetup")) {

            intent.putExtra("from","newsetup");
            startActivity(intent);

        }
    }
}

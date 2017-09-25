package com.brim;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class PatternLock extends AppCompatActivity {

    String save_pattern_key = "pattern_code";

    PatternLockView mPatternLockView;

    LinearLayout Button_StartOver, Button_Save;

    TextView Button_StartOver_Text, Button_Save_Text;

    String final_pattern;

    int SaveButton_Clicked = 0, StartOverButton_Clicked = 0;

    boolean PatterMatched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);

        if (save_pattern != null && !save_pattern.equals(null)) {

            Log.v("PatternLockIn::::", "If");

            Intent intent = new Intent(PatternLock.this,PatternLockFinalScreen.class);
            startActivity(intent);
            finish();


        } else {

            setContentView(R.layout.activity_pattern_lock);

            mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);

            Button_StartOver = (LinearLayout) findViewById(R.id.ll_startover);

            Button_Save = (LinearLayout) findViewById(R.id.ll_save);

            Button_StartOver_Text = (TextView) findViewById(R.id.tv_startover);

            Button_Save_Text = (TextView) findViewById(R.id.tv_save);

            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {

                    if (SaveButton_Clicked == 0) {

                        Log.v("PatternLockIn::::", "If");


                        final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);

                        Button_Save.setVisibility(View.VISIBLE);

                        Button_StartOver.setVisibility(View.VISIBLE);

                    } else {

                        Log.v("PatternLockIn::::", "Else");


                        final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);

                        String save_pattern = Paper.book().read(save_pattern_key);


                        if (final_pattern.equals(save_pattern)) {


                            PatterMatched = true;

                        } else {

                            //mPatternLockView.setWrongStateColor(Color.parseColor("#cf0404"));
                            Toast.makeText(PatternLock.this, "Incorrect Pattern!", Toast.LENGTH_SHORT).show();
                            mPatternLockView.clearPattern();

                        }

                    }


                }

                @Override
                public void onCleared() {

                }
            });

            Button_Save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (SaveButton_Clicked == 0) {

                        Paper.book().write(save_pattern_key, final_pattern);

                        Button_StartOver_Text.setText("CANCEL");

                        Button_Save_Text.setText("CONFIRM");

                        mPatternLockView.clearPattern();

                        SaveButton_Clicked = 1;
                        StartOverButton_Clicked = 1;


                    } else {


                        if (PatterMatched) {

                            startActivity(new Intent(PatternLock.this, BaseActivity.class));
                            finishAffinity();

                        }


                    }


//                    Toast.makeText(MainActivity.this,"Pattern Saved!",Toast.LENGTH_SHORT).show();
//
//                    mPatternLockView.clearPattern();
//
//                    Intent intent = new Intent(MainActivity.this,UnlockScreen.class);
//                    startActivity(intent);
//                    finish();

                }
            });


            Button_StartOver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (StartOverButton_Clicked == 0) {

                        mPatternLockView.clearPattern();

                        final_pattern = "";

                    } else {

                        Paper.clear(PatternLock.this);
                        Paper.delete(save_pattern_key);

                        finish();

                    }


                }
            });

        }

    }
}

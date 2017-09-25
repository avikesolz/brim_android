package com.brim;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.brim.AppContant.BrimApplication;

import java.util.List;

import io.paperdb.Paper;

public class PatternLockFinalScreen extends AppCompatActivity {

    String save_pattern_key = "pattern_code";

    PatternLockView mPatternLockView;
    LinearLayout button_signin;
    TextView txt_name;

    String final_pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock_final_screen);

        button_signin= (LinearLayout) findViewById(R.id.button_signin);
        txt_name= (TextView) findViewById(R.id.txt_name);
        txt_name.setText("Welcome back, "+ BrimApplication.getInstnace().GetName());

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view_final);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);

        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);

                if (final_pattern.equals(save_pattern)) {


                    startActivity(new Intent(PatternLockFinalScreen.this,BaseActivity.class));

                    finishAffinity();


                    mPatternLockView.clearPattern();

                }
                else {

                    //mPatternLockView.setWrongStateColor(Color.parseColor("#cf0404"));
                    Toast.makeText(PatternLockFinalScreen.this,"Incorrect Pattern!",Toast.LENGTH_SHORT).show();
                    mPatternLockView.clearPattern();

                }

            }

            @Override
            public void onCleared() {

            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatternLockFinalScreen.this,ChooseYourLoginWithOption.class));
                finishAffinity();
            }
        });

    }
}

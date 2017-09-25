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


        Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.d("check",""+PasswordText.getText().toString().trim()+"/////"+BrimApplication.getInstnace().GetPass());

                if (PasswordText.getText().toString().trim().equals(BrimApplication.getInstnace().GetPass())){

                    startActivity(new Intent(PasswordActivity.this,BaseActivity.class));

                }else{
                    Toast.makeText(PasswordActivity.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}

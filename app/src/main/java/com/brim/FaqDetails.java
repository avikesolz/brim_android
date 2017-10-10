package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.FaqAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.Pojo.FaqSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FaqDetails extends AppCompatActivity {

    TextView txt_qust,txt_qust2,txt_ans;
    ImageView back_button;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_details);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        back_button= (ImageView) findViewById(R.id.back_button);
        txt_qust= (TextView) findViewById(R.id.txt_qust);
        txt_qust2= (TextView) findViewById(R.id.txt_qust2);
        txt_ans= (TextView) findViewById(R.id.txt_ans);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if(networkChecking.isConnectingToInternet()==true) {

            getAns();

        }else{
            Toast.makeText(FaqDetails.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }

    }

    public void getAns(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.FAQ+"/"+getIntent().getExtras().getString("id")) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONObject jsonObject=new JSONObject(Response);

                    txt_qust.setText(jsonObject.getString("question"));
                    txt_qust2.setText(jsonObject.getString("question"));
                    txt_ans.setText(jsonObject.getString("answer"));


                }catch (JSONException e)
                {
                    e.printStackTrace();

                }
                appProgerssDialog.Dismiss();


            }

            @Override
            protected void OnErrorApi(String Error) {
                Loger.MSG("@@ ON Erroe",""+Error);
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnHttPError(String HttpError) {
                appProgerssDialog.Dismiss();

            }
        };
    }

}

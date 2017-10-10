package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

public class Help extends AppCompatActivity {

    ImageView back_button;
    RecyclerView rv_faq;

    FaqAdapter faqAdapter;
    ArrayList<FaqSetGet> list;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        rv_faq= (RecyclerView) findViewById(R.id.rv_faq);
        rv_faq.setHasFixedSize(true);
        rv_faq.setLayoutManager(new LinearLayoutManager(this));

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(networkChecking.isConnectingToInternet()==true) {
            getFaq();

        }else{
            Toast.makeText(Help.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
    }


    public void getFaq(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.FAQ) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONArray jsonArray=new JSONArray(Response);

                    if (jsonArray.length()>0){

                        list=new ArrayList<>();

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            FaqSetGet faqSetGet=new FaqSetGet();
                            faqSetGet.setId(jsonObject.getString("id"));
                            faqSetGet.setQust(jsonObject.getString("question"));

                            list.add(faqSetGet);

                        }
                    }

                    if(list.size()>0){

                        if(faqAdapter==null){

                            faqAdapter=new FaqAdapter(Help.this,list);
                            rv_faq.setAdapter(faqAdapter);

                        }
                    }


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

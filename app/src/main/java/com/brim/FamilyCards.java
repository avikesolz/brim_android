package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.brim.Adapter.FamilyCardsAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Pojo.FamilyCardsSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FamilyCards extends AppCompatActivity {

    ImageView back_button;
    RecyclerView rv_cards;


    FamilyCardsAdapter familyCardsAdapter;
    ArrayList<FamilyCardsSetGet> list;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_cards);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);


        rv_cards= (RecyclerView) findViewById(R.id.rv_cards);
        rv_cards.setHasFixedSize(true);
        rv_cards.setLayoutManager(new LinearLayoutManager(this));

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void getFamilyCardDetails(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.CARDS+"/"+ BrimApplication.getInstnace().GetCardId()+"/family_cards") {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONArray jsonArray=new JSONArray(Response);

                    if(jsonArray.length()>0){

                        list=new ArrayList<>();

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            FamilyCardsSetGet familyCardsSetGet=new FamilyCardsSetGet();
                            familyCardsSetGet.setId(jsonObject.getString("id"));
                            familyCardsSetGet.setFirst_name(jsonObject.getString("first_name"));
                            familyCardsSetGet.setLast_name(jsonObject.getString("last_name"));
                            familyCardsSetGet.setEmail(jsonObject.getString("email"));
                            familyCardsSetGet.setCredit_limit(jsonObject.getString("credit_limit"));
                            familyCardsSetGet.setLast_4_digit(jsonObject.getString("last_4_digit"));

                            list.add(familyCardsSetGet);
                        }

                        if(list.size()>0){

                            if(familyCardsAdapter==null){

                                familyCardsAdapter=new FamilyCardsAdapter(FamilyCards.this,list);
                                rv_cards.setAdapter(familyCardsAdapter);

                            }
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

    @Override
    protected void onResume() {
        super.onResume();

        if(networkChecking.isConnectingToInternet()==true) {

            familyCardsAdapter=null;

            getFamilyCardDetails();

        }else{
            Toast.makeText(FamilyCards.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
    }
}

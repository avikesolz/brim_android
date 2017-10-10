package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.FaqAdapter;
import com.brim.Adapter.TravelAdapter;
import com.brim.ApiHelper.HTTP_DELETE_AUTH;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.Pojo.FaqSetGet;
import com.brim.Pojo.TravelSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TravelNotice extends AppCompatActivity {

    ImageView back_button;
    RecyclerView rv_travel;
    TextView button_add_travel;

    ArrayList<TravelSetGet> list;
    TravelAdapter travelAdapter;


    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_notice);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        button_add_travel= (TextView) findViewById(R.id.button_add_travel);
        back_button= (ImageView) findViewById(R.id.back_button);
        rv_travel= (RecyclerView) findViewById(R.id.rv_travel);
        rv_travel.setHasFixedSize(true);
        rv_travel.setLayoutManager(new LinearLayoutManager(this));

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_add_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TravelNotice.this, UpdateTravel.class);
                intent.putExtra("from","add_travel");

                startActivity(intent);
            }
        });

    }


    public void getTravelNotice(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRAVEL_NOTICES) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONArray jsonArray=new JSONArray(Response);

                    if (jsonArray.length()>0){

                        list=new ArrayList<>();

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject=jsonArray.getJSONObject(i);

                            TravelSetGet travelSetGet=new TravelSetGet();
                            travelSetGet.setId(jsonObject.getString("id"));
                            travelSetGet.setLocation(jsonObject.getString("location"));
                            travelSetGet.setDeparture_date(jsonObject.getString("departure_date"));
                            travelSetGet.setArrival_date(jsonObject.getString("arrival_date"));

                            list.add(travelSetGet);

                        }
                    }

                    if(list.size()>0){

                        if(travelAdapter==null){

                            rv_travel.setVisibility(View.VISIBLE);


                            travelAdapter=new TravelAdapter(TravelNotice.this,list);
                            rv_travel.setAdapter(travelAdapter);

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

    public void delete(String id, final int posi){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_DELETE_AUTH(ApiConstant.TRAVEL_NOTICES+"/"+id){

            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(TravelNotice.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();

                        if(travelAdapter!=null){
                            if(travelAdapter.list.size()>0){
                                travelAdapter.list.remove(posi);
                                travelAdapter.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(TravelNotice.this,"Something wrong",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnErrorApi(String Error) {
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnHttPError(String HttpError) {
                appProgerssDialog.Dismiss();

            }

            @Override
            protected void OnResponseCode(int Code) {
                appProgerssDialog.Dismiss();

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(networkChecking.isConnectingToInternet()==true) {

            rv_travel.setVisibility(View.INVISIBLE);

            travelAdapter=null;

            getTravelNotice();

        }else{
            Toast.makeText(TravelNotice.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
    }
}

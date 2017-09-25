package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.InstallmentAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Pojo.InstallmentSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SetUpInstallments extends AppCompatActivity {

    TextView txt_description,txt_amount;
    RecyclerView rv_installments;
    RelativeLayout button_setup;
    ImageView back_button;


    InstallmentAdapter installmentAdapter;

    String card_id,trans_id;
    String amount;
    public String plan="";
    public int posi=-1;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    ArrayList<InstallmentSetGet> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_installments);
        txt_amount= (TextView) findViewById(R.id.txt_amount);
        txt_description= (TextView) findViewById(R.id.txt_description);
        button_setup= (RelativeLayout) findViewById(R.id.button_setup);
        back_button= (ImageView) findViewById(R.id.back_button);


        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        card_id=BrimApplication.getInstnace().GetCardId();

        Intent intent=getIntent();

        amount=intent.getExtras().getString("amount");
        trans_id=intent.getExtras().getString("transaction_id");

        txt_description.setText(intent.getExtras().getString("des"));
        txt_amount.setText("$"+intent.getExtras().getString("amount"));
        rv_installments= (RecyclerView) findViewById(R.id.rv_installments);
        rv_installments.setHasFixedSize(true);
        rv_installments.setLayoutManager(new LinearLayoutManager(this));

        Log.d("Trans_Id",":::::::::::::::"+trans_id);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(posi==-1){

                    Toast.makeText(SetUpInstallments.this,"Choose any installment first",Toast.LENGTH_SHORT).show();

                }else{

                    if(networkChecking.isConnectingToInternet()==true) {


                        JSONObject object=new JSONObject();
                        try {
                            object.put("transaction_id",trans_id);
                            object.put("plan",plan);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String Body=object.toString();

                        Log.d("body",":::::::::::::::::::"+Body);

                        continueInstallment(ApiConstant.TRANSACTION+card_id+ApiConstant.INSTALLMENTS,Body);


                    }else{
                        Toast.makeText(SetUpInstallments.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                    }

                }

            /*    Intent intent1=new Intent(SetUpInstallments.this,BaseActivity.class);
                intent1.putExtra("SetUp","SetUp");
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);*/
            }
        });


        if(networkChecking.isConnectingToInternet()==true) {
            getInstallmentPlan();
        }else{
            Toast.makeText(this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }


    }

    public void getInstallmentPlan(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRANSACTION+card_id+ApiConstant.INSTALLMENTS_PLAN+"?&amount="+amount) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONArray jsonArray=new JSONArray(Response);

                    if(jsonArray!=null){

                        if(jsonArray.length()>0){

                            list=new ArrayList<>();

                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                InstallmentSetGet installmentSetGet=new InstallmentSetGet();

                                installmentSetGet.setPlan(jsonObject.getString("plan"));
                                installmentSetGet.setRate(jsonObject.getLong("rate"));
                                installmentSetGet.setNum_month(jsonObject.getInt("num_month"));
                                installmentSetGet.setAmount(jsonObject.getLong("amount"));

                                list.add(installmentSetGet);
                            }

                            if(list.size()>0){

                                installmentAdapter=new InstallmentAdapter(SetUpInstallments.this,list);

                                rv_installments.setAdapter(installmentAdapter);


                            }
                        }
                    }

                    appProgerssDialog.Dismiss();
                }catch (JSONException e)
                {
                    e.printStackTrace();
                    appProgerssDialog.Dismiss();

                }

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

    public void continueInstallment(String url,String Body ){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();


        new HTTP_POST_AUTH(url, Body) {
            @Override
            protected void OnSucess(String Response) {

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
            protected void OnResponseCode(int code) {

                if (code==200){

                    //Toast.makeText(SetUpInstallments.this,"Payment successfully completed",Toast.LENGTH_SHORT).show();

                    finish();
                }else{

                   // Toast.makeText(SetUpInstallments.this,"Something Wrong",Toast.LENGTH_SHORT).show();

                }

            }
        };

    }


}

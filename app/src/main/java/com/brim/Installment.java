package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.InstallmentTransAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Pojo.InstallSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Installment extends AppCompatActivity {

    ImageView back_button,transaction_icon;
    TextView txt_description,txt_amount,txt_remainbal,txt_installment,txt_plan,txt_foreign_exchange,txt_forex;
    RelativeLayout set_up_block;
    LinearLayout foreign_exchange_block;
    RelativeLayout foreign_block;
    RecyclerView rv_installment;

    String id;
    ArrayList<InstallSetGet> list=null;
    InstallmentTransAdapter adapter=null;

    String image,name,cate_name;

    AppProgerssDialog appProgerssDialog;
    NetworkChecking networkChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installment);

        appProgerssDialog=new AppProgerssDialog(this);

        networkChecking=new NetworkChecking(this);

        back_button= (ImageView) findViewById(R.id.back_button);
        transaction_icon= (ImageView) findViewById(R.id.transaction_icon);

        txt_description= (TextView) findViewById(R.id.txt_description);
        txt_amount= (TextView) findViewById(R.id.txt_amount);
        txt_remainbal= (TextView) findViewById(R.id.txt_remainbal);
        txt_installment= (TextView) findViewById(R.id.txt_installment);
        txt_plan= (TextView) findViewById(R.id.txt_plan);
        txt_foreign_exchange= (TextView) findViewById(R.id.txt_foreign_exchange);
        txt_forex= (TextView) findViewById(R.id.txt_forex);

        foreign_exchange_block= (LinearLayout) findViewById(R.id.foreign_exchange_block);
        foreign_block= (RelativeLayout) findViewById(R.id.foreign_block);
        //foreign_block.setVisibility(View.GONE);

        set_up_block= (RelativeLayout) findViewById(R.id.set_up_block);
        //set_up_block.setVisibility(View.GONE);
        rv_installment=(RecyclerView)findViewById(R.id.rv_installment);
        rv_installment.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final Intent intent=getIntent();

        id=intent.getExtras().getString("id");
        image=intent.getExtras().getString("category_image");
        name=intent.getExtras().getString("description");
        cate_name=intent.getExtras().getString("category_name");

        txt_description.setText(intent.getExtras().getString("description"));
        txt_amount.setText("$"+intent.getExtras().getString("amount"));

        txt_plan.setText(""+intent.getExtras().getInt("total_number_of_payments")+" Months");
        txt_installment.setText(""+intent.getExtras().getInt("number_of_payments_made")+" of "+intent.getExtras().getInt("total_number_of_payments")+" Payments");
        txt_remainbal.setText("$"+intent.getExtras().getLong("remaining_balance"));


        Picasso.with(this)
                .load(intent.getExtras().getString("category_image"))
                .into(transaction_icon);

        if(intent.getExtras().getLong("savings")>0){

            foreign_exchange_block.setVisibility(View.VISIBLE);
            txt_foreign_exchange.setText("$"+intent.getExtras().getLong("savings"));
        }else{
            foreign_exchange_block.setVisibility(View.GONE);

        }

        if(intent.getExtras().getLong("foreign_amount")==0 && intent.getExtras().getLong("conversion_rate")==0){
            foreign_block.setVisibility(View.GONE);
        }else{
            foreign_block.setVisibility(View.VISIBLE);
            txt_forex.setText(intent.getExtras().getLong("foreign_amount")+" USD at "+intent.getExtras().getLong("conversion_rate"));
        }

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

        new HTTP_Get(ApiConstant.TRANSACTION+ BrimApplication.getInstnace().GetCardId()+ApiConstant.INSTALLMENTS+"/"+id) {

            @Override
            protected void OnSucess(String Response) {


                    try {
                        JSONObject JSobject=new JSONObject(Response);

                        JSONArray paymentsArray=JSobject.getJSONArray("payments");

                        if (paymentsArray!=null) {

                            if(paymentsArray.length()>0){

                                list=new ArrayList<>();

                                for (int i = 0; i < paymentsArray.length(); i++) {

                                    JSONObject object=paymentsArray.getJSONObject(i);

                                    InstallSetGet installSetGet=new InstallSetGet();

                                    installSetGet.setIamge(image);
                                    installSetGet.setName(name);
                                    installSetGet.setCate_name(cate_name);

                                    installSetGet.setAmount(object.getString("amount"));
                                    installSetGet.setDate(object.getString("date"));

                                    list.add(installSetGet);

                                }

                                if (adapter == null) {

                                    adapter=new InstallmentTransAdapter(Installment.this,list);
                                    rv_installment.setAdapter(adapter);

                                }

                            }
                        }
                        appProgerssDialog.Dismiss();


                    } catch (JSONException e) {
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

}

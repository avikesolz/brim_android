package com.brim;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.ProvinceAdapter;
import com.brim.ApiHelper.HTTP_GET_AUTH;
import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Pojo.SpinnerSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileDetails extends AppCompatActivity {

    ImageView back_button;
    EditText txt_email,txt_phone,txt_alter_phone,txt_adds,txt_city,txt_code;
    RelativeLayout spinner_block;
    TextView txt_province,button_submit,bitton_chngpass;

    ArrayList<SpinnerSetGet> list;
    String[] codelist= new String[]{"AB", "BC","MB","NB","NL","NS","NT","NU","ON","PE","QC","SK","YT"};
    String[] provicelist= new String[]{"Alberta", "British Columbia","Manitoba","New Brunswick","Newfoundland and Labrador"
    ,"Nova Scotia","Northwest Territories","Ontario","Prince Edward Island","Quebec","Saskatchewan","Yukon"};

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        spinner_block= (RelativeLayout) findViewById(R.id.spinner_block);

        txt_email= (EditText) findViewById(R.id.txt_email);
        txt_phone= (EditText) findViewById(R.id.txt_phone);
        txt_alter_phone= (EditText) findViewById(R.id.txt_alter_phone);
        txt_adds= (EditText) findViewById(R.id.txt_adds);
        txt_city= (EditText) findViewById(R.id.txt_city);
        txt_code= (EditText) findViewById(R.id.txt_code);
        txt_province= (TextView) findViewById(R.id.txt_province);
        bitton_chngpass= (TextView) findViewById(R.id.bitton_chngpass);
        button_submit= (TextView) findViewById(R.id.button_submit);

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(networkChecking.isConnectingToInternet()==true) {
            sessionCheck();
        }else{
            Toast.makeText(this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }



     list=new ArrayList<>();

     for (int i=0;i<provicelist.length;i++){
         SpinnerSetGet spinnerSetGet=new SpinnerSetGet();
         spinnerSetGet.setCode(codelist[i]);
         spinnerSetGet.setProvice(provicelist[i]);
         list.add(spinnerSetGet);
     }

     spinner_block.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             Dialog dialog=new Dialog(ProfileDetails.this);
             dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
             dialog.setContentView(R.layout.dialog_province_layout);

             int height= (int) (getWindow().getWindowManager().getDefaultDisplay().getHeight()/2.2);
             int width= (int) (getWindow().getWindowManager().getDefaultDisplay().getWidth()/1.1);

             dialog.getWindow().setLayout(width,height);
             dialog.setCanceledOnTouchOutside(false);

             RecyclerView rv_province=(RecyclerView) dialog.findViewById(R.id.rv_province);
             rv_province.setHasFixedSize(true);
             rv_province.setLayoutManager(new LinearLayoutManager(ProfileDetails.this));
             ProvinceAdapter provinceAdapter=new ProvinceAdapter(dialog,list,ProfileDetails.this);
             rv_province.setAdapter(provinceAdapter);

             dialog.show();

         }
     });

        bitton_chngpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog addressdialogshow = new Dialog(ProfileDetails.this);
                addressdialogshow.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addressdialogshow.setContentView(R.layout.new_address_layout);
                addressdialogshow.setCanceledOnTouchOutside(false);

                final TextView save = (TextView) addressdialogshow.findViewById(R.id.save);
                TextView cancel = (TextView) addressdialogshow.findViewById(R.id.cancel);

             /*   final EditText et_address = (EditText) addressdialogshow.findViewById(R.id.et_address);
                final EditText et_phone = (EditText) addressdialogshow.findViewById(R.id.et_phone);


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        save.setEnabled(false);


                        if (et_address.getText().toString().trim().equals("")) {
                            et_address.setHint("Please enter Delivery Address");
                            et_address.setHintTextColor(Color.RED);

                        } else if (et_phone.getText().toString().trim().equals("")) {
                            et_phone.setHint("Please enter Phone Number");
                            et_phone.setHintTextColor(Color.RED);
                        } else {

                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressdialogshow.dismiss();

                    }
                });*/


                addressdialogshow.show();

            }
        });



        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txt_email.getText().toString().toString().equals("")){
                    txt_email.setHint("Enter email");
                }else{
                    if (txt_phone.getText().toString().toString().equals("")){
                        txt_phone.setHint("Enter phone no.");
                    }else{
                        if (txt_alter_phone.getText().toString().toString().equals("")){
                            txt_alter_phone.setHint("Enter alternative no.");
                        }else{

                            if (txt_adds.getText().toString().toString().equals("")){
                                txt_adds.setHint("Enter address");
                            }else{

                                if (txt_city.getText().toString().toString().equals("")){
                                    txt_city.setHint("Enter city");
                                }else{

                                    if (txt_code.getText().toString().toString().equals("")){
                                        txt_code.setHint("Enter postal code");
                                    }else{

                                        JSONObject jsonObject=new JSONObject();
                                        try {
                                            jsonObject.put("email",txt_email.getText().toString());
                                            jsonObject.put("primary_phone_number",txt_phone.getText().toString());
                                            jsonObject.put("alt_phone_number",txt_alter_phone.getText().toString());
                                            jsonObject.put("home_address",txt_adds.getText().toString());
                                            jsonObject.put("home_city",txt_city.getText().toString());
                                            jsonObject.put("home_province",txt_province.getText().toString());
                                            jsonObject.put("home_postal_code",txt_code.getText().toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        updateProfile(jsonObject.toString());

                                    }

                                    }

                                }

                            }

                        }

                    }

                }

        });


    }


    public void sessionCheck(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();


        new HTTP_GET_AUTH(ApiConstant.ME) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);

                    txt_email.setText(jsonObject.getString("email"));
                    txt_phone.setText(jsonObject.getString("primary_phone_number"));
                    txt_alter_phone.setText(jsonObject.getString("alt_phone_number"));
                    txt_adds.setText(jsonObject.getString("home_address"));
                    txt_city.setText(jsonObject.getString("home_city"));
                    txt_code.setText(jsonObject.getString("mailing_postal_code"));
                    txt_province.setText(jsonObject.getString("home_province"));


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

    public void updateProfile(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_PATCH_AUTH(ApiConstant.ME_PROFILE, obj) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(ProfileDetails.this,"Profile update successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(ProfileDetails.this,"Something wrong",Toast.LENGTH_SHORT).show();

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
            protected void OnResponseCode(int code) {
                appProgerssDialog.Dismiss();

            }
        };

    }

    public void updateTxt(String txt){
        txt_province.setText(txt);
    }

}

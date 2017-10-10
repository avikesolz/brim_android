package com.brim;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.ProvinceAdapter;
import com.brim.ApiHelper.HTTP_GET_AUTH;
import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Pojo.SpinnerSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileDetails extends AppCompatActivity {

    ImageView back_button;
    EditText txt_email,txt_phone,txt_alter_phone,txt_adds,txt_city,txt_code;
    RelativeLayout spinner_block;
    TextView txt_province,button_submit,bitton_chngpass;
    TextView txt_header,txt_fname,txt_lname,txt_eml,txt_addname,txt_cityname;
    LinearLayout chang_pass_block;

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


        txt_header= (TextView) findViewById(R.id.txt_header);
        txt_fname= (TextView) findViewById(R.id.txt_fname);
        txt_lname= (TextView) findViewById(R.id.txt_lname);
        txt_eml= (TextView) findViewById(R.id.txt_eml);
        txt_addname= (TextView) findViewById(R.id.txt_addname);
        txt_cityname= (TextView) findViewById(R.id.txt_cityname);
        chang_pass_block= (LinearLayout) findViewById(R.id.chang_pass_block);

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if(getIntent().getExtras().get("from").equals("family")){

            txt_header.setText("Update Profile");
            txt_fname.setText("First Name");
            txt_lname.setText("Last Name");
            txt_eml.setText("Email");
            txt_addname.setText("Home Address");
            txt_cityname.setText("Home City");
            chang_pass_block.setVisibility(View.GONE);

            txt_phone.setInputType(InputType.TYPE_CLASS_TEXT);
            txt_alter_phone.setInputType(InputType.TYPE_CLASS_TEXT);
        }



        if(networkChecking.isConnectingToInternet()==true) {
            getProfile();
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

                int width=getWindow().getWindowManager().getDefaultDisplay().getWidth();

                addressdialogshow.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);

                final TextView save = (TextView) addressdialogshow.findViewById(R.id.save);
                TextView cancel = (TextView) addressdialogshow.findViewById(R.id.cancel);

                final EditText et_pass = (EditText) addressdialogshow.findViewById(R.id.et_pass);
                final EditText et_new_pass = (EditText) addressdialogshow.findViewById(R.id.et_new_pass);
                final EditText et_con_pass = (EditText) addressdialogshow.findViewById(R.id.et_con_pass);


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //save.setEnabled(false);

                        if(et_pass.getText().toString().equals("")){

                            et_pass.setHint("Enter current password");
                            et_pass.setHintTextColor(Color.RED);

                        }else{

                            if(et_new_pass.getText().toString().equals("")){

                                et_new_pass.setHint("Enter new password");
                                et_new_pass.setHintTextColor(Color.RED);

                            }else{

                                if(et_new_pass.getText().toString().length()<8){

                                    et_new_pass.setText("");
                                    et_new_pass.setHint("Password must contains 8 characters");
                                    et_new_pass.setHintTextColor(Color.RED);

                                }else{

                                    if(isValidPassword(et_new_pass.getText().toString())==false){

                                        et_new_pass.setText("");

                                        et_new_pass.setHint("Password field follow the parttern");
                                        et_new_pass.setHintTextColor(Color.RED);

                                        Toast.makeText(ProfileDetails.this,"-an upper case letter\n- a lower case letter\n- a number\n- a symbol",Toast.LENGTH_LONG).show();

                                    }else{

                                        if(et_new_pass.getText().toString().equals(et_con_pass.getText().toString())){

                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("current_password",et_pass.getText().toString());
                                                jsonObject.put("new_password",et_new_pass.getText().toString());
                                                jsonObject.put("confirm_new_password",et_con_pass.getText().toString());

                                                updatePassword(jsonObject.toString(),addressdialogshow);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }else{
                                            et_con_pass.setText("");
                                            et_con_pass.setHint("Confirm Password must be same");
                                            et_con_pass.setHintTextColor(Color.RED);
                                        }

                                    }



                                }



                            }



                        }



                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressdialogshow.dismiss();

                    }
                });


                addressdialogshow.show();

            }
        });



        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txt_email.getText().toString().toString().equals("")){

                    if(getIntent().getExtras().get("from").equals("family"))
                        txt_email.setHint("Enter Fist Name");
                    else
                        txt_email.setHint("Enter Email");
                    txt_email.setHintTextColor(Color.RED);

                }else{
                    if (txt_phone.getText().toString().toString().equals("")){

                        if(getIntent().getExtras().get("from").equals("family"))
                            txt_phone.setHint("Enter Last Name");
                        else
                            txt_phone.setHint("Enter Phone No.");

                        txt_phone.setHintTextColor(Color.RED);


                    }else{
                        if (txt_alter_phone.getText().toString().toString().equals("")){

                            if(getIntent().getExtras().get("from").equals("family"))
                                txt_alter_phone.setHint("Enter Email");
                            else
                                txt_alter_phone.setHint("Enter Alternative No.");

                            txt_alter_phone.setHintTextColor(Color.RED);


                        }else{

                            if (txt_adds.getText().toString().toString().equals("")){

                                if(getIntent().getExtras().get("from").equals("family"))
                                    txt_adds.setHint("Enter Home Address");
                                else
                                    txt_adds.setHint("Enter Address");

                                txt_adds.setHintTextColor(Color.RED);

                            }else{

                                if (txt_city.getText().toString().toString().equals("")){

                                    if(getIntent().getExtras().get("from").equals("family"))
                                        txt_city.setHint("Enter Home City");
                                    else
                                        txt_city.setHint("Enter City");

                                    txt_city.setHintTextColor(Color.RED);

                                }else{

                                    if (txt_code.getText().toString().toString().equals("")){
                                        txt_code.setHint("Enter postal code");
                                        txt_code.setHintTextColor(Color.RED);

                                    }else{

                                        JSONObject jsonObject=new JSONObject();

                                        if(getIntent().getExtras().get("from").equals("family")){

                                            try {
                                                jsonObject.put("first_name", txt_email.getText().toString());
                                                jsonObject.put("last_name", txt_phone.getText().toString());
                                                jsonObject.put("email", txt_alter_phone.getText().toString());
                                                jsonObject.put("home_address", txt_adds.getText().toString());
                                                jsonObject.put("home_city", txt_city.getText().toString());
                                                jsonObject.put("home_province", txt_province.getText().toString());
                                                jsonObject.put("home_postal_code", txt_code.getText().toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }else {

                                            try {
                                                jsonObject.put("email", txt_email.getText().toString());
                                                jsonObject.put("primary_phone_number", txt_phone.getText().toString());
                                                jsonObject.put("alt_phone_number", txt_alter_phone.getText().toString());
                                                jsonObject.put("home_address", txt_adds.getText().toString());
                                                jsonObject.put("home_city", txt_city.getText().toString());
                                                jsonObject.put("home_province", txt_province.getText().toString());
                                                jsonObject.put("home_postal_code", txt_code.getText().toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
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


    public void getProfile(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        String url;

        if(getIntent().getExtras().get("from").equals("family"))
            url=ApiConstant.CARDS+"/"+BrimApplication.getInstnace().GetCardId()+"/"+ApiConstant.FAMILY_CARDS
                    +"/"+getIntent().getExtras().getString("card_id");
        else
            url=ApiConstant.ME;


        new HTTP_GET_AUTH(url) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);

                    if(getIntent().getExtras().get("from").equals("family")){

                        txt_email.setText(jsonObject.getString("first_name"));
                        txt_phone.setText(jsonObject.getString("last_name"));
                        txt_alter_phone.setText(jsonObject.getString("email"));
                        txt_adds.setText(jsonObject.getString("home_address"));
                        txt_city.setText(jsonObject.getString("home_city"));
                        txt_code.setText(jsonObject.getString("home_postal_code"));
                        txt_province.setText(jsonObject.getString("home_province"));

                    }else{

                        txt_email.setText(jsonObject.getString("email"));
                        txt_phone.setText(jsonObject.getString("primary_phone_number"));
                        txt_alter_phone.setText(jsonObject.getString("alt_phone_number"));
                        txt_adds.setText(jsonObject.getString("home_address"));
                        txt_city.setText(jsonObject.getString("home_city"));
                        txt_code.setText(jsonObject.getString("mailing_postal_code"));
                        txt_province.setText(jsonObject.getString("home_province"));

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

    public void updateProfile(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        String url;

        if(getIntent().getExtras().get("from").equals("family"))
            url=ApiConstant.CARDS+"/"+BrimApplication.getInstnace().GetCardId()+"/"+ApiConstant.FAMILY_CARDS
                    +"/"+getIntent().getExtras().getString("card_id")+"/"+ApiConstant.PROFILE;
        else
            url=ApiConstant.ME_PROFILE;


            new HTTP_PATCH_AUTH(url, obj) {
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

    public void updatePassword(String obj, final Dialog addressdialogshow){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_PATCH_AUTH(ApiConstant.CHANGE_PASSWORD, obj) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){

                        addressdialogshow.cancel();

                        Toast.makeText(ProfileDetails.this,"Changed password successfully",Toast.LENGTH_SHORT).show();
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

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}

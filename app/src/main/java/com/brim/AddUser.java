package com.brim;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.ProvinceAdapter;
import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Pojo.SpinnerSetGet;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddUser extends AppCompatActivity {

    ImageView back_button;
    EditText et_fname,et_lname,et_email,et_address,et_city,et_code,et_credit_limit;
    RelativeLayout spinner_block;
    TextView txt_province,button_submit;

    static boolean first_time=true;
    static  TextView txt_dob;
    static String dob="";
    String province="";
    static int d,m,y;
    ArrayList<SpinnerSetGet> list;
    String[] codelist= new String[]{"AB", "BC","MB","NB","NL","NS","NT","NU","ON","PE","QC","SK","YT"};
    String[] provicelist= new String[]{"Alberta", "British Columbia","Manitoba","New Brunswick","Newfoundland and Labrador"
            ,"Nova Scotia","Northwest Territories","Ontario","Prince Edward Island","Quebec","Saskatchewan","Yukon"};


    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        back_button= (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinner_block= (RelativeLayout) findViewById(R.id.spinner_block);

        et_fname= (EditText) findViewById(R.id.et_fname);
        et_lname= (EditText) findViewById(R.id.et_lname);
        et_email= (EditText) findViewById(R.id.et_email);
        et_address= (EditText) findViewById(R.id.et_address);
        et_city= (EditText) findViewById(R.id.et_city);
        et_code= (EditText) findViewById(R.id.et_code);
        et_credit_limit= (EditText) findViewById(R.id.et_credit_limit);

        txt_province= (TextView) findViewById(R.id.txt_province);
        txt_dob= (TextView) findViewById(R.id.txt_dob);
        button_submit= (TextView) findViewById(R.id.button_submit);

        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogLayout datePickerDialogLayout = new DatePickerDialogLayout();
                datePickerDialogLayout.show(getSupportFragmentManager(), "datePicker");
            }
        });


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

                Dialog dialog=new Dialog(AddUser.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_province_layout);

                int height= (int) (getWindow().getWindowManager().getDefaultDisplay().getHeight()/2.2);
                int width= (int) (getWindow().getWindowManager().getDefaultDisplay().getWidth()/1.1);

                dialog.getWindow().setLayout(width,height);
                dialog.setCanceledOnTouchOutside(false);

                RecyclerView rv_province=(RecyclerView) dialog.findViewById(R.id.rv_province);
                rv_province.setHasFixedSize(true);
                rv_province.setLayoutManager(new LinearLayoutManager(AddUser.this));
                ProvinceAdapter provinceAdapter=new ProvinceAdapter(dialog,list,AddUser.this);
                rv_province.setAdapter(provinceAdapter);

                dialog.show();

            }
        });


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_fname.getText().toString().toString().equals("")){

                    et_fname.setHint("Enter First Name");
                    et_fname.setHintTextColor(Color.RED);

                }else{
                    if (et_lname.getText().toString().toString().equals("")){

                        et_lname.setHint("Enter Last Name");
                        et_lname.setHintTextColor(Color.RED);


                    }else{
                        if (et_email.getText().toString().toString().equals("")){

                            et_email.setHint("Enter Email");
                            et_email.setHintTextColor(Color.RED);


                        }else{

                            if (dob.equals("")){

                                txt_dob.setHint("Select DOB");
                                txt_dob.setHintTextColor(Color.RED);

                            }else{

                                if (et_address.getText().toString().toString().equals("")){

                                    et_address.setHint("Enter Home Address");
                                    et_address.setHintTextColor(Color.RED);

                                }else{

                                    if (et_city.getText().toString().toString().equals("")){
                                        et_city.setHint("Enter City");
                                        et_city.setHintTextColor(Color.RED);

                                    }else{

                                        if (province.equals("")){
                                            txt_province.setHint("Select Province");
                                            txt_province.setHintTextColor(Color.RED);

                                        }else{

                                            if (et_code.getText().toString().toString().equals("")){
                                                et_code.setHint("Enter Postal Code");
                                                et_code.setHintTextColor(Color.RED);

                                            }else{

                                                if (et_credit_limit.getText().toString().toString().equals("")){
                                                    et_credit_limit.setHint("Enter Credit Limit");
                                                    et_credit_limit.setHintTextColor(Color.RED);

                                                }else{

                                                    JSONObject jsonObject=new JSONObject();


                                                    try {
                                                        jsonObject.put("first_name", et_fname.getText().toString());
                                                        jsonObject.put("last_name", et_lname.getText().toString());
                                                        jsonObject.put("email", et_email.getText().toString());


                                                        jsonObject.put("datebirth_day", dob.substring(8,10));
                                                        jsonObject.put("datebirth_month", dob.substring(5,7));
                                                        jsonObject.put("datebirth_year", dob.substring(0,4));


                                                        jsonObject.put("home_address", et_address.getText().toString());
                                                        jsonObject.put("home_city", et_city.getText().toString());
                                                        jsonObject.put("home_province", txt_province.getText().toString());
                                                        jsonObject.put("home_postal_code", et_code.getText().toString());
                                                        jsonObject.put("credit_limit", et_credit_limit.getText().toString());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                    addUser(jsonObject.toString());

                                                }

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            }

        });

    }

    public static class DatePickerDialogLayout extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        Calendar calendar;
        String date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    R.style.dialog,this,year,month,day);
            datepickerdialog.getDatePicker().setMaxDate( System.currentTimeMillis());

            
            if(first_time) {

                y=year;
                m=month;
                d=day;

                first_time=false;

                Log.d("Date----------------","Fist Time");
            }else {

                datepickerdialog.updateDate(y,m,d);

                Log.d("Date----------------","Not Fist Time");


            }


            return datepickerdialog;
        }


        public void onDateSet(DatePicker view, int year, int month, int day)
        {

            y=year;
            m=month;
            d=day;

            Calendar calendar = Calendar.getInstance();
            calendar.set(y,m,d);

            dob=dateFormat.format(calendar.getTime());

            Log.d("new Date","----------"+dob);

            txt_dob.setText(DateFormatConvertion.yyyymmdd_to_mmmmddyyyy(dob));

        }


        public void onCancel(DialogInterface dialog)
        {
            dialog.dismiss();
        }

    }

    public void addUser(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();



        new HTTP_POST_AUTH(ApiConstant.CARDS+"/"+ BrimApplication.getInstnace().GetCardId()+"/"+ApiConstant.FAMILY_CARDS,obj){

            @Override
            protected void OnSucess(String Response) {
                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(AddUser.this,"User Added successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(AddUser.this,"Something wrong",Toast.LENGTH_SHORT).show();

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
        province=txt;

    }


}

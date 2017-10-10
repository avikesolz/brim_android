package com.brim;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.TravelAdapter;
import com.brim.ApiHelper.HTTP_PATCH_AUTH;
import com.brim.ApiHelper.HTTP_POST_AUTH;
import com.brim.AppContant.ApiConstant;
import com.brim.Pojo.TravelSetGet;
import com.brim.Utils.AppAlert;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.GetDate;
import com.brim.Utils.NetworkChecking;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateTravel extends AppCompatActivity {

    ImageView back_button;
    static TextView txt_arrival,txt_departure;
    TextView button_update,txt_header;
    EditText et_location;
    RelativeLayout button_depart,button_arriv;

    static int y=0,m=0,d=0;
    static int y2=0,m2=0,d2=0;

    static  String depart_date,arriv_date;
    String id;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_travel);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);


        back_button= (ImageView) findViewById(R.id.back_button);
        txt_arrival= (TextView) findViewById(R.id.txt_arrival);
        txt_departure= (TextView) findViewById(R.id.txt_departure);
        txt_header= (TextView) findViewById(R.id.txt_header);
        button_update= (TextView) findViewById(R.id.button_update);
        et_location= (EditText) findViewById(R.id.et_location);
        button_depart= (RelativeLayout) findViewById(R.id.button_depart);
        button_arriv= (RelativeLayout) findViewById(R.id.button_arriv);



        if(getIntent().getExtras().get("from").equals("update_travel")) {

            id = getIntent().getExtras().getString("id");
            depart_date = getIntent().getExtras().getString("departure");
            arriv_date = getIntent().getExtras().getString("arrival");

            et_location.setText(getIntent().getExtras().getString("location"));
            txt_departure.setText(DateFormatConvertion.yyyymmdd_to_mmmmddyyyy(depart_date));
            txt_arrival.setText(DateFormatConvertion.yyyymmdd_to_mmmmddyyyy(arriv_date));

            txt_header.setText("Update Travel Notice");
            button_update.setText("Update Travel Notice");

        }else{

            txt_header.setText("Add Travel Notice");
            button_update.setText("Add Travel Notice");

        }



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialogLayout datePickerDialogLayout = new DatePickerDialogLayout(depart_date);
                datePickerDialogLayout.show(getSupportFragmentManager(), "datePicker");
            }
        });

        button_arriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialogLayout2 datePickerDialogLayout = new DatePickerDialogLayout2(arriv_date);
                datePickerDialogLayout.show(getSupportFragmentManager(), "datePicker");
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_location.getText().toString().equals("")){

                    et_location.setHint("Enter Location");
                    et_location.setHintTextColor(Color.RED);
                }else{

                    if(depart_date==null){
                        txt_departure.setText("");
                        txt_departure.setHint("Set Departure Date");
                        txt_departure.setHintTextColor(Color.RED);

                    }else {

                        if(arriv_date==null){
                            txt_arrival.setText("");
                            txt_arrival.setHint("Set Arrival Date");
                            txt_arrival.setHintTextColor(Color.RED);

                        }else {

                            if (java.sql.Date.valueOf(GetDate.getDate()).after(java.sql.Date.valueOf(depart_date))) {

                                AppAlert appAlert = new AppAlert(UpdateTravel.this);
                                appAlert.Alert("Departure Date must be current date or future date");

                            } else {

                                if (java.sql.Date.valueOf(GetDate.getDate()).after(java.sql.Date.valueOf(arriv_date))) {

                                    AppAlert appAlert = new AppAlert(UpdateTravel.this);
                                    appAlert.Alert("Arrival Date must be current date or future date");

                                } else {

                                    if (java.sql.Date.valueOf(depart_date).after(java.sql.Date.valueOf(arriv_date))) {

                                        AppAlert appAlert = new AppAlert(UpdateTravel.this);
                                        appAlert.Alert("Arrival Date must be more than Departure date");

                                    } else {

                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("location", et_location.getText());
                                            jsonObject.put("departure_date", depart_date);
                                            jsonObject.put("arrival_date", arriv_date);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if(getIntent().getExtras().get("from").equals("update_travel")) {

                                            updateTravel(jsonObject.toString());
                                        }else{
                                            createTravel(jsonObject.toString());
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


    public void updateTravel(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_PATCH_AUTH(ApiConstant.TRAVEL_NOTICES+"/"+id, obj) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(UpdateTravel.this,"Travel Notice Updated",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(UpdateTravel.this,"Something wrong",Toast.LENGTH_SHORT).show();

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

    public void createTravel(String obj){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_POST_AUTH(ApiConstant.TRAVEL_NOTICES,obj) {
            @Override
            protected void OnSucess(String Response) {
                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    if(jsonObject.getString("status").equals("ok")){
                        Toast.makeText(UpdateTravel.this,"Create Travel Notice",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(UpdateTravel.this,"Something wrong",Toast.LENGTH_SHORT).show();

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





    public static class DatePickerDialogLayout extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        Calendar calendar;
        String date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        public DatePickerDialogLayout(String date) {

            this.date=date;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            int year,month,day;

            calendar = Calendar.getInstance();
            Date Current_date=calendar.getTime();

            if(date!=null) {
                Date Date = java.sql.Date.valueOf(date);

                if (Date.after(Current_date)) {

                    year = Integer.parseInt(this.date.substring(0, 4));
                    month = Integer.parseInt(this.date.substring(5, 7)) - 1;
                    day = Integer.parseInt(this.date.substring(8, 10));

                } else {

                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }
            }else{

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }


            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(), R.style.dialog,this,year,month,day);
            datepickerdialog.getDatePicker().setMinDate( System.currentTimeMillis());

            y=year;
            m=month;
            d=day;



            datepickerdialog.updateDate(y,m,d);

            Log.d("Date----------------","Select");



            return datepickerdialog;
        }


        public void onDateSet(DatePicker view, int year, int month, int day)
        {

            y=year;
            m=month;
            d=day;

            Calendar calendar = Calendar.getInstance();
            calendar.set(y,m,d);

            depart_date=dateFormat.format(calendar.getTime());

            Log.d("new Date","----------"+depart_date);

            txt_departure.setText(DateFormatConvertion.yyyymmdd_to_mmmmddyyyy(depart_date));

        }


        public void onCancel(DialogInterface dialog)
        {
            dialog.dismiss();
        }

    }

    public static class DatePickerDialogLayout2 extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        Calendar calendar;
        String date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        public DatePickerDialogLayout2(String date) {

            this.date=date;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            int year,month,day;

            calendar = Calendar.getInstance();
            Date Current_date=calendar.getTime();

            if(date!=null) {
                Date Date = java.sql.Date.valueOf(date);

                if (Date.after(Current_date)) {

                    year = Integer.parseInt(this.date.substring(0, 4));
                    month = Integer.parseInt(this.date.substring(5, 7)) - 1;
                    day = Integer.parseInt(this.date.substring(8, 10));

                } else {

                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }
            }else{

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

            }


            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(), R.style.dialog,this,year,month,day);
            datepickerdialog.getDatePicker().setMinDate( System.currentTimeMillis());

            y2=year;
            m2=month;
            d2=day;



            datepickerdialog.updateDate(y2,m2,d2);

            Log.d("Date----------------","Select");



            return datepickerdialog;
        }


        public void onDateSet(DatePicker view, int year, int month, int day)
        {

            y2=year;
            m2=month;
            d2=day;

            Calendar calendar = Calendar.getInstance();
            calendar.set(y2,m2,d2);

            arriv_date=dateFormat.format(calendar.getTime());

            Log.d("new Date","----------"+arriv_date);

            txt_arrival.setText(DateFormatConvertion.yyyymmdd_to_mmmmddyyyy(arriv_date));

        }


        public void onCancel(DialogInterface dialog)
        {
            dialog.dismiss();
        }

    }
}

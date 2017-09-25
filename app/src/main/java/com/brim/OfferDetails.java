package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.AddressListAdapter;
import com.brim.Adapter.RecentTransactionAdapter;
import com.brim.Adapter.TransactionAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.FotterFragmnet.Activity;
import com.brim.Pojo.AddressDetailsSetGet;
import com.brim.Pojo.TransactionListData;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.DateDifference;
import com.brim.Utils.NetworkChecking;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OfferDetails extends AppCompatActivity implements View.OnClickListener{

    ArrayList<AddressDetailsSetGet> AllAddressDetails;

    ImageView OfferImage;
    TextView OfferName,OfferDescription,DaysLeft;
    RecyclerView AddressList,TransactionList;
    MapView Map;
    GoogleMap googleMap;

    RelativeLayout button_days;
    Spinner spinner_days;
    boolean spinner_status=false;
    public int page_no=1;
    TransactionAdapter recentAdapter;
    String date="60";
    NetworkChecking networkChecking;

    String MerchantName;

    AppProgerssDialog appProgerssDialog;
    LinkedList<TransactionListData> RecentTransactionList;
    int size=10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        appProgerssDialog=new AppProgerssDialog(this);
        networkChecking=new NetworkChecking(this);


        appProgerssDialog=new AppProgerssDialog(OfferDetails.this);
        networkChecking=new NetworkChecking(OfferDetails.this);

        OfferImage = (ImageView) findViewById(R.id.iv_offerimage);
        OfferName = (TextView) findViewById(R.id.tv_offername);
        OfferDescription = (TextView) findViewById(R.id.tv_offerdesc);
        DaysLeft = (TextView) findViewById(R.id.tv_daysleft);


        button_days = (RelativeLayout) findViewById(R.id.button_days);
        spinner_days=(Spinner) findViewById(R.id.spinner_days);


        AddressList = (RecyclerView) findViewById(R.id.rv_addreslist);
        AddressList.setHasFixedSize(true);
        AddressList.setLayoutManager(new LinearLayoutManager(OfferDetails.this));

        TransactionList = (RecyclerView) findViewById(R.id.rv_transaction);
        TransactionList.setHasFixedSize(true);
        TransactionList.setLayoutManager(new LinearLayoutManager(OfferDetails.this));

        Map = (MapView) findViewById(R.id.map);
        Map.onCreate(savedInstanceState);
        Map.onResume();


        if(networkChecking.isConnectingToInternet()==true) {

            FetchingValues();

        }else{
            Toast.makeText(OfferDetails.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }

        //*************************************Days Spinner Set Up*********************************************************
        List<String> listDays = new ArrayList<String>();
        listDays.add("7 Days");
        listDays.add("14 Days");
        listDays.add("30 Days");
        listDays.add("60 Days");

        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(OfferDetails.this, R.layout.white_spinner_view, listDays);

        // Drop down layout style - list view with radio button
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_days.setAdapter(daysAdapter);
        spinner_days.setSelection(3);

        button_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner_days.performClick();
            }
        });

        spinner_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spinner_status==true) {

                    if (i == 0) {
                        page_no = 1;
                        recentAdapter=null;

                        date = "7";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(OfferDetails.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 1) {
                        page_no = 1;
                        recentAdapter=null;

                        date = "14";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(OfferDetails.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 2) {
                        page_no = 1;
                        recentAdapter=null;

                        date = "30";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(OfferDetails.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 3) {
                        page_no = 1;
                        recentAdapter=null;

                        date = "60";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(OfferDetails.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.IMG_Back:
                onBackPressed();
                break;
        }
    }


    public void getTransactions() {
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRANSACTION+ BrimApplication.getInstnace().GetCardId()+"/transactions?card_filter=primary&sort=-transaction_date"
                +"&installment_filter=&transaction_filter=&date_filter="+date+"&q=&merchant_filter="+MerchantName+"&page_number="+page_no+"&page_size="+size) {
            @Override
            protected void OnSucess(String Response) {
                RecentTransactionList=new LinkedList<>();

                Log.v("Transaction_Responce::",Response);

                try {
                    JSONObject JSobject=new JSONObject(Response);
                    JSONArray transactions=JSobject.getJSONArray("transactions");

                    if (transactions!=null) {

                        if(transactions.length()>0){


                            for (int i = 0; i < transactions.length(); i++) {
                                TransactionListData datat = new TransactionListData();
                                datat.setItemObject(transactions.getJSONObject(i));
                                datat.setImage(AppConstant.IMAGE_URL + transactions.getJSONObject(i).getJSONObject("category").getString("id") + ".png");

                                //Log.d("Image url","::::"+AppConstant.IMAGE_URL+transactions.getJSONObject(i).getJSONObject("category").getString("id")+".png");

                                RecentTransactionList.add(datat);

                            }

                            if (recentAdapter == null) {

                                recentAdapter = new TransactionAdapter(RecentTransactionList, OfferDetails.this);
                                TransactionList.setAdapter(recentAdapter);
                            } else {

                                recentAdapter.updateList(RecentTransactionList);
                                recentAdapter.notifyDataSetChanged();

                            }

                        }
                    }
                    appProgerssDialog.Dismiss();
                    spinner_status = true;


                } catch (JSONException e) {
                    e.printStackTrace();

                    appProgerssDialog.Dismiss();

                }


            }

            @Override
            protected void OnErrorApi(String Error) {

                appProgerssDialog.Dismiss();


            }

            @Override
            protected void OnHttPError(String HttpError) {

                appProgerssDialog.Dismiss();


            }
        };

    }

public void FetchingValues(){

    appProgerssDialog.SetTitle(getString(R.string.app_name));
    appProgerssDialog.SetMessage("Please Wait...");
    appProgerssDialog.Show();

    String OfferId = getIntent().getExtras().getString("OfferId");

    new HTTP_Get(ApiConstant.EARNING_OFFER+"/"+OfferId) {
        @Override
        protected void OnSucess(String Response) {

            Log.v("Responce:::",Response);


            try {
                JSONObject jsonObject = new JSONObject(Response);

                String id = jsonObject.getString("id");
                String multiplier = jsonObject.getString("multiplier");
                String start_date = jsonObject.getString("start_date");
                String end_date = jsonObject.getString("end_date");
                String type = jsonObject.getString("type");

                JSONObject merchant = jsonObject.getJSONObject("merchant");

                String MerchantId = merchant.getString("id");
                MerchantName = merchant.getString("name");
                String MerchantCategory = merchant.getString("category");
                String MerchantImage = merchant.getString("image");


                String dd= DateDifference.dateDifference(end_date,start_date);
                DaysLeft.setText(dd+" DAYS LEFT");

                OfferName.setText(MerchantName);

                try {

                    Picasso.with(OfferDetails.this)
                            .load(MerchantImage)
                            .into(OfferImage);

                }catch (Exception e){

                }



                if (jsonObject.has("addresses")){


                    JSONArray addresses = jsonObject.getJSONArray("addresses");

                    if (addresses.length() > 0){

                        AllAddressDetails = new ArrayList<AddressDetailsSetGet>();


                        for (int i  = 0;i < addresses.length();i++){


                            JSONObject AddressesObject = addresses.getJSONObject(i);

                            String AddressId = AddressesObject.getString("id");
                            String street_address = AddressesObject.getString("street_address");
                            String city = AddressesObject.getString("city");
                            String province = AddressesObject.getString("province");
                            String country = AddressesObject.getString("country");
                            String postal_code = AddressesObject.getString("postal_code");
                            String lat = AddressesObject.getString("lat");
                            String lng = AddressesObject.getString("lng");

                            AddressDetailsSetGet addressDetailsSetGet = new AddressDetailsSetGet(AddressId,street_address,city,province,country,
                                    postal_code,lat,lng);

                            AllAddressDetails.add(addressDetailsSetGet);


                        }


                        if (Map != null) {
                            Map.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap mMap) {

/* googleMap.addMarker(new MarkerOptions()
.icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
.anchor(0.0f, 1.0f)
.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(Long))));*/

                                    googleMap = mMap;

// For showing a move to my location button
//googleMap.setMyLocationEnabled(true);

// For dropping a marker at a point on the Map

                                    if (AllAddressDetails.size() > 0){

                                        for (int i =0;i<AllAddressDetails.size();i++){

                                            LatLng latLng = new LatLng(Double.parseDouble(AllAddressDetails.get(i).getLat()), Double.parseDouble(AllAddressDetails.get(i).getLong()));

                                            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_blue)).position(latLng).title(String.valueOf(Html.fromHtml("")));

                                            googleMap.addMarker(markerOptions);
                                            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
                                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                        }

                                    }



// For zooming automatically to the location of the marker



                                }
                            });

                        }

                        AddressListAdapter addressListAdapter = new AddressListAdapter(AllAddressDetails,OfferDetails.this);
                        AddressList.setAdapter(addressListAdapter);

                    }else {

                        AddressList.setVisibility(View.GONE);

                    }

                }else {

                    AddressList.setVisibility(View.GONE);

                }


                getTransactions();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void OnErrorApi(String Error) {

        }

        @Override
        protected void OnHttPError(String HttpError) {

        }
    };

}

}

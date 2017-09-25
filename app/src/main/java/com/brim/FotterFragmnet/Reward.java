package com.brim.FotterFragmnet;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.EarningOfferAdapter;
import com.brim.Adapter.HistoryAdapter;
import com.brim.Adapter.OfferAdapter;
import com.brim.Adapter.RecentTransactionAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.BaseActivity;
import com.brim.Pojo.HistorySetGet;
import com.brim.Pojo.OfferSetGet;
import com.brim.Pojo.TransactionListData;
import com.brim.R;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.Loger;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reward extends Fragment {

    ImageView button_cross,coin;
    RelativeLayout top_banner;
    TextView TXT_Balance,tab_earn,tab_redeem,tab_history,txt_banner;
    RecyclerView rv_offer_feature,rv_offer;
    View v1,v2,v3;
    NestedScrollView view_nested;
    LinearLayout history_view;


    OfferAdapter offerAdapter;
    EarningOfferAdapter earningOfferAdapter;
    String card_id;
    String tab_status="earn";

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;


    RelativeLayout button_type,button_days;
    Spinner spinner_type,spinner_days;
    boolean spinner_status=false;
    public int page_no=1,size=10;
    String tans_filter="all",date="60";
    HistoryAdapter historyAdapter;
    RecyclerView view_history;
    ArrayList<HistorySetGet> hisList=null;



    public Reward() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity)getActivity()).ButoomChnager(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reward, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        networkChecking=new NetworkChecking(getActivity());
        appProgerssDialog=new AppProgerssDialog(getActivity());

        view_nested=(NestedScrollView)view.findViewById(R.id.view_nested);
        history_view=(LinearLayout) view.findViewById(R.id.history_view);
        history_view.setVisibility(View.GONE);
        view_history=(RecyclerView)view.findViewById(R.id.view_history);

        tab_earn=(TextView)view.findViewById(R.id.tab_earn);
        tab_redeem=(TextView)view.findViewById(R.id.tab_redeem);
        tab_history=(TextView)view.findViewById(R.id.tab_history);
        txt_banner=(TextView)view.findViewById(R.id.txt_banner);
        coin=(ImageView) view.findViewById(R.id.coin);

        tab_earn.setVisibility(View.INVISIBLE);
        tab_redeem.setVisibility(View.INVISIBLE);
        tab_history.setVisibility(View.INVISIBLE);

        coin.setImageResource(R.drawable.coins_and_points);
        txt_banner.setText("Shopping has never been more fun. Earn more points select merchants.");

        v1=view.findViewById(R.id.v1);
        v2=view.findViewById(R.id.v2);
        v3=view.findViewById(R.id.v3);

        button_cross=(ImageView) view.findViewById(R.id.button_cross);
        top_banner=(RelativeLayout) view.findViewById(R.id.top_banner);
        TXT_Balance=(TextView) view.findViewById(R.id.TXT_Balance);

        rv_offer_feature=(RecyclerView) view.findViewById(R.id.rv_offer_feature);
        rv_offer_feature.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv_offer=(RecyclerView) view.findViewById(R.id.rv_offer);
        rv_offer.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        spinner_type=(Spinner)view.findViewById(R.id.spinner_types);
        spinner_days=(Spinner)view.findViewById(R.id.spinner_days);
        button_type=(RelativeLayout) view.findViewById(R.id.button_type);
        button_days=(RelativeLayout) view.findViewById(R.id.button_days);

        view_history=(RecyclerView) view.findViewById(R.id.view_history);
        view_history.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        button_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top_banner.setVisibility(View.GONE);
            }
        });

        tab_earn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_nested.setVisibility(View.VISIBLE);
                history_view.setVisibility(View.GONE);


                tab_status="earn";

                top_banner.setVisibility(View.VISIBLE);

                coin.setImageResource(R.drawable.coins_and_points);
                txt_banner.setText("Shopping has never been more fun. Earn more points select merchants.");

                v1.setBackgroundColor(getResources().getColor(R.color.lightOrange));
                v2.setBackgroundColor(getResources().getColor(R.color.white));
                v3.setBackgroundColor(getResources().getColor(R.color.white));

                appProgerssDialog.SetTitle(getString(R.string.app_name));
                appProgerssDialog.SetMessage("Please Wait...");
                appProgerssDialog.Show();

                String url=ApiConstant.EARNING_OFFER+"?type_filter=featured"+"&card_id="+card_id;

                earningOfferAdapter=null;
                offerAdapter=null;
                getOfferFeature(url);

            }
        });

        tab_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_nested.setVisibility(View.VISIBLE);
                history_view.setVisibility(View.GONE);

                tab_status="redeem";

                top_banner.setVisibility(View.VISIBLE);

                coin.setImageResource(R.drawable.coins2);
                txt_banner.setText("Take advantage of our redemption offer to make your points go further");


                v1.setBackgroundColor(getResources().getColor(R.color.white));
                v2.setBackgroundColor(getResources().getColor(R.color.lightOrange));
                v3.setBackgroundColor(getResources().getColor(R.color.white));

                appProgerssDialog.SetTitle(getString(R.string.app_name));
                appProgerssDialog.SetMessage("Please Wait...");
                appProgerssDialog.Show();

                String url=ApiConstant.REDEEM_OFFER+"?type_filter=featured"+"&card_id="+card_id;

                earningOfferAdapter=null;
                offerAdapter=null;

                getOfferFeature(url);
            }
        });

        tab_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_nested.setVisibility(View.GONE);
                history_view.setVisibility(View.VISIBLE);

                //tab_status="history";

                top_banner.setVisibility(View.GONE);

                v1.setBackgroundColor(getResources().getColor(R.color.white));
                v2.setBackgroundColor(getResources().getColor(R.color.white));
                v3.setBackgroundColor(getResources().getColor(R.color.lightOrange));

                if(networkChecking.isConnectingToInternet()==true) {
                    page_no=1;
                    historyAdapter=null;
                    date="60";
                    tans_filter="all";
                    getHistory();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }
            }
        });


        //*************************************Types Spinner Set Up*********************************************************
        List<String> listTypes = new ArrayList<String>();
        listTypes.add("All");
        listTypes.add("Earn");
        listTypes.add("Redeem");

        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(getActivity(), R.layout.white_spinner_view, listTypes);
        // Drop down layout style - list view with radio button
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_type.setAdapter(typesAdapter);


        button_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner_type.performClick();
            }
        });

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spinner_status==true) {

                    if (i == 0) {
                        page_no = 1;
                        historyAdapter=null;

                        tans_filter = "all";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getHistory();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 1) {
                        page_no = 1;
                        historyAdapter=null;

                        tans_filter = "earn";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getHistory();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 2) {
                        page_no = 1;
                        historyAdapter=null;

                        tans_filter = "redeem";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getHistory();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //*************************************Days Spinner Set Up*********************************************************
        List<String> listDays = new ArrayList<String>();
        listDays.add("7 Days");
        listDays.add("14 Days");
        listDays.add("30 Days");
        listDays.add("60 Days");

        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getActivity(), R.layout.white_spinner_view, listDays);

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
                        historyAdapter=null;

                        date = "7";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getHistory();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 1) {
                        page_no = 1;
                        historyAdapter=null;

                        date = "14";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getHistory();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 2) {
                        page_no = 1;
                        historyAdapter=null;

                        date = "30";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getHistory();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 3) {
                        page_no = 1;
                        historyAdapter=null;

                        date = "60";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getHistory();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if(networkChecking.isConnectingToInternet()==true) {
            getCardDetails();
        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
    }


    public void getCardDetails(){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.CARDS) {

            @Override
            protected void OnSucess(String Response) {
                try {

                    JSONObject Object=new JSONObject(Response);

                    card_id=Object.getString("id");
                    TXT_Balance.setText(Object.getString("current_points_balance"));

                    //BrimApplication.getInstnace().SetPoints(Object.getString("current_points_balance"));

                    if (Object.getBoolean("primary")){
                        tab_earn.setVisibility(View.VISIBLE);
                        tab_redeem.setVisibility(View.VISIBLE);
                        tab_history.setVisibility(View.VISIBLE);

                        v1.setBackgroundColor(getActivity().getResources().getColor(R.color.lightOrange));


                    }else{
                        tab_earn.setVisibility(View.VISIBLE);
                        tab_redeem.setVisibility(View.INVISIBLE);
                        tab_history.setVisibility(View.VISIBLE);

                        v1.setBackgroundColor(getActivity().getResources().getColor(R.color.lightOrange));

                    }

                    String url=ApiConstant.EARNING_OFFER+"?type_filter=featured"+"&card_id="+card_id;

                    getOfferFeature(url);

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


    private void getOfferFeature(String url){

       /* appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();*/

        new HTTP_Get(url){

            @Override
            protected void OnSucess(String Response) {


                try {
                    JSONArray array=new JSONArray(Response);

                    if(array!=null) {

                        ArrayList<OfferSetGet> offerList=new ArrayList<>();

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject jsonObject=array.getJSONObject(i);

                            OfferSetGet offerSetGet=new OfferSetGet();

                            String url="";
                            if(tab_status.equals("earn"))
                                offerSetGet.setType("earning_offer");
                            else if(tab_status.equals("redeem"))
                                offerSetGet.setType("redemption_offer");


                            offerSetGet.setContent(jsonObject);

                            offerList.add(offerSetGet);

                        }

                        if(offerList.size()>0) {

                            if (offerAdapter == null) {

                                offerAdapter = new OfferAdapter(getActivity(), offerList);

                                rv_offer_feature.setAdapter(offerAdapter);

                                String url = "";
                                if (tab_status.equals("earn"))
                                    url = ApiConstant.EARNING_OFFER + "?card_id=" + card_id;
                                else if (tab_status.equals("redeem"))
                                    url = ApiConstant.REDEEM_OFFER + "?card_id=" + card_id;


                                getOffer(url);

                            }
                        }

                       appProgerssDialog.Dismiss();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void getOffer(String url){

       /* appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();*/

        new HTTP_Get(url){

            @Override
            protected void OnSucess(String Response) {


                try {
                    JSONArray array=new JSONArray(Response);

                    if(array!=null) {

                        ArrayList<OfferSetGet> offerList=new ArrayList<>();

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject jsonObject=array.getJSONObject(i);

                            OfferSetGet offerSetGet=new OfferSetGet();

                            if(tab_status.equals("earn"))
                                offerSetGet.setType("earning_offer");
                            else if(tab_status.equals("redeem"))
                                offerSetGet.setType("redemption_offer");

                            offerSetGet.setContent(jsonObject);
                            offerList.add(offerSetGet);

                        }

                        if(earningOfferAdapter==null) {

                            earningOfferAdapter=new EarningOfferAdapter(getActivity(),offerList);

                            rv_offer.setAdapter(earningOfferAdapter);
                        }

                        appProgerssDialog.Dismiss();


                    }


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


    public void getHistory() {
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.REWARDS_HISTORY+"?"+AppConstant.CARDID+"="+card_id
                +"&transaction_filter="+tans_filter+"&date_filter="+date
                +"&page_number="+page_no+"&page_size="+size) {
            @Override
            protected void OnSucess(String Response) {

                spinner_status=true;

                try {
                    JSONObject jsonObject=new JSONObject(Response);

                    JSONArray jsonArray=jsonObject.getJSONArray("transactions");

                    if(jsonArray!=null){

                        hisList=new ArrayList<>();

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);

                            HistorySetGet historySetGet=new HistorySetGet();
                            historySetGet.setDescription(object.getString("description"));
                            historySetGet.setAmount(object.getString("amount"));
                            historySetGet.setPoints(object.getString("points"));
                            historySetGet.setEarn_multiplier(object.getString("earn_multiplier"));
                            historySetGet.setRedeem_points_saved(object.getString("redeem_points_saved"));
                            historySetGet.setTransaction_type(object.getString("transaction_type"));
                            historySetGet.setTransaction_date(object.getString("transaction_date"));

                            hisList.add(historySetGet);

                        }

                        if(hisList.size()>0){

                            if(historyAdapter==null) {

                                historyAdapter = new HistoryAdapter(getContext(), hisList, Reward.this);
                                view_history.setAdapter(historyAdapter);
                            }else{

                                historyAdapter.updateList(hisList);
                                historyAdapter.notifyDataSetChanged();
                            }
                        }
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
        };

    }



}

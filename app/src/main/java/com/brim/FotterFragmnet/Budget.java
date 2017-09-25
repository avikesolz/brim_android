package com.brim.FotterFragmnet;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.GraphAdapter;
import com.brim.Adapter.MyBudgetAdapter;
import com.brim.Adapter.MySpendAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.BaseActivity;
import com.brim.Pojo.BudgetSetGet;
import com.brim.Pojo.SpendSetGet;
import com.brim.R;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.GetDate;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Budget extends Fragment {

    TextView tab_spend,tab_budget;
    View v1,v2;
    LinearLayout send_view,tav_view;
    RecyclerView rv_my_budget,rv_spend;
    ViewPager graph_viewpager;
    ImageView selector,selector2;

    MyBudgetAdapter myBudgetAdapter;
    MySpendAdapter mySpendAdapter;
    GraphAdapter graphAdapter;

    ArrayList<BudgetSetGet> budgetList=null;
    ArrayList<SpendSetGet> spendList=null;




    AppProgerssDialog appProgerssDialog;
    NetworkChecking networkChecking;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity)getActivity()).ButoomChnager(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        appProgerssDialog=new AppProgerssDialog(getActivity());
        networkChecking=new NetworkChecking(getActivity());

        graph_viewpager=(ViewPager) view.findViewById(R.id.graph_viewpager);

        tab_spend=(TextView) view.findViewById(R.id.tab_spend);
        tab_budget=(TextView) view.findViewById(R.id.tab_budget);

        v1=view.findViewById(R.id.v1);
        v2=view.findViewById(R.id.v2);

        tav_view=(LinearLayout) view.findViewById(R.id.tav_view);
        send_view=(LinearLayout) view.findViewById(R.id.send_view);

        rv_my_budget=(RecyclerView) view.findViewById(R.id.rv_my_budget);
        rv_my_budget.setHasFixedSize(true);
        rv_my_budget.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_spend=(RecyclerView) view.findViewById(R.id.rv_spend);
        rv_spend.setHasFixedSize(true);
        rv_spend.setLayoutManager(new LinearLayoutManager(getActivity()));


        send_view.setVisibility(View.VISIBLE);
        rv_my_budget.setVisibility(View.GONE);

        v1.setBackgroundColor(getResources().getColor(R.color.lightOrange));
        v2.setBackgroundColor(getResources().getColor(R.color.white));

        selector=(ImageView) view.findViewById(R.id.selector);
        selector2=(ImageView) view.findViewById(R.id.selector2);



        graph_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position==0){

                    selector.setImageResource(R.drawable.selected_dot);
                    selector2.setImageResource(R.drawable.tab_selector);

                }else if(position==1){

                    selector2.setImageResource(R.drawable.selected_dot);
                    selector.setImageResource(R.drawable.tab_selector);
                }

            }

            @Override
            public void onPageSelected(int position) {

                if(position==0){

                    selector.setImageResource(R.drawable.selected_dot);
                    selector2.setImageResource(R.drawable.tab_selector);

                }else if(position==1){

                    selector2.setImageResource(R.drawable.selected_dot);
                    selector.setImageResource(R.drawable.tab_selector);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        tab_spend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_view.setVisibility(View.VISIBLE);
                rv_my_budget.setVisibility(View.GONE);

                v1.setBackgroundColor(getResources().getColor(R.color.lightOrange));
                v2.setBackgroundColor(getResources().getColor(R.color.white));

                mySpendAdapter=null;
                //graphAdapter=null;

                if(networkChecking.isConnectingToInternet()==true) {
                    getSpend(GetDate.getMonth());
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }

            }
        });

        tab_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myBudgetAdapter=null;

                if(networkChecking.isConnectingToInternet()==true) {
                    getBudget();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }

            }
        });


        if(networkChecking.isConnectingToInternet()==true) {
            getSpend(GetDate.getMonth());
        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }


    }

    public void getSpend(String date) {

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.SPEND_SUMMARY+"?"+AppConstant.CARDID+"="+ BrimApplication.getInstnace().GetCardId()+"&year_month="+date) {
            @Override
            protected void OnSucess(String response) {

                try {
                    JSONObject budget=new JSONObject(response);

                    JSONArray results=budget.getJSONArray("results");

                    if(results!=null){

                        spendList=new ArrayList<>();

                        for (int i=0;i<results.length();i++){

                            SpendSetGet spendSetGet=new SpendSetGet();

                            JSONObject Object=results.getJSONObject(i);

                            spendSetGet.setGrand_total(budget.getInt("grand_total"));
                            spendSetGet.setTotal_amount(Object.getInt("total_amount"));
                            spendSetGet.setPercent(Object.getString("percent"));
                            spendSetGet.setCategory(Object.getJSONObject("category"));

                            spendList.add(spendSetGet);

                        }
                    }

                    if(spendList!=null){
                        if(spendList.size()>0){
                            if (mySpendAdapter == null) {

                                mySpendAdapter = new MySpendAdapter(getContext(), spendList);
                                rv_spend.setAdapter(mySpendAdapter);

                            }
                        }
                    }

                    if(budget!=null) {

                        if(graphAdapter==null) {

                            graphAdapter = new GraphAdapter(getChildFragmentManager(), Budget.this, budget.toString());
                            graph_viewpager.setAdapter(graphAdapter);
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

                appProgerssDialog.Dismiss();


            }

            @Override
            protected void OnHttPError(String HttpError) {

                appProgerssDialog.Dismiss();


            }
        };

    }


    public void getBudget() {
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.BUDGET+"?"+AppConstant.CARDID+"="+ BrimApplication.getInstnace().GetCardId()) {
            @Override
            protected void OnSucess(String response) {

                try {
                    JSONArray budget=new JSONArray(response);

                    if(budget.length()>0){

                        budgetList=new ArrayList<>();

                        for (int i=0;i<budget.length();i++){

                            BudgetSetGet budgetSetGet=new BudgetSetGet();

                            JSONObject jsonObject=budget.getJSONObject(i);

                            budgetSetGet.setMax_amount(jsonObject.getInt("max_amount"));
                            budgetSetGet.setTotal_amount(jsonObject.getInt("total_amount"));
                            budgetSetGet.setCategory(jsonObject.getJSONObject("category"));
                            budgetSetGet.setPrev_months(jsonObject.getJSONArray("prev_months"));

                            budgetList.add(budgetSetGet);

                        }

                        if(budgetList!=null){
                            if(budgetList.size()>0){
                                if (myBudgetAdapter == null) {

                                    send_view.setVisibility(View.GONE);
                                    rv_my_budget.setVisibility(View.VISIBLE);

                                    v1.setBackgroundColor(getResources().getColor(R.color.white));
                                    v2.setBackgroundColor(getResources().getColor(R.color.lightOrange));

                                    myBudgetAdapter = new MyBudgetAdapter(getContext(), budgetList);
                                    rv_my_budget.setAdapter(myBudgetAdapter);
                                }else{
                                    myBudgetAdapter.notifyDataSetChanged();
                                }
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

                appProgerssDialog.Dismiss();


            }

            @Override
            protected void OnHttPError(String HttpError) {

                appProgerssDialog.Dismiss();


            }
        };

    }

    public  void updateSpendList(String obj){

        mySpendAdapter=null;

        try {

            JSONObject  budget = new JSONObject(obj);

        JSONArray results=budget.getJSONArray("results");

        if(results!=null){

            spendList=new ArrayList<>();

            for (int i=0;i<results.length();i++){

                SpendSetGet spendSetGet=new SpendSetGet();

                JSONObject Object=results.getJSONObject(i);

                spendSetGet.setGrand_total(budget.getInt("grand_total"));
                spendSetGet.setTotal_amount(Object.getInt("total_amount"));
                spendSetGet.setPercent(Object.getString("percent"));
                spendSetGet.setCategory(Object.getJSONObject("category"));

                spendList.add(spendSetGet);

            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(spendList!=null){
            if(spendList.size()>0){
                if (mySpendAdapter == null) {

                    mySpendAdapter = new MySpendAdapter(getContext(), spendList);
                    rv_spend.setAdapter(mySpendAdapter);

                }
            }
        }
    }

}

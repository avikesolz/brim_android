package com.brim.FotterFragmnet;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brim.Adapter.CardAdapter;
import com.brim.Adapter.RecentTransactionAdapter;
import com.brim.Adapter.StatementAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.BaseActivity;
import com.brim.MakePayment;
import com.brim.Pojo.StateSetGet;
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
 * Activities that contain this fragment must implement the
 * {@link Activity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Activity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Activity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spinner_type,spinner_days;
    RelativeLayout button_type,button_days;
    TextView TXT_Balance;
    EditText et_search;
    LinearLayout button_payment;
    ImageView button_search;
    LinearLayout installments_view,filter_block;
    CardView search_view;
    View v1,v2,v3;
    TextView tab_trans,tab_install,tab_state;
    TextView button_cinstall,button_einstall;
    LinearLayout view_1,view_2;

    String card_id;
    public int page_no=1,size=10;
    String tans_filter="all",date="60",sear_text="",install_filter="";
    RecentTransactionAdapter recentAdapter;
    RecyclerView rv_Transcation;
    boolean spinner_status=false;
    public String tab_status="TRANS";

    boolean resume_status=false;

    LinkedList<TransactionListData> RecentTransactionList;

    //*****************************************statement variables************************************************
    RecyclerView rv_statement;
    StatementAdapter statementAdapter;
    ArrayList<StateSetGet> stateList=null;



    AppProgerssDialog appProgerssDialog;
    NetworkChecking networkChecking;


    public Activity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Activity.
     */
    // TODO: Rename and change types and number of parameters
    public static Activity newInstance(String param1, String param2) {
        Activity fragment = new Activity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((BaseActivity)getActivity()).ButoomChnager(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appProgerssDialog=new AppProgerssDialog(getActivity());

        networkChecking=new NetworkChecking(getActivity());


        TXT_Balance=(TextView) view.findViewById(R.id.TXT_Balance);
        et_search=(EditText) view.findViewById(R.id.et_search);
        button_search=(ImageView) view.findViewById(R.id.button_search);
        spinner_type=(Spinner)view.findViewById(R.id.spinner_types);
        spinner_days=(Spinner)view.findViewById(R.id.spinner_days);
        button_type=(RelativeLayout) view.findViewById(R.id.button_type);
        button_days=(RelativeLayout) view.findViewById(R.id.button_days);
        rv_Transcation=(RecyclerView)view.findViewById(R.id.rv_transaction);
        rv_Transcation.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        rv_statement=(RecyclerView)view.findViewById(R.id.rv_statement);
        rv_statement.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        tab_trans=(TextView) view.findViewById(R.id.tab_trans);
        tab_trans.setVisibility(View.INVISIBLE);

        tab_install=(TextView) view.findViewById(R.id.tab_install);
        tab_install.setVisibility(View.INVISIBLE);

        tab_state=(TextView) view.findViewById(R.id.tab_state);
        tab_state.setVisibility(View.INVISIBLE);


        button_cinstall=(TextView) view.findViewById(R.id.button_cinstall);
        button_einstall=(TextView) view.findViewById(R.id.button_einstall);

        v1=view.findViewById(R.id.v1);
        v2=view.findViewById(R.id.v2);
        v3=view.findViewById(R.id.v3);

        button_payment=(LinearLayout) view.findViewById(R.id.button_payment);
        installments_view=(LinearLayout) view.findViewById(R.id.installments_view);
        filter_block=(LinearLayout) view.findViewById(R.id.filter_block);
        search_view=(CardView) view.findViewById(R.id.search_view);

        view_1=(LinearLayout) view.findViewById(R.id.view_1);
        view_2=(LinearLayout) view.findViewById(R.id.view_2);
        view_2.setVisibility(View.INVISIBLE);


        //*************************************tab Selection *******************************************************

        tab_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_1.setVisibility(View.VISIBLE);
                view_2.setVisibility(View.INVISIBLE);


                tab_status="TRANS";

                v1.setBackgroundColor(getResources().getColor(R.color.lightOrange));
                v2.setBackgroundColor(getResources().getColor(R.color.white));
                v3.setBackgroundColor(getResources().getColor(R.color.white));

                installments_view.setVisibility(View.GONE);
                search_view.setVisibility(View.VISIBLE);
                filter_block.setVisibility(View.VISIBLE);

                tans_filter="all";
                date="60";
                sear_text="";
                install_filter="";

                spinner_type.setSelection(0);
                spinner_days.setSelection(3);


                page_no=1;
                recentAdapter=null;

                if(networkChecking.isConnectingToInternet()==true) {
                    getTransactions();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }


            }
        });

        //**************************************************Installments Section****************************************

        tab_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_1.setVisibility(View.VISIBLE);
                view_2.setVisibility(View.INVISIBLE);

                tab_status="INSTALL";

                v1.setBackgroundColor(getResources().getColor(R.color.white));
                v2.setBackgroundColor(getResources().getColor(R.color.lightOrange));
                v3.setBackgroundColor(getResources().getColor(R.color.white));

                installments_view.setVisibility(View.VISIBLE);
                search_view.setVisibility(View.GONE);

                filter_block.setVisibility(View.GONE);


                tans_filter="";
                date="";
                install_filter="";


                page_no=1;
                recentAdapter=null;
                if(networkChecking.isConnectingToInternet()==true) {
                    getInstallments();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }
            }
        });


        button_cinstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tab_status="INSTALL";


                button_einstall.setBackgroundResource(R.drawable.mold_rectangle_bule_white_right);
                button_cinstall.setBackgroundResource(R.drawable.mold_rectangle_blue_left );

                button_cinstall.setTextColor(getResources().getColor(R.color.white));
                button_einstall.setTextColor(getResources().getColor(R.color.deepBlue));

                filter_block.setVisibility(View.GONE);


                tans_filter="";
                date="";
                install_filter="";


                page_no=1;
                recentAdapter=null;
                if(networkChecking.isConnectingToInternet()==true) {
                    getInstallments();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }

            }
        });


        button_einstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tab_status="TRANS";


                button_cinstall.setBackgroundResource(R.drawable.mold_rectangle_blue_white_left);
                button_einstall.setBackgroundResource(R.drawable.mold_rectangle_blue_right);

                button_einstall.setTextColor(getResources().getColor(R.color.white));
                button_cinstall.setTextColor(getResources().getColor(R.color.deepBlue));

                filter_block.setVisibility(View.VISIBLE);


                tans_filter="";
                date="";
                install_filter="1";
                sear_text="";


                page_no=1;
                recentAdapter=null;
                if(networkChecking.isConnectingToInternet()==true) {
                    getTransactions();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }
            }
        });

        tab_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tab_status="STATEMENT";


                v1.setBackgroundColor(getResources().getColor(R.color.white));
                v2.setBackgroundColor(getResources().getColor(R.color.white));
                v3.setBackgroundColor(getResources().getColor(R.color.lightOrange));

                view_1.setVisibility(View.INVISIBLE);
                view_2.setVisibility(View.VISIBLE);


                if(networkChecking.isConnectingToInternet()==true) {

                    page_no=1;
                    statementAdapter=null;
                    getStatement();

                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }
            }
        });


//*************************************Types Spinner Set Up*********************************************************
        List<String> listTypes = new ArrayList<String>();
        listTypes.add("All Transaction Types");
        listTypes.add("Pre-Authorized Transaction");
        listTypes.add("Foreign Transaction");
        listTypes.add("Payments");

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
                        recentAdapter=null;

                        tans_filter = "all";

                    } else if (i == 1) {
                        page_no = 1;
                        recentAdapter=null;

                        tans_filter = "pre-auth";

                    } else if (i == 2) {
                        page_no = 1;
                        recentAdapter=null;

                        tans_filter = "foreign";

                    } else if (i == 3) {
                        page_no = 1;
                        recentAdapter=null;
                        tans_filter = "payment";

                    }

                    if(networkChecking.isConnectingToInternet()==true) {
                        if(tab_status.equals("TRANS")) {
                            getTransactions();
                        }
                        else if(tab_status.equals("TRANS")){

                        }
                    }else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
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
                        recentAdapter=null;

                        date = "7";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 1) {
                        page_no = 1;
                        recentAdapter=null;

                        date = "14";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 2) {
                        page_no = 1;
                        recentAdapter=null;

                        date = "30";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == 3) {
                        page_no = 1;
                        recentAdapter=null;

                        date = "60";
                        if(networkChecking.isConnectingToInternet()==true) {
                            getTransactions();
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

        //******************************************************Payment Action*******************************************

        button_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().startActivity(new Intent(getActivity(), MakePayment.class));
            }
        });


        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                page_no=1;
                recentAdapter=null;

                sear_text=et_search.getText().toString();

                if(networkChecking.isConnectingToInternet()==true) {
                    getTransactions();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                }

                View v = getActivity().getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    page_no=1;
                    recentAdapter=null;

                    sear_text=et_search.getText().toString();

                    if(networkChecking.isConnectingToInternet()==true) {
                        getTransactions();
                    }else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                    }

                    View v1 = getActivity().getCurrentFocus();
                    if (v1 != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v1.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
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
                    TXT_Balance.setText("$"+Object.getString("current_balance"));
                    spinner_status=true;

                    resume_status=true;

                    BrimApplication.getInstnace().SetPrimaryCardType(Object.getString("primary"));

                    if (Object.getBoolean("primary")){
                        tab_trans.setVisibility(View.VISIBLE);
                        tab_install.setVisibility(View.VISIBLE);
                        tab_state.setVisibility(View.VISIBLE);

                        v1.setBackgroundColor(getResources().getColor(R.color.lightOrange));


                    }else{
                        tab_trans.setVisibility(View.VISIBLE);
                        tab_install.setVisibility(View.INVISIBLE);
                        tab_state.setVisibility(View.INVISIBLE);

                        v1.setBackgroundColor(getResources().getColor(R.color.lightOrange));

                    }

                    getTransactions();

                    //appProgerssDialog.Dismiss();
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

    //http://abcanada.ca/api/cards/5041160207877698/transactions?card_filter=&page_size=10&date_filter=&transaction_filter=&q=&page_number=1&installment_filter=1&sort=-transaction_date

    public void getTransactions() {
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRANSACTION+card_id+"/transactions?card_filter=&sort=-transaction_date"
                +"&installment_filter="+install_filter
                +"&transaction_filter="+tans_filter+"&date_filter="+date+"&q="+Html.fromHtml((String) sear_text).toString()
                +"&page_number="+page_no+"&page_size="+size) {
            @Override
            protected void OnSucess(String Response) {
                RecentTransactionList=new LinkedList<>();

                try {
                    JSONObject JSobject=new JSONObject(Response);
                    JSONArray transactions=JSobject.getJSONArray("transactions");

                    if (transactions!=null) {

                        if(transactions.length()>0){

                            rv_Transcation.setVisibility(View.VISIBLE);


                            for (int i = 0; i < transactions.length(); i++) {
                                TransactionListData datat = new TransactionListData();
                                datat.setItemObject(transactions.getJSONObject(i));

                                Object intervention = transactions.getJSONObject(i).get("category");
                                if (intervention instanceof JSONArray) {
                                    // It's an array
                                    //JSONArray jsonArray = (JSONArray)intervention;
                                }
                                else if (intervention instanceof JSONObject) {
                                    // It's an object
                                    //JSONObject jsonObject = (JSONObject)intervention;
                                    datat.setImage(AppConstant.IMAGE_URL + transactions.getJSONObject(i).getJSONObject("category").getString("id") + ".png");

                                }
                                //Log.d("Image url","::::"+AppConstant.IMAGE_URL+transactions.getJSONObject(i).getJSONObject("category").getString("id")+".png");

                                RecentTransactionList.add(datat);

                            }

                            if (recentAdapter == null) {

                                recentAdapter = new RecentTransactionAdapter(getActivity(), RecentTransactionList, Activity.this);
                                rv_Transcation.setAdapter(recentAdapter);
                            } else {

                                recentAdapter.updateList(RecentTransactionList);
                                recentAdapter.notifyDataSetChanged();

                            }

                        }else{

                            if(page_no==1)
                            rv_Transcation.setVisibility(View.INVISIBLE);

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



    public void getInstallments() {
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRANSACTION+card_id+ApiConstant.INSTALLMENTS+"?"+"installment_filter="+install_filter
                +"&transaction_filter="+tans_filter+"&date_filter="+date
                +"&sort=-transaction_date&page_number="+page_no+"&page_size="+size) {
            @Override
            protected void OnSucess(String Response) {
                RecentTransactionList=new LinkedList<>();

                try {
                    JSONObject JSobject=new JSONObject(Response);
                    JSONArray installments=JSobject.getJSONArray("installments");

                    if (installments!=null) {


                        if(installments.length()>0){

                            rv_Transcation.setVisibility(View.VISIBLE);



                            for (int i = 0; i < installments.length(); i++) {
                                TransactionListData datat = new TransactionListData();
                                datat.setItemObject(installments.getJSONObject(i).getJSONObject("transaction"));
                                datat.setImage(AppConstant.IMAGE_URL + installments.getJSONObject(i).getJSONObject("transaction").getJSONObject("category").getString("id") + ".png");

                                datat.setId(installments.getJSONObject(i).getString("id"));
                                datat.setPrice(installments.getJSONObject(i).getString("amount"));
                                datat.setTotal_number_of_payments(installments.getJSONObject(i).getInt("total_number_of_payments"));
                                datat.setNumber_of_payments_made(installments.getJSONObject(i).getInt("number_of_payments_made"));
                                datat.setRemaining_balance(installments.getJSONObject(i).getLong("remaining_balance"));

                                RecentTransactionList.add(datat);

                            }

                            if (recentAdapter == null) {

                                recentAdapter = new RecentTransactionAdapter(getActivity(), RecentTransactionList, Activity.this);
                                rv_Transcation.setAdapter(recentAdapter);
                            } else {

                                recentAdapter.updateList(RecentTransactionList);
                                recentAdapter.notifyDataSetChanged();

                            }
                        }else{

                            if(page_no==1)
                                rv_Transcation.setVisibility(View.INVISIBLE);

                        }
                    }

                    appProgerssDialog.Dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
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

    public void getStatement(){



        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRANSACTION+card_id+ApiConstant.STATEMENTS+"&page_number="+page_no+"&page_size="+size) {
            @Override
            protected void OnSucess(String Response) {

                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    JSONArray jsonArray=jsonObject.getJSONArray("statements");

                    int total_pages=jsonObject.getJSONObject("pagination").getInt("total_pages");

                    if(jsonArray!=null){

                        stateList=new ArrayList<>();

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);

                            StateSetGet stateSetGet=new StateSetGet();
                            stateSetGet.setId(object.getString("id"));
                            stateSetGet.setPrimary_card_id(object.getString("primary_card_id"));
                            stateSetGet.setFrom_date(object.getString("from_date"));
                            stateSetGet.setTo_date(object.getString("to_date"));
                            stateSetGet.setDue_date(object.getString("due_date"));
                            stateSetGet.setBalance(object.getString("balance"));
                            stateSetGet.setMinimum_payment(object.getString("minimum_payment"));

                            stateList.add(stateSetGet);

                        }

                        if(stateList.size()>0){
                            statementAdapter=new StatementAdapter(getContext(),stateList,Activity.this,total_pages);
                            rv_statement.setAdapter(statementAdapter);
                        }else{

                            statementAdapter.update(stateList);
                            statementAdapter.notifyDataSetChanged();

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



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(resume_status==true) {

            if (tab_status.equals("TRANS")) {
                if (networkChecking.isConnectingToInternet() == true) {
                    page_no = 1;
                    recentAdapter = null;
                    getTransactions();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        }else if(tab_status.equals("INSTALL")){

            if (networkChecking.isConnectingToInternet() == true) {
                page_no = 1;
                recentAdapter = null;
                getInstallments();
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }else if(tab_status.equals("STATEMENT")){

            if (networkChecking.isConnectingToInternet() == true) {
                page_no = 1;
                statementAdapter = null;
                getStatement();
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }

    }
}

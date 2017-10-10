package com.brim.FotterFragmnet;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.brim.Adapter.CardAdapter;
import com.brim.Adapter.OfferAdapter;
import com.brim.Adapter.RecentTransactionAdapter;
import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.BaseActivity;
import com.brim.Font.AxiformaBook;
import com.brim.Font.AxiformaMedium;
import com.brim.Font.AxiformaRegular;
import com.brim.Pojo.OfferSetGet;
import com.brim.Pojo.TransactionListData;
import com.brim.R;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.ConvertLocal;
import com.brim.Utils.DateFormatConvertion;
import com.brim.Utils.Loger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashBoard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RelativeLayout Page_indicator;
    ImageView selector,selector2;
    private ImageView[] dots;
    RecyclerView list_item;

    ViewPager card_viewPager;
    CardAdapter cardAdapter;
    OfferAdapter offerAdapter;
    RecentTransactionAdapter recentAdapter;
    LinkedList<TransactionListData> RecentTransactionList;
    public int page_no=1,size=10;
    String card_id="";


    RecyclerView Rec_Recent_Transcation;

    AppProgerssDialog appProgerssDialog;

    private OnFragmentInteractionListener mListener;

    public DashBoard() {




    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoard newInstance(String param1, String param2) {
        DashBoard fragment = new DashBoard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity)getActivity()).ButoomChnager(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        appProgerssDialog=new AppProgerssDialog(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board, container, false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Top_Pager=(ViewPager)view.findViewById(R.id.Top_Pager);
        Page_indicator=(RelativeLayout) view.findViewById(R.id.Page_indicator);
        selector=(ImageView) view.findViewById(R.id.selector);
        selector2=(ImageView) view.findViewById(R.id.selector2);
        Rec_Recent_Transcation=(RecyclerView) view.findViewById(R.id.Rec_Recent_Transcation);
        list_item=(RecyclerView)view.findViewById(R.id.list_item);
        list_item.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        Rec_Recent_Transcation.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        card_viewPager=(ViewPager) view.findViewById(R.id.card_viewpager);


        card_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        getCardDetails();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                    BrimApplication.getInstnace().SetCardId(card_id);
                    BrimApplication.getInstnace().SetPrimaryCardType(Object.getString("primary"));

                    getOffer(card_id);                      //**********  ****call offer details****************

                    CallToAddView();

                    if(Object!=null) {

                        if (Object.length() > 0) {
                            cardAdapter = new CardAdapter(getContext(), Object.toString(), 2);
                            card_viewPager.setAdapter(cardAdapter);
                        }
                    }


                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            protected void OnErrorApi(String Error) {
                Loger.MSG("@@ ON Erroe",""+Error);
            }

            @Override
            protected void OnHttPError(String HttpError) {

            }
        };
    }

    //*************************************************Fetching offer details********************************************


    private void getOffer(String card_id){

        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.INSIGHTS+"?card_id="+card_id){

            @Override
            protected void OnSucess(String Response) {


                try {
                    JSONArray array=new JSONArray(Response);

                    if(array!=null) {

                        ArrayList<OfferSetGet> offerList=new ArrayList<>();

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject jsonObject=array.getJSONObject(i);

                            OfferSetGet offerSetGet=new OfferSetGet();
                            offerSetGet.setType(jsonObject.getString("type"));
                            offerSetGet.setContent(jsonObject.getJSONObject("content"));

                            offerList.add(offerSetGet);

                        }

                        if(offerAdapter==null) {

                            offerAdapter=new OfferAdapter(getActivity(),offerList);

                            list_item.setAdapter(offerAdapter);
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

    //***********************************************************fetch transaction details****************************************************

    public void CallToAddView() {
        appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();

        new HTTP_Get(ApiConstant.TRANSACTION+card_id+"/transactions?card_filter=&sort=-transaction_date&page_number="+page_no+"&page_size="+size) {
            @Override
            protected void OnSucess(String Response) {
                RecentTransactionList=new LinkedList<>();

                try {
                    JSONObject JSobject=new JSONObject(Response);
                    JSONArray transactions=JSobject.getJSONArray("transactions");

                    if (transactions!=null) {

                        if(transactions.length()>0){


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

                                recentAdapter = new RecentTransactionAdapter(getActivity(), RecentTransactionList, DashBoard.this);
                                Rec_Recent_Transcation.setAdapter(recentAdapter);
                                //Rec_Recent_Transcation.setNestedScrollingEnabled(true);
                            } else {

                                recentAdapter.updateList(RecentTransactionList);
                                recentAdapter.notifyDataSetChanged();

                            }
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

}

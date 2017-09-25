package com.brim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.brim.Font.AxiformaBook;
import com.brim.Font.AxiformaRegular;
import com.brim.Font.SFNFTextView;
import com.brim.FotterFragmnet.Account;
import com.brim.FotterFragmnet.Activity;
import com.brim.FotterFragmnet.Budget;
import com.brim.FotterFragmnet.DashBoard;
import com.brim.FotterFragmnet.Reward;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    LinearLayout LL_Account,LL_Budget,LL_Reward,LL_Activity,LL_Dashboard;
    ImageView IMG_Dashboard,IMG_Activity,IMG_Reward,IMG_Budget,IMG_Account,menu_button,brim_icon;
    SFNFTextView TXT_Account,TXT_Budget,TXT_Reward,TXT_Activity,TXT_Dashboard,txt_header;
    private String PAGEVALUE="ok";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TXT_Account=(SFNFTextView)findViewById(R.id.TXT_Account);
        TXT_Budget=(SFNFTextView)findViewById(R.id.TXT_Budget);
        TXT_Reward=(SFNFTextView)findViewById(R.id.TXT_Reward);
        TXT_Activity=(SFNFTextView)findViewById(R.id.TXT_Activity);
        TXT_Dashboard=(SFNFTextView)findViewById(R.id.TXT_Dashboard);

        IMG_Dashboard=(ImageView)findViewById(R.id.IMG_Dashboard);
        IMG_Activity=(ImageView)findViewById(R.id.IMG_Activity);
        IMG_Reward=(ImageView)findViewById(R.id.IMG_Reward);
        IMG_Budget=(ImageView)findViewById(R.id.IMG_Budget);
        IMG_Account=(ImageView)findViewById(R.id.IMG_Account);

        LL_Account=(LinearLayout)findViewById(R.id.LL_Account);
        LL_Budget=(LinearLayout)findViewById(R.id.LL_Budget);
        LL_Reward=(LinearLayout)findViewById(R.id.LL_Reward);
        LL_Activity=(LinearLayout)findViewById(R.id.LL_Activity);
        LL_Dashboard=(LinearLayout)findViewById(R.id.LL_Dashboard);

        menu_button= (ImageView) findViewById(R.id.menu_button);
        brim_icon= (ImageView) findViewById(R.id.brim_icon);
        txt_header=(SFNFTextView)findViewById(R.id.txt_header);
        txt_header.setVisibility(View.GONE);


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        DashBoard search_basic = new DashBoard();
        fragmentTransaction.add(R.id.Container, search_basic);
        fragmentTransaction.commit();


    }


    public void ButoomChnager(Fragment fr)
    {

        LL_Account.setBackgroundColor(Color.WHITE);
        LL_Budget.setBackgroundColor(Color.WHITE);
        LL_Reward.setBackgroundColor(Color.WHITE);
        LL_Activity.setBackgroundColor(Color.WHITE);
        LL_Dashboard.setBackgroundColor(Color.WHITE);

        TXT_Account.setTextColor(Color.parseColor("#808080"));
        TXT_Budget.setTextColor(Color.parseColor("#808080"));
        TXT_Reward.setTextColor(Color.parseColor("#808080"));
        TXT_Activity.setTextColor(Color.parseColor("#808080"));
        TXT_Dashboard.setTextColor(Color.parseColor("#808080"));

        IMG_Dashboard.setImageResource(R.drawable.ic_dashboard);
        IMG_Activity.setImageResource(R.drawable.ic_activity);
        IMG_Reward.setImageResource(R.drawable.ic_rewards);
        IMG_Budget.setImageResource(R.drawable.ic_budget);
        IMG_Account.setImageResource(R.drawable.ic_account);




         /*   if (getIntent().getExtras()!=null){

            PAGEVALUE=getIntent().getExtras().getString("SetUp");
            }*/



  /*      if (PAGEVALUE.equals("SetUp"))
        {
//            if (fr instanceof Activity)
//            {
                LL_Activity.setBackgroundColor(Color.parseColor("#F0FDFE"));
                TXT_Activity.setTextColor(Color.parseColor("#05C3DE"));
                IMG_Activity.setImageResource(R.drawable.icon_activity_navyx);

                menu_button.setVisibility(View.GONE);
                brim_icon.setVisibility(View.GONE);
                txt_header.setVisibility(View.VISIBLE);
                txt_header.setText("Activity");

//            }

        }

        else {*/


            if (fr instanceof DashBoard)
            {
                LL_Dashboard.setBackgroundColor(Color.parseColor("#F0FDFE"));
                TXT_Dashboard.setTextColor(Color.parseColor("#05C3DE"));
                IMG_Dashboard.setImageResource(R.drawable.ic_dashboard_navy);

                menu_button.setVisibility(View.VISIBLE);
                brim_icon.setVisibility(View.VISIBLE);
                txt_header.setVisibility(View.GONE);

            }

            if (fr instanceof Activity)
            {
                LL_Activity.setBackgroundColor(Color.parseColor("#F0FDFE"));
                TXT_Activity.setTextColor(Color.parseColor("#05C3DE"));
                IMG_Activity.setImageResource(R.drawable.icon_activity_navyx);

                menu_button.setVisibility(View.GONE);
                brim_icon.setVisibility(View.GONE);
                txt_header.setVisibility(View.VISIBLE);
                txt_header.setText("Activity");

            }

            if (fr instanceof Reward)
            {
                LL_Reward.setBackgroundColor(Color.parseColor("#F0FDFE"));
                TXT_Reward.setTextColor(Color.parseColor("#05C3DE"));
                IMG_Reward.setImageResource(R.drawable.icon_rewards_navy);

                menu_button.setVisibility(View.GONE);
                brim_icon.setVisibility(View.GONE);
                txt_header.setVisibility(View.VISIBLE);
                txt_header.setText("Rewards");
            }

            if (fr instanceof Budget)
            {
                LL_Budget.setBackgroundColor(Color.parseColor("#F0FDFE"));
                TXT_Budget.setTextColor(Color.parseColor("#05C3DE"));
                IMG_Budget.setImageResource(R.drawable.icon_budget_navy);

                menu_button.setVisibility(View.GONE);
                brim_icon.setVisibility(View.GONE);
                txt_header.setVisibility(View.VISIBLE);
                txt_header.setText("Budget");

            }

            if (fr instanceof Account)
            {
                LL_Account.setBackgroundColor(Color.parseColor("#F0FDFE"));
                TXT_Account.setTextColor(Color.parseColor("#05C3DE"));
                IMG_Account.setImageResource(R.drawable.icon_account_navy);

                menu_button.setVisibility(View.GONE);
                brim_icon.setVisibility(View.GONE);
                txt_header.setVisibility(View.VISIBLE);
                txt_header.setText("Account Management");

            }

        }






   // }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.LL_Dashboard:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                DashBoard search_basic = new DashBoard();
                fragmentTransaction.replace(R.id.Container, search_basic);
                fragmentTransaction.commit();
                break;

            case R.id.LL_Activity:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Container, new Activity());
                fragmentTransaction.commit();
                break;

            case R.id.LL_Reward:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Container, new Reward());
                fragmentTransaction.commit();
                break;

            case R.id.LL_Budget:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Container, new Budget());
                fragmentTransaction.commit();
                break;
                case R.id.LL_Account:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Container, new Account());
                fragmentTransaction.commit();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Alert !")
                .setMessage("Are you sure to exit the app ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        finishAffinity();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

package com.brim.FotterFragmnet;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.brim.AppContant.BrimApplication;
import com.brim.BaseActivity;
import com.brim.ChooseYourLoginWithOption;
import com.brim.FinalPinActivity;
import com.brim.Help;
import com.brim.LegalPrivacy;
import com.brim.ManageCard;
import com.brim.PasswordActivity;
import com.brim.PatternLockFinalScreen;
import com.brim.ProfileDetails;
import com.brim.R;
import com.brim.RemoveAccount;
import com.brim.Settings_Notification;
import com.brim.SplashActivity;
import com.brim.TravelNotice;


public class Account extends Fragment {


    RelativeLayout button_pro,button_mang_card,button_settings,button_travel,button_help,button_legal,button_logout,button_signin_m;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity)getActivity()).ButoomChnager(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_pro=(RelativeLayout) view.findViewById(R.id.button_pro);
        button_mang_card=(RelativeLayout) view.findViewById(R.id.button_mang_card);
        button_settings=(RelativeLayout) view.findViewById(R.id.button_settings);
        button_travel=(RelativeLayout) view.findViewById(R.id.button_travel);
        button_help=(RelativeLayout) view.findViewById(R.id.button_help);
        button_legal=(RelativeLayout) view.findViewById(R.id.button_legal);
        button_logout=(RelativeLayout) view.findViewById(R.id.button_logout);
        button_signin_m=(RelativeLayout) view.findViewById(R.id.button_signin_m);


        button_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ProfileDetails.class);
                intent.putExtra("from","account");
                startActivity(intent);
            }
        });

        button_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Help.class));
            }
        });

        button_mang_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ManageCard.class));
            }
        });

        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Settings_Notification.class));
            }
        });

        button_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TravelNotice.class));
            }
        });

        button_legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LegalPrivacy.class));
            }
        });

        button_signin_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BrimApplication.getInstnace().GetPassType().equals("password")){

                    Intent intent=new Intent(getActivity(), PasswordActivity.class);
                    intent.putExtra("from","account");
                    startActivity(intent);

                }   else if(BrimApplication.getInstnace().GetPassType().equals("pin"))  {

                    Intent intent=new Intent(getActivity(), FinalPinActivity.class);
                    intent.putExtra("from","account");
                    startActivity(intent);

                }
                else if(BrimApplication.getInstnace().GetPassType().equals("pattern"))  {

                    Intent intent=new Intent(getActivity(), PatternLockFinalScreen.class);
                    intent.putExtra("from","account");
                    startActivity(intent);

                }else{
                    startActivity(new Intent(getActivity(), ChooseYourLoginWithOption.class));

                }
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }
                builder.setTitle("Logout")
                        .setMessage("Are you sure to Logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                BrimApplication.getInstnace().SetUserId("");
                                startActivity(new Intent(getActivity(), RemoveAccount.class));
                                getActivity().finishAffinity();
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
        });

    }
}

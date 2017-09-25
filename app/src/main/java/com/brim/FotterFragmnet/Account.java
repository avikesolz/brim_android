package com.brim.FotterFragmnet;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.brim.BaseActivity;
import com.brim.ProfileDetails;
import com.brim.R;


public class Account extends Fragment {


    RelativeLayout button_pro,button_mang_card,button_settings,button_travel,button_help,button_legal,button_logout;


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
        button_settings=(RelativeLayout) view.findViewById(R.id.button_pro);
        button_travel=(RelativeLayout) view.findViewById(R.id.button_travel);
        button_help=(RelativeLayout) view.findViewById(R.id.button_help);
        button_legal=(RelativeLayout) view.findViewById(R.id.button_legal);
        button_logout=(RelativeLayout) view.findViewById(R.id.button_logout);


        button_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileDetails.class));
            }
        });
    }
}

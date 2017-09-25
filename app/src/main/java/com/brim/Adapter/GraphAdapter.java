package com.brim.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brim.FotterFragmnet.Budget;
import com.brim.PagerFragment.LineFrag;
import com.brim.PagerFragment.PieFrag;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by su on 20/9/17.
 */

public class GraphAdapter extends FragmentPagerAdapter {
    String Object;
    Budget budget;


    public GraphAdapter(FragmentManager fragmentManager, Budget budget,String Object) {
        super(fragmentManager);
        this.budget=budget;
        this.Object=Object;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PieFrag(budget,Object);
            case 1:
                return new LineFrag();
            default:
                return null;

        }
    }


}

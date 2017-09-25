package com.brim.AppContant;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by apple on 18/08/17.
 */

public class BrimApplication extends Application{
    private static BrimApplication instnace = null;

    public static BrimApplication getInstnace() {
        return instnace;
    }

    private SharedPreferences authProfilePreference = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instnace = this;
        authProfilePreference = getSharedPreferences(AppConstant.AUTHORITY_PREFERENCE, MODE_APPEND);
    }

    public void SetAuthToken(String Token)
    {
        authProfilePreference.edit().putString(AppConstant.AUTHORITY_TOKEN,Token).commit();
    }

    public String GetAuthToken()
    {
       return authProfilePreference.getString(AppConstant.AUTHORITY_TOKEN,"");
    }


    public void SetUserId(String id)
    {
        authProfilePreference.edit().putString(AppConstant.AUTHORITY_USERID,id).commit();
    }

    public String GetUserId()
    {
        return authProfilePreference.getString(AppConstant.AUTHORITY_USERID,"");
    }

    public void SetName(String name)
    {
        authProfilePreference.edit().putString(AppConstant.NAME,name).commit();
    }

    public String GetName()
    {
        return authProfilePreference.getString(AppConstant.NAME,"");
    }

    public void SetCardId(String id)
    {
        authProfilePreference.edit().putString(AppConstant.CARDID,id).commit();
    }

    public String GetCardId()
    {
        return authProfilePreference.getString(AppConstant.CARDID,"");
    }

    public void SetPass(String id)
    {
        authProfilePreference.edit().putString(AppConstant.PASSWORD,id).commit();
    }

    public String GetPass()
    {
        return authProfilePreference.getString(AppConstant.PASSWORD,"");
    }

    public void SetPin(String id)
    {
        authProfilePreference.edit().putString(AppConstant.PIN,id).commit();
    }

    public String GetPin()
    {
        return authProfilePreference.getString(AppConstant.PIN,"");
    }


}

package com.brim.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by su on 14/9/17.
 */

public class GetDate {

    public static String getDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        Log.d("Today Date","::::::"+currentDate);

        return currentDate;
    }

    public static String getMonth(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String currentDate = sdf.format(new Date());

        Log.d("Current yyyy-MM","::::::"+currentDate);

        return currentDate;
    }

    public static String getPrevMonth(){



        Calendar cal =  Calendar.getInstance();
        cal.add(Calendar.MONTH ,-1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String prevDate = sdf.format(cal.getTime());

        Log.d("Previous yyyy-MM","::::::"+prevDate);

        return prevDate;
    }

    public static String getPrev2Month(){



        Calendar cal =  Calendar.getInstance();
        cal.add(Calendar.MONTH ,-2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String prevDate = sdf.format(cal.getTime());

        Log.d("Previous yyyy-MM","::::::"+prevDate);

        return prevDate;
    }

    public static String getNextMonth(String date){


        Calendar cal =  Calendar.getInstance();
        cal.set(Integer.parseInt(date.toString().substring(0,4)),Integer.parseInt(date.toString().substring(5,7)),Integer.parseInt(date.toString().substring(8,10)));
        //cal.add(Calendar.MONTH ,1);
        cal.add(Calendar.DATE ,30);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String nextDate = sdf.format(cal.getTime());

        Log.d("Next MMM dd, yyyy","::::::"+nextDate);

        return nextDate;
    }

}

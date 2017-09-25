package com.brim.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by su on 14/9/17.
 */

public class DateDifference {


    public static String dateDifference(String startDate, String endDate){
        int diffInDays=0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = simpleDateFormat.parse(startDate);
            Date date2 = simpleDateFormat.parse(endDate);

            //different = date2.compareTo(date1);

            diffInDays = (int) ((date1.getTime() - date2.getTime())/ (1000 * 60 * 60 * 24));

            Log.d("date difference",":::"+diffInDays);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  ""+diffInDays;
    }
}

package com.brim.Utils;

import java.text.DecimalFormat;

/**
 * Created by apple on 18/08/17.
 */

public class ConvertLocal {
    public static String priceWithDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(price);
    }

    public static String priceWithOutDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }
}

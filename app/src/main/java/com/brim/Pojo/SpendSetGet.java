package com.brim.Pojo;

import org.json.JSONObject;

/**
 * Created by su on 20/9/17.
 */

public class SpendSetGet {
    int grand_total;
    int total_amount;
    String percent;

    JSONObject category;

    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public JSONObject getCategory() {
        return category;
    }

    public void setCategory(JSONObject category) {
        this.category = category;
    }
}

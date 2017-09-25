package com.brim.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by su on 20/9/17.
 */

public class BudgetSetGet {

    int max_amount;
    int total_amount;

    JSONObject category;
    JSONArray prev_months;

    public int getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(int max_amount) {
        this.max_amount = max_amount;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public JSONObject getCategory() {
        return category;
    }

    public void setCategory(JSONObject category) {
        this.category = category;
    }

    public JSONArray getPrev_months() {
        return prev_months;
    }

    public void setPrev_months(JSONArray prev_months) {
        this.prev_months = prev_months;
    }
}

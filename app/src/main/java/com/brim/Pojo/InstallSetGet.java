package com.brim.Pojo;

import org.json.JSONObject;

/**
 * Created by su on 14/9/17.
 */

public class InstallSetGet {

    String iamge;
    String name;
    String cate_name;

    String amount;
    String date;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIamge() {
        return iamge;
    }

    public void setIamge(String iamge) {
        this.iamge = iamge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

}

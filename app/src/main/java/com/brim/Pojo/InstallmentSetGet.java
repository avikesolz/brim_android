package com.brim.Pojo;

/**
 * Created by su on 12/9/17.
 */

public class InstallmentSetGet {

    String plan;
    float rate;
    int num_month;
    Long amount;

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getNum_month() {
        return num_month;
    }

    public void setNum_month(int num_month) {
        this.num_month = num_month;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

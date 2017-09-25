package com.brim.Pojo;

/**
 * Created by su on 25/9/17.
 */

public class HistorySetGet {

    String description;
    String amount;
    String points;
    String earn_multiplier;
    String redeem_points_saved;
    String transaction_type;
    String transaction_date;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getEarn_multiplier() {
        return earn_multiplier;
    }

    public void setEarn_multiplier(String earn_multiplier) {
        this.earn_multiplier = earn_multiplier;
    }

    public String getRedeem_points_saved() {
        return redeem_points_saved;
    }

    public void setRedeem_points_saved(String redeem_points_saved) {
        this.redeem_points_saved = redeem_points_saved;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }
}

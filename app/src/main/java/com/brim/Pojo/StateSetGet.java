package com.brim.Pojo;

/**
 * Created by su on 25/9/17.
 */

public class StateSetGet {

    String id;
    String primary_card_id;
    String from_date;
    String to_date;
    String due_date;
    String balance;
    String minimum_payment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimary_card_id() {
        return primary_card_id;
    }

    public void setPrimary_card_id(String primary_card_id) {
        this.primary_card_id = primary_card_id;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMinimum_payment() {
        return minimum_payment;
    }

    public void setMinimum_payment(String minimum_payment) {
        this.minimum_payment = minimum_payment;
    }
}

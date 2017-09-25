package com.brim.Pojo;

import org.json.JSONObject;

/**
 * Created by apple on 17/08/17.
 */

public class TransactionListData {
    String Image;
    String Name;
    String Type;
    String Price;
    String Status;

    String id;
    int total_number_of_payments;
    int number_of_payments_made;
    Long remaining_balance;


    JSONObject ItemObject;

    public JSONObject getItemObject() {
        return ItemObject;
    }

    public void setItemObject(JSONObject itemObject) {
        ItemObject = itemObject;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal_number_of_payments() {
        return total_number_of_payments;
    }

    public void setTotal_number_of_payments(int total_number_of_payments) {
        this.total_number_of_payments = total_number_of_payments;
    }

    public int getNumber_of_payments_made() {
        return number_of_payments_made;
    }

    public void setNumber_of_payments_made(int number_of_payments_made) {
        this.number_of_payments_made = number_of_payments_made;
    }

    public Long getRemaining_balance() {
        return remaining_balance;
    }

    public void setRemaining_balance(Long remaining_balance) {
        this.remaining_balance = remaining_balance;
    }
}

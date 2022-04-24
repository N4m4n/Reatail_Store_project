package com.example.checking;

public class order {
    private int orderID;
    private String dateOfPurchase;
    private float price;

    order(int orderID, String date, float price){
        this.orderID = orderID;
        this.dateOfPurchase = date;
        this.price = price;
    }
    public int getOrderID(){
        return this.orderID;

    }
    public String getDateOfPurchase(){
        return  this.dateOfPurchase;
    }
    public float getPrice(){
        return this.price;
    }
}

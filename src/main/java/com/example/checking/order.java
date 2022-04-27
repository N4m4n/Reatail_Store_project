package com.example.checking;

public class order {
    private int orderID;
    private String modeOfPayment;
    private String date;

    order(int orderID, String modeOfPayment, String date){
        this.orderID = orderID;
        this.modeOfPayment = modeOfPayment;
        this.date = date;
    }
    public int getOrderID(){
        return this.orderID;

    }
    public String getModeOfPayment(){
        return  this.modeOfPayment;
    }
    public String getDate(){
        return this.date;
    }
}

package com.example.checking;

public class complaint {
    private int customerID;
    private int productID;
    private String productName;
    private String comment;
    private String date;
    complaint(int customerID, int productID, String productName, String comment, String date) {
        this.customerID = customerID;
        this.productID = productID;
        this.productName = productName;
        this.comment = comment;
        this.date = date;
    }

    public int getCustomerID() {
        return this.customerID;
    }
    public int getProductID(){
        return this.productID;
    }

    public String getDate(){
        return this.date;
    }
    public String getProductName(){
        return this.productName;
    }
    public String getComment(){
        return this.comment;
    }
}


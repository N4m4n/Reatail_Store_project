package com.example.checking;

public class orderedProducts {
    private int productID;
    private String productName;
    private int quantity;
    private int totalCost;
    private String description;

    public orderedProducts(String productName, int quantity, int totalCost){
        this.productName = productName;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public orderedProducts(int productID, int quantity){
        this.productID = productID;
        this.quantity = quantity;

    }

    public orderedProducts(int productID, String productName, int quantity, int totalCost){
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public String getProductName(){
        return this.productName;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public int getTotalCost(){
        return this.totalCost;
    }
    public int getProductID(){return this.productID;}


}


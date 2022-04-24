package com.example.checking;

public class orderedProducts {
    private String productName;
    private int quantity;
    private int totalCost;
    private String description;

    public orderedProducts(String productName, int quantity, int totalCost){
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

}


package com.example.checking;

public class availableProducts {

    private int quantityAvailable;
    private float price;
    private String description;
    private String prodName;
    private int productID;
    public availableProducts (int productID, String prodName, float price, String desciption, int quantityAvailable ){
        this.price = price;
        this.description = desciption;
        this.productID = productID;
        this.quantityAvailable = quantityAvailable;
        this.prodName = prodName;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getProdName() {
        return prodName;
    }

    public int getProductID() {
        return productID;
    }



}

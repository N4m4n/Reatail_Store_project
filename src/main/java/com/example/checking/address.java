package com.example.checking;

public class address {
    private int addressID;
    private String addressLine;
    private int pinCode;

    address(int addressID, String addressLine, int pinCode){
        this.addressID = addressID;
        this.addressLine = addressLine;
        this.pinCode = pinCode;
    }
    public int getAddressID(){
        return this.addressID;
    }
    public String getAddressLine(){
        return this.addressLine;
    }
    public float getPinCode(){
        return this.pinCode;
    }

}

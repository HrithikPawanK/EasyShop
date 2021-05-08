package com.example.eazyshop;

public class NewCart {
    private String barcode,name,price,quantity;
    public NewCart(){}

    public NewCart(String barcode, String name, String price,String quantity) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.quantity=quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

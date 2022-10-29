package com.mia.apa;

public class Item {

    String code, name, reference1, reference2, qty, price,totalPrice;

    public Item(String code, String name, String reference1, String reference2, String qty, String price, String totalPrice) {
        this.code = code;
        this.name = name;
        this.reference1 = reference1;
        this.reference2 = reference2;
        this.qty = qty;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getReference1() {
        return reference1;
    }

    public String getReference2() {
        return reference2;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}

package com.mia.apa;

public class SpareItem {

    String code, name, make, model, stockPurchased, stockSold, stockAvailable, price, date, supplier, delivery, cashier;

    public SpareItem(String code, String name, String make, String model, String stockPurchased, String stockSold, String stockAvailable, String price, String date, String supplier, String delivery, String cashier) {
        this.code = code;
        this.name = name;
        this.make = make;
        this.model = model;
        this.stockPurchased = stockPurchased;
        this.stockSold = stockSold;
        this.stockAvailable = stockAvailable;
        this.price = price;
        this.date = date;
        this.supplier = supplier;
        this.delivery = delivery;
        this.cashier = cashier;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getStockPurchased() {
        return stockPurchased;
    }

    public String getStockSold() {
        return stockSold;
    }

    public String getStockAvailable() {
        return stockAvailable;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getCashier() {
        return cashier;
    }
}

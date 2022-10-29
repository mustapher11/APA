package com.mia.apa;

public class Rack {

    String itemCode, itemName, make, model, stock, cost, rackNUmber, cashier;

    public Rack(String itemCode, String itemName, String make, String model, String stock, String cost, String rackNUmber, String cashier) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.make = make;
        this.model = model;
        this.stock = stock;
        this.cost = cost;
        this.rackNUmber = rackNUmber;
        this.cashier = cashier;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getStock() {
        return stock;
    }

    public String getCost() {
        return cost;
    }
    public String getRackNUmber() {
        return rackNUmber;
    }

    public String getCashier() {
        return cashier;
    }
}

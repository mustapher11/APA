package com.mia.apa;

import javafx.scene.control.CheckBox;

public class Spare {
    String  code, name, make, model, stock, stock_purchased, stock_sold, cost, supplier;
    CheckBox action;
    public Spare(String code, String name, String make, String model, String stock, String stock_purchased, String stock_sold, String cost, String supplier) {
        this.code = code;
        this.name = name;
        this.make = make;
        this.model = model;
        this.stock = stock;
        this.stock_purchased = stock_purchased;
        this.stock_sold = stock_sold;
        this.cost = cost;
        this.supplier = supplier;
        this.action = new CheckBox();
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

    public String getStock() {
        return stock;
    }
    public String getStock_purchased() {
        return stock_purchased;
    }
    public String getStock_sold() {
        return stock_sold;
    }

    public String getCost() {
        return cost;
    }

    public String getSupplier() {
        return supplier;
    }

    public CheckBox getAction() {
        return action;
    }
}

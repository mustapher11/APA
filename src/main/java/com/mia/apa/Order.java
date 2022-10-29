package com.mia.apa;

public class Order {
    String table;
    String type;
    String name;
    String unitCost;
    String quantity;
    String totalCost;

    public Order(String table, String type, String name, String unitCost, String quantity, String totalCost) {
        this.table = table;
        this.type = type;
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public String getTable() {
        return table;
    }

    public String geType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTotalCost() {
        return totalCost;
    }
}

package com.mia.apa;

public class Stock {

    String availableStock, stockSold, quantity, itemCode;

    public Stock(String availableStock, String stockSold, String quantity, String itemCode) {
        this.availableStock = availableStock;
        this.stockSold = stockSold;
        this.quantity = quantity;
        this.itemCode = itemCode;
    }

    public String getAvailableStock() {
        return availableStock;
    }

    public String getStockSold() {
        return stockSold;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getItemCode() {
        return itemCode;
    }
}

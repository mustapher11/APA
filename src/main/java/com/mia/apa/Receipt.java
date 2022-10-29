package com.mia.apa;

public class Receipt {

    String invoiceNo, itemCode, itemName, make, model, quantity, unitPrice, totalPrice, date, time, cashier;

    public Receipt(String invoiceNo, String itemCode, String itemName, String make, String model, String quantity, String unitPrice, String totalPrice, String date, String time, String cashier) {
        this.invoiceNo = invoiceNo;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.make = make;
        this.model = model;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.date = date;
        this.time = time;
        this.cashier = cashier;
    }

    public String getInvoiceNo() {
        return invoiceNo;
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

    public String getQuantity() {
        return quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCashier() {
        return cashier;
    }
}

package com.mia.apa;

public class Printer {

    String printerName, printerType, cashier;

    public Printer(String printerName, String printerType, String cashier) {
        this.printerName = printerName;
        this.printerType = printerType;
        this.cashier = cashier;
    }

    public String getPrinterName() {
        return printerName;
    }

    public String getPrinterType() {
        return printerType;
    }

    public String getCashier() {
        return cashier;
    }
}

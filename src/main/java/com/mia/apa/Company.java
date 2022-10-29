package com.mia.apa;

public class Company {

    String companyName, phone, cashier;

    public Company(String companyName, String phone, String cashier) {
        this.companyName = companyName;
        this.phone = phone;
        this.cashier = cashier;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPhone() {
        return phone;
    }

    public String getCashier() {
        return cashier;
    }
}

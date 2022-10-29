package com.mia.apa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    static String query;

    public static Connection createConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String URL = "jdbc:mysql://localhost:3308/mainland";
        final String USERNAME = "root";
        final String PASSWORD = "1234";
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static int registerCashier(String firstName, String lastName, String idNumber, String phone, String password) throws Exception {
        query =  "INSERT INTO `cshier`(`first_name`, `last_name`, `id_number`, `phone_number`, `password`) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, idNumber);
        preparedStatement.setString(4, phone);
        preparedStatement.setString(5, password);
        return preparedStatement.executeUpdate();
    }

    public static boolean validateLogInDetails(String id) throws Exception {
        query = "SELECT `id_number` FROM `cshier` WHERE id_number = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    public static boolean validateLogInDetails(String id, String password) throws Exception {
        query = "SELECT `id_number`, `password` FROM `cshier` WHERE id_number = ? AND password = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static int updatePassword(String password, String id) throws Exception{
        query = "UPDATE `cshier` SET `password`= ? WHERE id_number = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, id);
        return preparedStatement.executeUpdate();
    }

    public static boolean checkTable(String tableName) throws Exception {
        query = "SELECT `table_name` FROM `tables` WHERE table_name = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, tableName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static int addTable(String tableName, String id) throws Exception{
        query = "INSERT INTO `tables`(`table_name`, `cashier_id`) VALUES (?, ?)";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, tableName);
        preparedStatement.setString(2, id);
        return preparedStatement.executeUpdate();
    }

    public static int deleteTable(String tableName) throws Exception{
        query = "DELETE FROM `tables` WHERE table_name = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, tableName);
        return preparedStatement.executeUpdate();
    }

    public static ObservableList<Spare> fetchItems() throws Exception {
        ObservableList<Spare> items = FXCollections.observableArrayList();
        query = "SELECT item_code, item_name, make, model, stock, stock_purchased, stock_sold, unit_cost, supplier FROM items WHERE 1";
        Statement statement = createConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            String code = resultSet.getString("item_code");
            String name = resultSet.getString("item_name");
            String ref1 = resultSet.getString("make");
            String ref2 = resultSet.getString("model");
            String stock = resultSet.getString("stock");
            String stock_purchased = resultSet.getString("stock_purchased");
            String stock_sold = resultSet.getString("stock_sold");
            String cost = resultSet.getString("unit_cost");
            String supplier = resultSet.getString("supplier");
            items.add(new Spare(code, name, ref1, ref2, stock, stock_purchased, stock_sold, "Kshs." + cost, supplier));
        }

        return items;
    }

    public static int savePurchase(String receiptNo, String code, String name, String make, String model, String quantity, String cost, String totalPrice, String date, String time, String cashier) throws Exception {
        query = "INSERT INTO `purchases`(`receipt_number`, `item_code`, `item_name`, `reference1`, `reference2`, `quantity`, `unit_price`, `total_price`, `date`, `time`, `cashier`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, receiptNo);
        preparedStatement.setString(2, code);
        preparedStatement.setString(3, name);
        preparedStatement.setString(4, make);
        preparedStatement.setString(5, model);
        preparedStatement.setString(6, quantity);
        preparedStatement.setString(7, cost);
        preparedStatement.setString(8, totalPrice);
        preparedStatement.setString(9, date);
        preparedStatement.setString(10, time);
        preparedStatement.setString(11, cashier);
        return preparedStatement.executeUpdate();
    }

    public static int savePrinter(String printerName, String printerType, String cashier) throws Exception {
        query = "INSERT INTO `printer`(`printer_name`, `printer_type`, `cashier`) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, printerName);
        preparedStatement.setString(2, printerType);
        preparedStatement.setString(3, cashier);
        return preparedStatement.executeUpdate();
    }

    public int saveDeliver(String companyName, String phone, String cashier) throws Exception {
        query = "INSERT INTO `delivery`(`company_name`, `phone`, `cashier`) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, companyName);
        preparedStatement.setString(2, phone);
        preparedStatement.setString(3, cashier);
        return preparedStatement.executeUpdate();
    }

    public ObservableList<Printer> fetchPrinters() throws Exception {
        ObservableList<Printer> printers = FXCollections.observableArrayList();
        query = "SELECT printer_name, printer_type, cashier FROM printer WHERE 1";
        Statement statement = createConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            String name = resultSet.getString("printer_name");
            String type = resultSet.getString("printer_type");
            String cashier = resultSet.getString("cashier");
            printers.add(new Printer(name, type, cashier));
        }
        return printers;
    }

    public ObservableList<Company> fetchCompanies() throws Exception {
        ObservableList<Company> companies = FXCollections.observableArrayList();
        query = "SELECT company_name, phone, cashier FROM delivery WHERE 1";
        Statement statement = createConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            String name = resultSet.getString("company_name");
            String phone = resultSet.getString("phone");
            String cashier = resultSet.getString("cashier");
            companies.add(new Company(name, phone, cashier));
        }
        return companies;
    }

    public static int updateStock(String newStockAvailable, String newStockSOld, String itemCode) throws Exception {
        query = "UPDATE `items` SET `stock`= ?,`stock_sold`= ? WHERE item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, newStockAvailable);
        preparedStatement.setString(2, newStockSOld);
        preparedStatement.setString(3, itemCode);
        return preparedStatement.executeUpdate();
    }

    public static ObservableList<Receipt> fetchReceipts() throws Exception {
        ObservableList<Receipt> receipts = FXCollections.observableArrayList();
        query = "SELECT receipt_number, item_code, item_name, reference1, reference2, quantity, unit_price, total_price, date, time, cashier FROM purchases WHERE 1";
        Statement statement = createConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            String number = resultSet.getString("receipt_number");
            String code = resultSet.getString("item_code");
            String name = resultSet.getString("item_name");
            String make = resultSet.getString("reference1");
            String model = resultSet.getString("reference2");
            String quantity = resultSet.getString("quantity");
            String unitPrice = resultSet.getString("unit_price");
            String totalPrice = resultSet.getString("total_price");
            String date = resultSet.getString("date");
            String time = resultSet.getString("time");
            String cashier = resultSet.getString("cashier");

            receipts.add(new Receipt(number, code, name, make, model, quantity, "Kshs." + unitPrice, "Kshs." + totalPrice, date, time, cashier));
        }
        return receipts;
    }

    public static ObservableList<SpareItem> fetchSpares() throws Exception {
        ObservableList<SpareItem> spareItems = FXCollections.observableArrayList();
        query = "SELECT * FROM `items` WHERE 1";
        Statement statement = createConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            String code = resultSet.getString("item_code");
            String name = resultSet.getString("item_name");
            String make = resultSet.getString("make");
            String model = resultSet.getString("model");
            String stockPurchased = resultSet.getString("stock_purchased");
            String stockAvailable = resultSet.getString("stock");
            String stockSold = resultSet.getString("stock_sold");
            String unitCost = resultSet.getString("unit_cost");
            String date = resultSet.getString("date");
            String supplier = resultSet.getString("supplier");
            String delivery = resultSet.getString("delivery");
            String cashier = resultSet.getString("cashier");

            spareItems.add(new SpareItem(code, name, make, model, stockPurchased, stockSold, stockAvailable, "Kshs." + unitCost, date, supplier, delivery, cashier));
        }
        return spareItems;
    }

    public static int updateProduct(String code, String name, String make, String model, String stock, String stockPurchased, String unitCost, String date, String supplier, String delivery) throws Exception {
        query = "UPDATE `items` SET `item_code`= ?,`item_name`= ?,`make`= ?,`model`= ?, `stock`=?, `stock_purchased`= ?, `unit_cost`= ?,`date`= ?,`supplier`= ?,`delivery`= ? WHERE item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, code);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, make);
        preparedStatement.setString(4, model);
        preparedStatement.setString(5, stock);
        preparedStatement.setString(6, stockPurchased);
        preparedStatement.setString(7, unitCost);
        preparedStatement.setString(8, date);
        preparedStatement.setString(9, supplier);
        preparedStatement.setString(10, delivery);
        preparedStatement.setString(11, code);

        return preparedStatement.executeUpdate();
    }

    public static void deleteProduct(String itemCode) throws Exception {
        query = "DELETE FROM `items` WHERE item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, itemCode);
        preparedStatement.executeUpdate();
    }

    public static int insertProduct(String code, String name, String make, String model, String stockAvailable, String stockPurchased, String stockSold, String unitCost, String date, String supplier, String delivery, String cashier) throws Exception {
        query = "INSERT INTO `items`(`item_code`, `item_name`, `make`, `model`, `stock`, `stock_purchased`, `stock_sold`, `unit_cost`, `date`, `supplier`, `delivery`, `cashier`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, code);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, make);
        preparedStatement.setString(4, model);
        preparedStatement.setString(5, stockAvailable);
        preparedStatement.setString(6, stockPurchased);
        preparedStatement.setString(7, stockSold);
        preparedStatement.setString(8, unitCost);
        preparedStatement.setString(9, date);
        preparedStatement.setString(10, supplier);
        preparedStatement.setString(11, delivery);
        preparedStatement.setString(12, cashier);

        return preparedStatement.executeUpdate();
    }

    public static boolean checkSpareItem(String itemCode) throws Exception {
        query = "SELECT `item_code` FROM `items` WHERE item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, itemCode);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static int updateReceipt(String quantity, String totalPrice, String receiptNumber, String itemCode) throws Exception {
        query = "UPDATE `purchases` SET `quantity`= ?, `total_price`= ? WHERE receipt_number = ? AND item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, quantity);
        preparedStatement.setString(2, totalPrice);
        preparedStatement.setString(3, receiptNumber);
        preparedStatement.setString(4, itemCode);

        return preparedStatement.executeUpdate();
    }

    public static void deleteReceipt(String receiptNo, String itemCode) throws Exception {
        query = "DELETE FROM `purchases` WHERE receipt_number = ? AND item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, receiptNo);
        preparedStatement.setString(2, itemCode);
        preparedStatement.executeUpdate();
    }

    public static int insertRack(String itemCode, String itemName, String make, String model, String stockPurchased, String cost, String position, String cashier) throws Exception {
        query = "INSERT INTO `rack` (`item_code`, `item_name`, `reference1`, `reference2`, `stock`, `unit_cost`, `rack_number`, `cashier`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, itemCode);
        preparedStatement.setString(2, itemName);
        preparedStatement.setString(3, make);
        preparedStatement.setString(4, model);
        preparedStatement.setString(5, stockPurchased);
        preparedStatement.setString(6, cost);
        preparedStatement.setString(7, position);
        preparedStatement.setString(8, cashier);

        return preparedStatement.executeUpdate();
    }

    public static ObservableList<Rack> fetchRackDetails() throws Exception {
        ObservableList<Rack> racks = FXCollections.observableArrayList();
        query = "SELECT * FROM `rack` WHERE 1";
        Statement statement = createConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            String code = resultSet.getString("item_code");
            String name = resultSet.getString("item_name");
            String make = resultSet.getString("reference1");
            String model = resultSet.getString("reference2");
            String stock = resultSet.getString("stock");
            String cost = resultSet.getString("unit_cost");
            String number = resultSet.getString("rack_number");
            String cashier = resultSet.getString("cashier");
            racks.add(new Rack(code, name, make, model, stock, "Kshs." + cost, number, cashier));
        }

        return  racks;
    }

    public static void deleteRack(String rackNumber) throws Exception {
        query = "DELETE FROM `rack` WHERE rack_number = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, rackNumber);
        preparedStatement.executeUpdate();
    }

    public static boolean checkSpareItemInRack(String itemCode) throws Exception {
        query = "SELECT `item_code` FROM `rack` WHERE item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, itemCode);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static int updateRack(String itemCode, String itemName, String make, String model, String stockPurchased, String cost, String position, String cashier) throws Exception {
        query = "UPDATE `rack` SET `item_code`=?,`item_name`=?,`reference1`=?,`reference2`=?,`stock`=?,`unit_cost`=?,`rack_number`=?,`cashier`=? WHERE item_code = ?";
        PreparedStatement preparedStatement = createConnection().prepareStatement(query);
        preparedStatement.setString(1, itemCode);
        preparedStatement.setString(2, itemName);
        preparedStatement.setString(3, make);
        preparedStatement.setString(4, model);
        preparedStatement.setString(5, stockPurchased);
        preparedStatement.setString(6,cost);
        preparedStatement.setString(7, position);
        preparedStatement.setString(8, cashier);
        preparedStatement.setString(9, itemCode);

        return preparedStatement.executeUpdate();
    }
}

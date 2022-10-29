package com.mia.apa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsSectionController implements Initializable {
    public MenuItem addTable, showMenu, logOut, dailySales;
    public BorderPane borderPane;
    public VBox vbox;
    public MenuItem contactUs;
    public SplitMenuButton addFood, addDrink;
    public ComboBox<String> tableList, tableList2;
    public ComboBox<String> categoryList, drinkType;
    public ComboBox<String> foodList, drink;
    public TextField cost, drinkCost;
    public TextField quantity, drinkQuantity;
    public TextField total, total2;
    public TextArea receipt;
    public MenuItem clearFoodQueue, clearDrinkQueue;
    public SplitMenuButton print, generate;
    ObservableList<Order> foodOrders, drinkOrders;
    static String id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foodOrders = FXCollections.observableArrayList();
        drinkOrders = FXCollections.observableArrayList();
        receipt.setEditable(false);
        receipt.setWrapText(true);

        id = LogInController.getIdText();
    }
    public void showMenu(){
        CreateScene.changeSceneModal("menu.fxml", "Menu", false);
    }

    public void logOut(){
        CreateScene.changeSceneModal("login.fxml", "Log In", false);
        borderPane.getScene().getWindow().hide();
    }

    public void addFoodToQueue(){
        String selectedTable = tableList.getValue();
        String selectedCategory = categoryList.getValue();
        String selectedFood = foodList.getValue();
        String generatedCost = cost.getText().trim();
        String purchasedQuantity = quantity.getText().trim();
        String calculatedCost = total.getText().trim();

        if (selectedTable == null && selectedCategory == null && selectedFood == null && generatedCost.isEmpty()
                && purchasedQuantity.isEmpty() && calculatedCost.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        }else if (selectedTable == null){
            AlertMessage.showErrorAlert("Please select a table!");

        } else if (selectedCategory == null) {
            AlertMessage.showErrorAlert("Please select a food category!");

        } else if (selectedFood == null) {
            AlertMessage.showErrorAlert("No food selected!");

        } else if (generatedCost.isEmpty()) {
            AlertMessage.showErrorAlert("Please enter the cost!");

        } else if (purchasedQuantity.isEmpty()) {
            AlertMessage.showErrorAlert("Please enter quantity!");

        } else if (calculatedCost.isEmpty()) {
            AlertMessage.showErrorAlert("Please enter the total cost!");

        }else {
            foodOrders.add(new Order(selectedTable, selectedCategory, selectedFood, generatedCost, purchasedQuantity, calculatedCost));
        }
    }

    public void addDrinkToQueue(){
        String selectedTable = tableList2.getValue();
        String selectedType = drinkType.getValue();
        String selectedDrink = drink.getValue();
        String generatedCost = drinkCost.getText().trim();
        String quantity = drinkQuantity.getText().trim();
        String calculatedCost = total2.getText().trim();

        if (selectedTable == null && selectedType == null && selectedDrink == null && generatedCost.isEmpty()
                && quantity.isEmpty() && calculatedCost.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        }else if (selectedTable == null){
            AlertMessage.showErrorAlert("Please select a table!");

        } else if (selectedType == null) {
            AlertMessage.showErrorAlert("Please select a drink category!");

        } else if (selectedDrink == null) {
            AlertMessage.showErrorAlert("No drink selected!");

        } else if (generatedCost.isEmpty()) {
            AlertMessage.showErrorAlert("Please enter the cost!");

        } else if (quantity.isEmpty()) {
            AlertMessage.showErrorAlert("Please enter quantity!");

        } else if (calculatedCost.isEmpty()) {
            AlertMessage.showErrorAlert("Please enter the total cost!");

        }else {
            drinkOrders.add(new Order(selectedTable, selectedType, selectedDrink, generatedCost, quantity, calculatedCost));
        }
    }

    public void clearFoodOrderList(){
        if (!foodOrders.isEmpty()){
            foodOrders.clear();
        }
    }

    public void clearDrinkOrderList(){
        if (!drinkOrders.isEmpty()){
            drinkOrders.clear();
        }
    }
    public void printReceipt(){
        ObservableSet<Printer> printers = Printer.getAllPrinters();
        String generatedReceipt = receipt.getText().trim();

        if (!generatedReceipt.isEmpty() || !generatedReceipt.isBlank()){
            if (printers != null && !printers.isEmpty()){
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                boolean isPrinted = printerJob.printPage(receipt);

                if (isPrinted){
                    printerJob.endJob();
                    Notification.createSuccessNotification("Success", "Receipt successfully printed!");

                }else {
                    AlertMessage.showErrorAlert("Receipt could not be printed! Please try again.");
                }
            }else {
                AlertMessage.showErrorAlert("No printers available! Please connect to a printer.");
            }
        }else {
            AlertMessage.showErrorAlert("No receipt to be printed!");
        }
    }

    public void saveTable(){
        CreateScene.changeSceneModal("add-table.fxml", "Table", false);
    }

    public void showNotification(){
        Notification.createSuccessNotification("Order status", "Order successfully placed!");
    }

    public void showHelpNotification(){
        Notification.showHelpNotification();
    }

    public void foodOrder(){
        if (!foodOrders.isEmpty()){
            for (Order foodOrder : foodOrders) {
                receipt.appendText("Table:\t" + foodOrder.getTable() + "\n");
                receipt.appendText("Category:\t" + foodOrder.geType() + "\n");
                receipt.appendText("Food:\t" + foodOrder.getName() + "\n");
                receipt.appendText("Unit cost:\t" + foodOrder.getUnitCost() + "\n");
                receipt.appendText("Quantity:\t" + foodOrder.getQuantity() + "\n");
                receipt.appendText("Total Cost:\t" + foodOrder.getTotalCost() + "\n");
            }
        }
    }

    public void drinkOrder(){
        if (!drinkOrders.isEmpty()){
            for (Order drinkOrder : drinkOrders) {
                receipt.appendText("Table:\t" + drinkOrder.getTable() + "\n");
                receipt.appendText("Category:\t" + drinkOrder.geType() + "\n");
                receipt.appendText("Food:\t" + drinkOrder.getName() + "\n");
                receipt.appendText("Unit cost:\t" + drinkOrder.getUnitCost() + "\n");
                receipt.appendText("Quantity:\t" + drinkOrder.getQuantity() + "\n");
                receipt.appendText("Total Cost:\t" + drinkOrder.getTotalCost() + "\n");
            }
        }
    }

    public void receiptHeader(){
        if (!foodOrders.isEmpty() || !drinkOrders.isEmpty()){
            receipt.clear();
            receipt.setText("NEW MAINLAND RESTAURANT\n\n");
            receipt.appendText("CITY:\t\tMOMBASA-KENYA\n");
            receipt.appendText("LOCATION:\tSABASABA, MAJENGO\n");
            receipt.appendText("*********************************************");
        }else {
            AlertMessage.showErrorAlert("No receipt to be printed!");
        }
    }

    public void generateReceipt(){
        receiptHeader();
        foodOrder();
        drinkOrder();
    }

    public void clearReceipt(){
        receipt.clear();
    }

    public static String getId(){
        return id;
    }
}
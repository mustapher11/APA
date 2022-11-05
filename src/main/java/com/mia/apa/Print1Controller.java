package com.mia.apa;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Print1Controller implements Initializable {

    public VBox vbox;
    public TextArea textArea;
    public Button close, print;
    String code, name, ref1, ref2, quantity, cost, totalCost, date, time, cashierID;
    ImageView closeImage, printImage;
    int receiptNo, updatedStockAvailable, updateStockSold, totalSum = 0;
    ObservableList<Item> queue;
    ObservableList<Stock> stocks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        code = PurchaseSectionController.getCode();
        name = PurchaseSectionController.getSpareName();
        ref1 = PurchaseSectionController.getSpareRef1();
        ref2 = PurchaseSectionController.getSpareRef2();
        quantity = PurchaseSectionController.getSpareQuantity();
        cost = PurchaseSectionController.getSpareCost();
        totalCost = PurchaseSectionController.getSpareTotal();
        date = DateClass.generateDate();
        time = DateClass.generateTime();
        cashierID = LogInController.getIdText();
        queue = PurchaseSectionController.getQueue();
        stocks = PurchaseSectionController.getStockObservableList();
        receiptNo = Invoice.invoiceNumber();
        updatedStockAvailable = PurchaseSectionController.getNewStockAvailable();
        updateStockSold = PurchaseSectionController.getNewStockSold();

        System.out.println(updatedStockAvailable + "  " + updateStockSold);

        textArea.setEditable(false);
        createImageButtons();
        createReceipt();
    }

    public void createImageButtons(){
        closeImage = new ImageView("icons8-close-window-96.png");
        closeImage.setFitHeight(30.0);
        closeImage.setPreserveRatio(true);

        printImage = new ImageView("icons8-print-96.png");
        printImage.setFitHeight(30.0);
        printImage.setPreserveRatio(true);

        print.setGraphic(printImage);
        close.setGraphic(closeImage);
    }

    public void closeReceipt() {
        vbox.getScene().getWindow().hide();
    }

    public void printReceipt() throws Exception {
        ObservableSet<Printer> printers = Printer.getAllPrinters();
        String generatedReceipt = textArea.getText().trim();
        int rowsAffected = 0;
        int updatedField = 0;

        Font font = Font.font("Freeroad Bold.ttf", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 8);
        textArea.setFont(font);

        if (!queue.isEmpty()){
            for (Item item: queue){
                rowsAffected = rowsAffected +  Database.savePurchase(receiptNo + "", item.getCode(), item.getName(), item.getReference1(), item.getReference2(), item.getQty(), item.getPrice(), item.getTotalPrice(), date, time, cashierID);
            }
        }else {
            rowsAffected = rowsAffected + Database.savePurchase(receiptNo + "", code, name, ref1, ref2, quantity, cost, totalSum + "", date, time, cashierID);
        }

        if (!generatedReceipt.isEmpty() || !generatedReceipt.isBlank()){
            if (printers != null && !printers.isEmpty()){
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                JobSettings jobSettings = printerJob.getJobSettings();
                PageLayout pageLayout = jobSettings.getPageLayout();
                for (Printer printer: printers){
                    pageLayout = printer.createPageLayout(Paper.A0, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL);
                }

                jobSettings.setPageLayout(pageLayout);
                jobSettings.setPrintQuality(PrintQuality.HIGH);

                if (rowsAffected > 0){
                    if (!stocks.isEmpty()){
                        for (Stock stock: stocks) {
                            int newAvailableStock = Integer.parseInt(stock.getAvailableStock()) - Integer.parseInt(stock.getQuantity());
                            int newSoldStock = Integer.parseInt(stock.getStockSold()) + Integer.parseInt(stock.getQuantity());
                            updatedField = updatedField +  Database.updateStock(newAvailableStock + "", newSoldStock + "", stock.getItemCode());
                        }
                    }else {
                        updatedField = updatedField +  Database.updateStock(updatedStockAvailable + "", updateStockSold + "", code);
                    }
                    if (updatedField > 0){
                        boolean isPrinted = printerJob.printPage(pageLayout, textArea);
                        if (isPrinted){
                            printerJob.endJob();
                            clearData();
                            AlertMessage.showSuccessAlert("Receipt successfully printed!");
                        }else {
                            AlertMessage.showErrorAlert("Receipt could not be printed! Please try again.");
                        }
                    }
                }
            }else {
                AlertMessage.showErrorAlert("No printers available! Please connect a printer.");
            }
        }else {
            AlertMessage.showErrorAlert("No receipt to be printed!");
        }
    }

    public void createReceipt(){
        textArea.clear();
        textArea.setText("\t\tALI POLE AUTO SPARES\n");
        textArea.appendText("\t\tDEALERS IN AUTO MOBILE SPARES\n");
        textArea.appendText("\t\tMAJENGO-MVITA \n\t\tOPPOSITE UWANJA WA JOHO\n");
        textArea.appendText("\t\tTEL: +254712061300\n\n");
        textArea.appendText("Receipt No: " + receiptNo + "\n");
        textArea.appendText("Date: " + date + "\n");
        textArea.appendText("Time: " + time + "\n");
        textArea.appendText("Sales Person ID: " + cashierID +"\n\n");

        if (!queue.isEmpty()){
            for (Item item : queue) {
                textArea.appendText("ITEM:\t" + item.getName() + "\n");
                textArea.appendText("QTY:\t\t" + item.getQty() + "\n");
                textArea.appendText("AMOUNT:\t" + "kshs." + item.getPrice() + ".00" + "\n");
                textArea.appendText("TOTAL COST:\t" + "kshs." + item.getTotalPrice() + ".00" + "\n\n");
                totalSum = totalSum + Integer.parseInt(item.getTotalPrice());
            }
        }else {
            if (!"".equals(name) && !"".equals(quantity) && !"".equals(cost) && !"".equals(totalCost)){
                textArea.appendText("ITEM:\t" + name + "\n");
                textArea.appendText("QTY:\t\t" + quantity + "\n");
                textArea.appendText("AMOUNT:\t" + "kshs." + cost + ".00" + "\n");
                textArea.appendText("COST:\t" + "kshs." + totalCost + ".00" + "\n\n");
                totalSum = Integer.parseInt(Objects.requireNonNullElse(totalCost, "0"));
            }
        }
        textArea.appendText("TOTAL PRICE:\t" + "kshs." + totalSum +  ".00");
    }

    public void clearData(){
        textArea.clear();
        code = null;
        name = null;
        ref1 = null;
        ref2 = null;
        quantity = null;
        cost = "0.00";
        totalCost = "0.00";
    }
}

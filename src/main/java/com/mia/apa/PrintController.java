package com.mia.apa;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrintController implements Initializable {

    public Button generate, close;

    public VBox vbox;
    public TextArea textArea;
    ImageView generateImage, closeImage;
    String code, name, ref1, ref2, quantity, cost, totalCost, date, time, cashierID;
    ObservableList<Item> queue;
    int receiptNo, totalSum = 0;

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
        receiptNo = Invoice.invoiceNumber();

        createImageButtons();
        createReceipt();
    }

    public void createImageButtons(){
        closeImage = new ImageView("icons8-close-window-96.png");
        closeImage.setFitHeight(30.0);
        closeImage.setPreserveRatio(true);

        generateImage = new ImageView("icons8-general-ledger-96.png");
        generateImage.setFitHeight(30.0);
        generateImage.setPreserveRatio(true);

        generate.setGraphic(generateImage);
        close.setGraphic(closeImage);
    }

    public void closeReceipt(){
        vbox.getScene().getWindow().hide();
    }

    public void generateReceipt(){
        Path filePath = Path.of("C:\\Users\\user\\Desktop\\Receipts");
        File file = new File(filePath.toUri());
        boolean directoryCreated;

        Path path = Path.of(filePath + "\\" + receiptNo + ".pdf");
        if (!file.exists()){
            directoryCreated = file.mkdir();
            if (directoryCreated){
                if (!textArea.getText().trim().isEmpty()){
                    try {
                        Files.writeString(path, textArea.getText().trim());
                        AlertMessage.showSuccessAlert("Receipt successfully generated!");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    AlertMessage.showErrorAlert("Cannot generate an empty receipt!");
                }
            }else {
                AlertMessage.showErrorAlert("An error has occurred during directory creation!");
            }
        }else {
            if (!textArea.getText().trim().isEmpty()){
                try {
                    Files.writeString(path, textArea.getText().trim());
                    AlertMessage.showSuccessAlert("Receipt successfully generated!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                AlertMessage.showErrorAlert("Cannot generate an empty receipt!");
            }
        }
    }

    public void createReceipt(){
        textArea.clear();
        textArea.setText("ALI POLE AUTO SPARES\n");
        textArea.appendText("DEALERS IN AUTO MOBILE SPARES & TOOLS\n");
        textArea.appendText("MAJENGO-MVITA OPPOSITE UWANJA WA JOHO\n");
        textArea.appendText("TEL: +254712061300\n\n");
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
                textArea.appendText("AMOUNT:\t" + "kshs." + cost + "0.00" + "\n");
                textArea.appendText("TOTAL COST:\t" + "kshs." + totalCost + "0.00" + "\n\n");
                totalSum = Integer.parseInt(Objects.requireNonNullElse(totalCost, "0"));
            }
        }
        textArea.appendText("TOTAL PRICE FOR ITEMS:\t" + "kshs." + totalSum +  ".00");
    }
}

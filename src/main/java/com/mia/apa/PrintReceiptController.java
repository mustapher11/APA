package com.mia.apa;

import javafx.collections.ObservableSet;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrintReceiptController implements Initializable {

    public VBox vbox;
    public TextArea textArea;

    public Button print, close;
    ImageView closeImage, printImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void closeReceiptSection() {
        vbox.getScene().getWindow().hide();
    }

    public void printReceipt() {
        ObservableSet<Printer> printers = javafx.print.Printer.getAllPrinters();
        String generatedReceipt = textArea.getText().trim();
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        JobSettings jobSettings = printerJob.getJobSettings();
        PageLayout pageLayout = jobSettings.getPageLayout();

        if (!generatedReceipt.isEmpty() || !generatedReceipt.isBlank()) {
            if (printers != null && !printers.isEmpty()) {
                for (Printer printer : printers) {
                    pageLayout = printer.createPageLayout(Paper.A1, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL);
                }
            }
        }
        jobSettings.setPageLayout(pageLayout);

        if (!textArea.getText().isEmpty()){
            boolean isPrinted = printerJob.printPage(pageLayout, textArea);
            if (isPrinted) {
                printerJob.endJob();
                textArea.clear();
                AlertMessage.showSuccessAlert("Receipt successfully printed!");
            }
        }else {
            AlertMessage.showErrorAlert("Cannot print an empty receipt!");
        }

    }

    public void createReceipt(){
        String receiptNo = ReceiptController.getInvoice();
        String date = ReceiptController.getDateText();
        String time = ReceiptController.getTimeText();
        String cashierID = LogInController.getIdText();
        String name = ReceiptController.getNameString();
        String quantity = ReceiptController.getQuantityString();
        String cost = ReceiptController.getPriceString();
        String totalCost = ReceiptController.getTotalString();
        int totalSum = 0;

        textArea.clear();
        textArea.setText("\t\tALI POLE AUTO SPARES\n");
        textArea.appendText("\t\tDEALERS IN AUTO MOBILE SPARES\n");
        textArea.appendText("\t\tMAJENGO-MVITA \n\t\tOPPOSITE UWANJA WA JOHO\n");
        textArea.appendText("\t\tTEL: +254712061300\n\n");
        textArea.appendText("Receipt No: " + receiptNo + "\n");
        textArea.appendText("Date: " + date + "\n");
        textArea.appendText("Time: " + time + "\n");
        textArea.appendText("Sales Person ID: " + cashierID +"\n\n");

        if (!"".equals(name) && !"".equals(quantity) && !"".equals(cost) && !"".equals(totalCost)) {
            textArea.appendText("ITEM:\t" + name + "\n");
            textArea.appendText("QTY:\t\t" + quantity + "\n");
            textArea.appendText("AMOUNT:\t" + "kshs." + cost + ".00" + "\n");
            textArea.appendText("COST:\t" + "kshs." + totalCost + ".00" + "\n\n");
            totalSum = Integer.parseInt(Objects.requireNonNullElse(totalCost, "0"));
        }
        textArea.appendText("TOTAL PRICE:\t" + "kshs." + totalSum +  ".00");
    }
}

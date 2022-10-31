package com.mia.apa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

    public Button refresh, update, total, clear, delete, print, exit;

    public VBox vbox;
    public TableView<Receipt> receiptTable;
    public TableColumn<Receipt, String> invoiceNo;

    public TableColumn<Receipt, String> itemCode;

    public TableColumn<Receipt, String> itemName;

    public TableColumn<Receipt, String> make;

    public TableColumn<Receipt, String> model;

    public TableColumn<Receipt, String> quantity;
    public TableColumn<Receipt, String> unitPrice;
    public TableColumn<Receipt, String> totalPrice;

    public TableColumn<Receipt, String> date;
    public TableColumn<Receipt, String> time;

    public TableColumn<Receipt, String> cashier;
    public TextField searchReceipt;

    public TextField number, code, name, makeText, modelText, quantityText, price, totalText;
    ObservableList<Receipt> receipts = FXCollections.observableArrayList();
    ImageView refreshImage, updateImage, totalImage, clearImage, deleteImage, printImage, exitImage;
    static String dateText, timeText, invoice, nameString, quantityString, priceString, totalString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            receipts.addAll(Database.fetchReceipts());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        number.setEditable(false);
        code.setEditable(false);
        name.setEditable(false);
        makeText.setEditable(false);
        modelText.setEditable(false);
        totalText.setEditable(false);

        createButtons();
        setReceiptTable();
        fetchReceiptData();
        try {
            filterReceipts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createButtons(){
        refreshImage = new ImageView("icons8-refresh-96.png");
        refreshImage.setFitHeight(30.0);
        refreshImage.setPreserveRatio(true);

        updateImage = new ImageView("icons8-update-96.png");
        updateImage.setFitHeight(30.0);
        updateImage.setPreserveRatio(true);

        totalImage = new ImageView("icons8-plus-math-96.png");
        totalImage.setFitHeight(30.0);
        totalImage.setPreserveRatio(true);

        clearImage = new ImageView("icons8-clear-symbol-96.png");
        clearImage.setFitHeight(30.0);
        clearImage.setPreserveRatio(true);

        deleteImage = new ImageView("icons8-delete-96.png");
        deleteImage.setFitHeight(30.0);
        deleteImage.setPreserveRatio(true);

        printImage = new ImageView("icons8-print-96.png");
        printImage.setFitHeight(30.0);
        printImage.setPreserveRatio(true);

        exitImage = new ImageView("icons8-logout-96.png");
        exitImage.setFitHeight(30.0);
        exitImage.setPreserveRatio(true);

        refresh.setGraphic(refreshImage);
        update.setGraphic(updateImage);
        total.setGraphic(totalImage);
        delete.setGraphic(deleteImage);
        print.setGraphic(printImage);
        clear.setGraphic(clearImage);
        exit.setGraphic(exitImage);
    }

    public void exitReceiptSection() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.load();
        LogInController controller = loader.getController();
        controller.logInButton();
        vbox.getScene().getWindow().hide();
    }

    public void setReceiptTable(){
        invoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        itemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        cashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));

        receiptTable.setItems(receipts);
        receiptTable.getColumns().clear();
        receiptTable.getColumns().add(invoiceNo);
        receiptTable.getColumns().add(itemCode);
        receiptTable.getColumns().add(itemName);
        receiptTable.getColumns().add(make);
        receiptTable.getColumns().add(model);
        receiptTable.getColumns().add(quantity);
        receiptTable.getColumns().add(unitPrice);
        receiptTable.getColumns().add(totalPrice);
        receiptTable.getColumns().add(date);
        receiptTable.getColumns().add(time);
        receiptTable.getColumns().add(cashier);
    }

    public void refreshReceipts() throws Exception {
        receipts = Database.fetchReceipts();
        filterReceipts();
    }

    public void fetchReceiptData(){
        receiptTable.setRowFactory(tv -> {
            TableRow<Receipt> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2){
                    //dateText, timeText, invoice, nameString, quantityString, priceString, totalString
                    number.setText(tableRow.getItem().getInvoiceNo());
                    code.setText(tableRow.getItem().getItemCode());
                    name.setText(tableRow.getItem().getItemName());
                    makeText.setText(tableRow.getItem().getMake());
                    modelText.setText(tableRow.getItem().getModel());
                    quantityText.setText(tableRow.getItem().getQuantity());
                    price.setText(tableRow.getItem().getUnitPrice().replaceFirst("Kshs.", ""));
                    totalText.setText(tableRow.getItem().getTotalPrice().replaceFirst("Kshs.", ""));

                    dateText = tableRow.getItem().getDate().trim();
                    timeText = tableRow.getItem().getTime().trim();
                    invoice = number.getText().trim();
                    nameString = name.getText().trim();
                    quantityString = quantityText.getText().trim();
                    priceString = price.getText().trim();
                    totalString = totalText.getText().trim();
                }
                });
            return tableRow;
        });
    }

    public void filterReceipts() {
        invoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        itemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        cashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));

        FilteredList<Receipt> receiptFilteredList = new FilteredList<>(receipts, b -> true);
        searchReceipt.textProperty().addListener((observable, oldValue, newValue) -> receiptFilteredList.setPredicate(Receipt -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKeyWord = newValue.toLowerCase();
            if (Receipt.getInvoiceNo().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getItemCode().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getItemName().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getMake().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getModel().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getQuantity().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getUnitPrice().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getTotalPrice().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getDate().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Receipt.getTime().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else {
                return Receipt.getCashier().toLowerCase().contains(searchKeyWord);
            }

        }));
        SortedList<Receipt> sortedList = new SortedList<>(receiptFilteredList);
        sortedList.comparatorProperty().bind(receiptTable.comparatorProperty());
        receiptTable.setItems(sortedList);
    }

    public void clearTextFields(){
        number.clear();
        code.clear();
        name.clear();
        makeText.clear();
        modelText.clear();
        quantityText.clear();
        price.clear();
        totalText.clear();
    }

    public void setTotalPrice(){
        String qnty = quantityText.getText().trim();
        String cost = price.getText().trim();

        if (!qnty.isEmpty() && !cost.isEmpty()){
            int totalCost = Integer.parseInt(qnty) * Integer.parseInt(cost);
            totalText.setText(totalCost + "");

        } else if (qnty.isEmpty() && cost.isEmpty()) {
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (qnty.isEmpty()) {
            AlertMessage.showErrorAlert("Quantity cannot be empty!");

        } else {
            AlertMessage.showErrorAlert("Cost cannot be empty!");
        }
    }

    public void setPrint(){
        CreateScene.changeSceneModal("print_receipt.fxml","Print", false);
    }

    public void updateButton() throws Exception {
        String invoiceNo = number.getText().trim();
        String itemCode = code.getText().trim();
        String itemName = name.getText().trim();
        String make = makeText.getText().trim();
        String model = modelText.getText().trim();
        String qnty = quantityText.getText().trim();
        String cost = price.getText().trim();
        String total = totalText.getText().trim();

        if (invoiceNo.isEmpty() && itemCode.isEmpty() && itemName.isEmpty() && make.isEmpty() && model.isEmpty() && qnty.isEmpty()
         && cost.isEmpty() && total.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (invoiceNo.isEmpty()) {
            AlertMessage.showErrorAlert("Invoice Number must be filled!");

        } else if (itemCode.isEmpty()) {
            AlertMessage.showErrorAlert("Item Code must br filled!");

        } else if (itemName.isEmpty()) {
            AlertMessage.showErrorAlert("Item Name must br filled!");

        } else if (make.isEmpty()) {
            AlertMessage.showErrorAlert("Item Make must br filled!");

        } else if (model.isEmpty()) {
            AlertMessage.showErrorAlert("Item Model must br filled!");

        } else if (qnty.isEmpty()) {
            AlertMessage.showErrorAlert("Quantity must br filled!");

        } else if (cost.isEmpty()) {
            AlertMessage.showErrorAlert("Item Cost must br filled!");

        } else if (total.isEmpty()) {
            AlertMessage.showErrorAlert("Total Cost must br filled!");

        }else {
            int rowsAffected = Database.updateReceipt(qnty, total, invoiceNo, itemCode);
            if (rowsAffected > 0){
                AlertMessage.showSuccessAlert("Receipt successfully updated!");
                clearTextFields();
                refreshReceipts();
                filterReceipts();
            }else {
                AlertMessage.showErrorAlert("An error has occurred!");
            }
        }
    }

    public void deleteButton() throws Exception {
        String invoiceNo = number.getText().trim();
        String itemCode = code.getText().trim();
        String itemName = name.getText().trim();
        String make = makeText.getText().trim();
        String model = modelText.getText().trim();
        String qnty = quantityText.getText().trim();
        String cost = price.getText().trim();
        String total = totalText.getText().trim();

        if (invoiceNo.isEmpty() && itemCode.isEmpty() && itemName.isEmpty() && make.isEmpty() && model.isEmpty() && qnty.isEmpty()
                && cost.isEmpty() && total.isEmpty()) {
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (invoiceNo.isEmpty()) {
            AlertMessage.showErrorAlert("Invoice Number must be filled!");

        } else if (itemCode.isEmpty()) {
            AlertMessage.showErrorAlert("Item Code must br filled!");

        } else if (itemName.isEmpty()) {
            AlertMessage.showErrorAlert("Item Name must br filled!");

        } else if (make.isEmpty()) {
            AlertMessage.showErrorAlert("Item Make must br filled!");

        } else if (model.isEmpty()) {
            AlertMessage.showErrorAlert("Item Model must br filled!");

        } else if (qnty.isEmpty()) {
            AlertMessage.showErrorAlert("Quantity must br filled!");

        } else if (cost.isEmpty()) {
            AlertMessage.showErrorAlert("Item Cost must br filled!");

        } else if (total.isEmpty()) {
            AlertMessage.showErrorAlert("Total Cost must br filled!");

        } else {
            Database.deleteReceipt(invoiceNo, itemCode);
            AlertMessage.showSuccessAlert("Receipt successfully deleted!");
            clearTextFields();
            refreshReceipts();
            filterReceipts();
        }
    }

    public static String getDateText() {
        return dateText;
    }

    public static String getTimeText() {
        return timeText;
    }

    public static String getInvoice() {
        return invoice;
    }

    public static String getNameString() {
        return nameString;
    }

    public static String getQuantityString() {
        return quantityString;
    }

    public static String getPriceString() {
        return priceString;
    }

    public static String getTotalString() {
        return totalString;
    }
}

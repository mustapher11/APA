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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    public Button save, clear, exit, update, delete, refresh, productList, report;

    public VBox vbox;
    public TableView<SpareItem> spareTable;

    public TableColumn<SpareItem, String> code, name, make, model, stockPurchased, stockSold, stockAvailable, unitCost, date, supplier, delivery, cashier, action;
    public TextField searchItem;

    public TextField truckNumber, invoice, spare, truckMake, truckModel, stock, price, purchaseDate, spareSupplier;
    public CheckBox selectAll;

    ImageView saveImage, clearImage, exitImage, updateImage, deleteImage, refreshImage, productListImage, receiptImage;
    ObservableList<SpareItem> spareItems;

    ObservableList<String> vehicleNumber;
    String dateText, cashierID, newStock;
    TableRow<SpareItem> tableRow1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            spareItems = Database.fetchSpares();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        vehicleNumber = FXCollections.observableArrayList();

        for (SpareItem spareItem: spareItems){
            vehicleNumber.add(spareItem.getCode());
        }

        TextFields.bindAutoCompletion(truckNumber, vehicleNumber);

        cashierID = LogInController.getIdText();
        dateText = DateClass.generateDate();
        purchaseDate.setText(dateText);

        createImageButtons();
        setSpareTable();
        selectAll();
        checkBoxFont();
        try {
            filterProducts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        fetchProductData();
    }

    public void createImageButtons(){
        saveImage = new ImageView("icons8-save-96.png");
        saveImage.setFitHeight(30.0);
        saveImage.setPreserveRatio(true);

        clearImage = new ImageView("icons8-clear-symbol-96.png");
        clearImage.setFitHeight(30.0);
        clearImage.setPreserveRatio(true);

        exitImage = new ImageView("icons8-logout-96.png");
        exitImage.setFitHeight(30.0);
        exitImage.setPreserveRatio(true);

        updateImage = new ImageView("icons8-update-96.png");
        updateImage.setFitHeight(30.0);
        updateImage.setPreserveRatio(true);

        deleteImage = new ImageView("icons8-delete-96.png");
        deleteImage.setFitHeight(30.0);
        deleteImage.setPreserveRatio(true);

        refreshImage = new ImageView("icons8-refresh-96.png");
        refreshImage.setFitHeight(30.0);
        refreshImage.setPreserveRatio(true);

        productListImage = new ImageView("icons8-pricing-96.png");
        productListImage.setFitHeight(30.0);
        productListImage.setPreserveRatio(true);

        receiptImage = new ImageView("icons8-receipt-96.png");
        receiptImage.setFitHeight(30.0);
        receiptImage.setPreserveRatio(true);

        save.setGraphic(saveImage);
        clear.setGraphic(clearImage);
        exit.setGraphic(exitImage);
        update.setGraphic(updateImage);
        delete.setGraphic(deleteImage);
        refresh.setGraphic(refreshImage);
        productList.setGraphic(productListImage);
        report.setGraphic(receiptImage);
    }

    public void exit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.load();
        LogInController controller = loader.getController();
        controller.logInButton();
        vbox.getScene().getWindow().hide();
    }

    public void setSpareTable() {
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        stockPurchased.setCellValueFactory(new PropertyValueFactory<>("stockPurchased"));
        stockSold.setCellValueFactory(new PropertyValueFactory<>("stockSold"));
        stockAvailable.setCellValueFactory(new PropertyValueFactory<>("stockAvailable"));
        unitCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        cashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        action.setCellValueFactory(new PropertyValueFactory<>("action"));

        spareTable.setItems(spareItems);
        spareTable.getColumns().clear();
        spareTable.getColumns().add(code);
        spareTable.getColumns().add(delivery);
        spareTable.getColumns().add(name);
        spareTable.getColumns().add(make);
        spareTable.getColumns().add(model);
        spareTable.getColumns().add(stockPurchased);
        spareTable.getColumns().add(unitCost);
        spareTable.getColumns().add(cashier);
        spareTable.getColumns().add(stockAvailable);
        spareTable.getColumns().add(stockSold);
        spareTable.getColumns().add(date);
        spareTable.getColumns().add(supplier);
        spareTable.getColumns().add(action);
    }

    public void filterProducts() {
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        stockPurchased.setCellValueFactory(new PropertyValueFactory<>("stockPurchased"));
        stockSold.setCellValueFactory(new PropertyValueFactory<>("stockSold"));
        stockAvailable.setCellValueFactory(new PropertyValueFactory<>("stockAvailable"));
        unitCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        cashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));

        FilteredList<SpareItem> spareItemFilteredList = new FilteredList<>(spareItems, b -> true);
        searchItem.textProperty().addListener((observable, oldValue, newValue) -> spareItemFilteredList.setPredicate(SpareItem -> {
            if (newValue == null || newValue.isEmpty() || newValue.isBlank()){
                return true;
            }
            String searchKeyWord = newValue.toLowerCase();
            if (SpareItem.getCode().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getName().toLowerCase().contains(searchKeyWord)){
                return true;

            } else if (SpareItem.getMake().toLowerCase().contains(searchKeyWord)){
                return true;

            } else if (SpareItem.getModel().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getStockPurchased().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getStockAvailable().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getStockSold().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getPrice().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getDate().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getSupplier().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (SpareItem.getDelivery().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else { return SpareItem.getCashier().toLowerCase().contains(searchKeyWord); }
        }));
        SortedList<SpareItem> sortedList = new SortedList<>(spareItemFilteredList);
        sortedList.comparatorProperty().bind(spareTable.comparatorProperty());
        spareTable.setItems(sortedList);
    }

    public void fetchProductData(){
        spareTable.setRowFactory(tv -> {
            TableRow<SpareItem> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2){
                    truckNumber.setText(tableRow.getItem().getCode());
                    invoice.setText(tableRow.getItem().getName());
                    spare.setText(tableRow.getItem().getMake());
                    truckMake.setText(tableRow.getItem().getModel());
                    truckModel.setText(tableRow.getItem().getStockPurchased());
                    stock.setText(tableRow.getItem().getDelivery());
                    price.setText(tableRow.getItem().getPrice().replaceFirst("Kshs.", ""));
                    purchaseDate.setText(tableRow.getItem().getDate());
                    spareSupplier.setText(tableRow.getItem().getSupplier());
                    tableRow1 = tableRow;
                }
            });
            return tableRow;
        });
    }

    public void refreshProductsTable() throws Exception {
        spareItems = Database.fetchSpares();
        filterProducts();
    }

    public void selectAll(){
        selectAll.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            for (SpareItem spareItem: spareItems) {
                spareItem.getAction().setSelected(selectAll.isSelected());
            }
        });
    }

    public void printReport(){
        for (SpareItem spareItem: spareItems){
            if (spareItem.getAction().isSelected()){
                System.out.println(spareItem.getCode() + " " + spareItem.getName() + " " + spareItem.getStockAvailable());
            }
        }
    }

    public void checkBoxFont(){
        Font font = Font.font("System", FontWeight.BOLD, 14.0);
        selectAll.setFont(font);
    }

    public void clearProductFields(){
        truckNumber.clear();
        invoice.clear();
        spare.clear();
        truckMake.clear();
        truckModel.clear();
        stock.clear();
        price.clear();
        purchaseDate.setText(dateText);
        spareSupplier.clear();
    }

    public void updateButton() throws Exception {
        String plateNumber = truckNumber.getText().trim().toUpperCase();
        String receiptInvoice = invoice.getText().trim().toUpperCase();
        String sparePart = spare.getText().trim().toUpperCase();
        String vehicleMake = truckMake.getText().trim().toUpperCase();
        String vehicleModel = truckModel.getText().trim();
        String spareStock = stock.getText().trim();
        String unitPrice = price.getText().trim().toUpperCase();
        String date_purchased = purchaseDate.getText().trim();
        String item_supplier = spareSupplier.getText().trim().toUpperCase();

        if (plateNumber.isEmpty() && receiptInvoice.isEmpty() && sparePart.isEmpty() && vehicleMake.isEmpty()
                && vehicleModel.isEmpty() && spareStock.isEmpty() && unitPrice.isEmpty() && date_purchased.isEmpty() && item_supplier.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (plateNumber.isEmpty()) {
            AlertMessage.showErrorAlert("Item Code must be filled!");

        } else if (receiptInvoice.isEmpty()) {
            AlertMessage.showErrorAlert("Item Name must be filled!");

        } else if (sparePart.isEmpty()) {
            AlertMessage.showErrorAlert("Item Make must be filled!");

        } else if (vehicleMake.isEmpty()) {
            AlertMessage.showErrorAlert("Item Model must be filled!");

        } else if (vehicleModel.isEmpty()) {
            AlertMessage.showErrorAlert("Stock must be filled!");

        } else if (spareStock.isEmpty()) {
            AlertMessage.showErrorAlert("Price field must be filled!");

        } else if (unitPrice.isEmpty()) {
            AlertMessage.showErrorAlert("Date must be filled!");

        } else if (date_purchased.isEmpty()) {
            AlertMessage.showErrorAlert("Supplier must be filled!");

        } else if (item_supplier.isEmpty()){
            AlertMessage.showErrorAlert("Delivery must be filled!");

        }else {
            if (Database.checkSpareItem(plateNumber)){
                newStock = String.valueOf(Integer.parseInt(stock.getText().trim()) - Integer.parseInt(tableRow1.getItem().getStockSold()));
                String total = String.valueOf(Integer.parseInt(newStock) * Integer.parseInt(tableRow1.getItem().getPrice()));
                int rowsAffected = Database.updateProduct(plateNumber, receiptInvoice, sparePart, vehicleMake, vehicleModel, spareStock, unitPrice, total,  newStock, date_purchased, item_supplier);
                if (rowsAffected > 0){
                    refreshProductsTable();
                    clearProductFields();
                    filterProducts();
                    AlertMessage.showSuccessAlert("Spare item successfully updated!");
                }else {
                    AlertMessage.showErrorAlert("An error has occurred!");
                }
            }else {
                AlertMessage.showErrorAlert("Spare item not found!");
            }
        }
    }

    public void deleteButton() throws Exception {
        if (AlertMessage.deleteConfirmation("Are you sure you want to delete the selected item(s)?")) {
            for (int i = 0; i < spareItems.size(); i++) {
                if (spareItems.get(i).getAction().isSelected()) {
                    String item_code = spareItems.get(i).getCode().trim();
                    if (Database.checkSpareItem(item_code)) {
                        Database.deleteProduct(item_code);
                        if (i == 1){
                            AlertMessage.showSuccessAlert("Spare item(s) successfully deleted!");
                        }
                    }else {
                        if (i == 1){
                            AlertMessage.showErrorAlert("Spare item cannot be found!");
                        }
                    }
                }else {
                    AlertMessage.showErrorAlert("Please select an item to be deleted!");
                }
            }
        }
        refreshProductsTable();
        filterProducts();
//
//        String item_code = truckNumber.getText().trim().toUpperCase();
//        String item_name = invoice.getText().trim().toUpperCase();
//        String item_make = spare.getText().trim().toUpperCase();
//        String item_model = truckMake.getText().trim().toUpperCase();
//        String item_stock = truckModel.getText().trim();
//        String unit_price = stock.getText().trim();
//        String date_purchased = purchaseDate.getText().trim();
//        String item_supplier = spareSupplier.getText().trim().toUpperCase();
//        String item_delivery = price.getText().trim().toUpperCase();
//
//        if (item_code.isEmpty() && item_name.isEmpty() && item_make.isEmpty() && item_model.isEmpty()
//                && item_stock.isEmpty() && unit_price.isEmpty() && date_purchased.isEmpty() && item_supplier.isEmpty()
//                && item_delivery.isEmpty()){
//            AlertMessage.showErrorAlert("All fields must be filled!");
//
//        } else if (item_code.isEmpty()) {
//            AlertMessage.showErrorAlert("Item Code must be filled!");
//
//        } else if (item_name.isEmpty()) {
//            AlertMessage.showErrorAlert("Item Name must be filled!");
//
//        } else if (item_make.isEmpty()) {
//            AlertMessage.showErrorAlert("Item Make must be filled!");
//
//        } else if (item_model.isEmpty()) {
//            AlertMessage.showErrorAlert("Item Model must be filled!");
//
//        } else if (item_stock.isEmpty()) {
//            AlertMessage.showErrorAlert("Stock must be filled!");
//
//        } else if (unit_price.isEmpty()) {
//            AlertMessage.showErrorAlert("Price field must be filled!");
//
//        } else if (date_purchased.isEmpty()) {
//            AlertMessage.showErrorAlert("Date must be filled!");
//
//        } else if (item_supplier.isEmpty()) {
//            AlertMessage.showErrorAlert("Supplier must be filled!");
//
//        } else if (item_delivery.isEmpty()){
//            AlertMessage.showErrorAlert("Delivery must be filled!");
//
//        } else {
//            if (Database.checkSpareItem(item_code)){
//                if (AlertMessage.deleteConfirmation("Are you sure you want to delete the selected item(s)?")){
//                    Database.deleteProduct(item_code);
//                    clearProductFields();
//                    refreshProductsTable();
//                    filterProducts();
//                    AlertMessage.showSuccessAlert("Spare item(s) successfully deleted!");
//                }
//            }else {
//                AlertMessage.showErrorAlert("Spare item cannot be found!");
//                clearProductFields();
//            }
//        }
    }

    public void saveButton() throws Exception {
        String plateNumber = truckNumber.getText().trim().toUpperCase();
        String receiptInvoice = invoice.getText().trim().toUpperCase();
        String sparePart = spare.getText().trim().toUpperCase();
        String vehicleMake = truckMake.getText().trim().toUpperCase();
        String vehicleModel = truckModel.getText().trim();
        String spareStock = stock.getText().trim();
        String unitPrice = price.getText().trim().toUpperCase();
        String date_purchased = purchaseDate.getText().trim();
        String item_supplier = spareSupplier.getText().trim().toUpperCase();

        if (plateNumber.isEmpty() && receiptInvoice.isEmpty() && sparePart.isEmpty() && vehicleMake.isEmpty()
                && vehicleModel.isEmpty() && spareStock.isEmpty() && date_purchased.isEmpty() && item_supplier.isEmpty()
                && unitPrice.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (plateNumber.isEmpty()) {
            AlertMessage.showErrorAlert("Item Code must be filled!");

        } else if (receiptInvoice.isEmpty()) {
            AlertMessage.showErrorAlert("Item Name must be filled!");

        } else if (sparePart.isEmpty()) {
            AlertMessage.showErrorAlert("Item Make must be filled!");

        } else if (vehicleMake.isEmpty()) {
            AlertMessage.showErrorAlert("Item Model must be filled!");

        } else if (vehicleModel.isEmpty()) {
            AlertMessage.showErrorAlert("Stock must be filled!");

        } else if (spareStock.isEmpty()) {
            AlertMessage.showErrorAlert("Price field must be filled!");

        } else if (date_purchased.isEmpty()) {
            AlertMessage.showErrorAlert("Date must be filled!");

        } else if (item_supplier.isEmpty()) {
            AlertMessage.showErrorAlert("Supplier must be filled!");

        } else if (unitPrice.isEmpty()){
            AlertMessage.showErrorAlert("Delivery must be filled!");

        } else {
            if (!Database.checkSpareItem(plateNumber)){
                int total = Integer.parseInt(stock.getText().trim()) * Integer.parseInt(price.getText().trim());
                String stringTotal = String.valueOf(total);
                int rowsAffected = Database.insertProduct(plateNumber, receiptInvoice, sparePart, vehicleMake, vehicleModel, spareStock, unitPrice, stringTotal, spareStock, "0", date_purchased, item_supplier, cashierID);
                if (rowsAffected > 0){
                    clearProductFields();
                    refreshProductsTable();
                    filterProducts();
                    AlertMessage.showSuccessAlert("Spare item added successfully!");
                }else {
                    AlertMessage.showErrorAlert("An error has occurred!");
                }
            }else {
                AlertMessage.showErrorAlert("Spare item already exists!");
                clearProductFields();
            }
        }
    }
}

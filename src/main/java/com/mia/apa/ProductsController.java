package com.mia.apa;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    public Button save, clear, exit, update, delete, refresh, productList;

    public VBox vbox;
    public TableView<SpareItem> spareTable;

    public TableColumn<SpareItem, String> code, name, make, model, stockPurchased, stockSold, stockAvailable, unitCost, date, supplier, delivery, cashier;
    public TextField searchItem;

    public TextField itemCode, itemName, itemMake, itemModel, itemStock, unitPrice, datePurchased, itemSupplier, itemDelivery;
    ImageView saveImage, clearImage, exitImage, updateImage, deleteImage, refreshImage, productListImage;
    ObservableList<SpareItem> spareItems;
    String dateText, cashierID, newStock;
    TableRow<SpareItem> tableRow1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            spareItems = Database.fetchSpares();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cashierID = LogInController.getIdText();
        dateText = DateClass.generateDate();
        datePurchased.setText(dateText);

        createImageButtons();
        setSpareTable();
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

        save.setGraphic(saveImage);
        clear.setGraphic(clearImage);
        exit.setGraphic(exitImage);
        update.setGraphic(updateImage);
        delete.setGraphic(deleteImage);
        refresh.setGraphic(refreshImage);
        productList.setGraphic(productListImage);
    }

    public void exit(){
        CreateScene.changeSceneModal("dashboard.fxml", "Dashboard", false);
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

        spareTable.setItems(spareItems);
        spareTable.getColumns().clear();
        spareTable.getColumns().add(code);
        spareTable.getColumns().add(name);
        spareTable.getColumns().add(make);
        spareTable.getColumns().add(model);
        spareTable.getColumns().add(stockPurchased);
        spareTable.getColumns().add(stockAvailable);
        spareTable.getColumns().add(stockSold);
        spareTable.getColumns().add(unitCost);
        spareTable.getColumns().add(date);
        spareTable.getColumns().add(supplier);
        spareTable.getColumns().add(delivery);
        spareTable.getColumns().add(cashier);
    }

    public void filterProducts() throws Exception {
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

        spareItems.addAll(Database.fetchSpares());
        spareTable.setItems(spareItems);

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
                    itemCode.setText(tableRow.getItem().getCode());
                    itemName.setText(tableRow.getItem().getName());
                    itemMake.setText(tableRow.getItem().getMake());
                    itemModel.setText(tableRow.getItem().getModel());
                    itemStock.setText(tableRow.getItem().getStockPurchased());
                    unitPrice.setText(tableRow.getItem().getPrice().replaceFirst("Kshs.", ""));
                    datePurchased.setText(tableRow.getItem().getDate());
                    itemSupplier.setText(tableRow.getItem().getSupplier());
                    itemDelivery.setText(tableRow.getItem().getDelivery());
                    tableRow1 = tableRow;
                }
            });
            return tableRow;
        });
    }

    public void refreshProductsTable() throws Exception {
        spareItems = Database.fetchSpares();
        setSpareTable();
        filterProducts();
    }

    public void clearProductFields(){
        itemCode.clear();
        itemName.clear();
        itemMake.clear();
        itemModel.clear();
        itemStock.clear();
        unitPrice.clear();
        datePurchased.clear();
        itemSupplier.clear();
        itemDelivery.clear();
    }

    public void updateButton() throws Exception {
        String item_code = itemCode.getText().trim().toUpperCase();
        String item_name = itemName.getText().trim().toUpperCase();
        String item_make = itemMake.getText().trim().toUpperCase();
        String item_model = itemModel.getText().trim().toUpperCase();
        String item_stock = itemStock.getText().trim();
        String unit_price = unitPrice.getText().trim();
        String date_purchased = datePurchased.getText().trim();
        String item_supplier = itemSupplier.getText().trim().toUpperCase();
        String item_delivery = itemDelivery.getText().trim().toUpperCase();

        if (item_code.isEmpty() && item_name.isEmpty() && item_make.isEmpty() && item_model.isEmpty()
                && item_stock.isEmpty() && unit_price.isEmpty() && date_purchased.isEmpty() && item_supplier.isEmpty()
         && item_delivery.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (item_code.isEmpty()) {
            AlertMessage.showErrorAlert("Item Code must be filled!");

        } else if (item_name.isEmpty()) {
            AlertMessage.showErrorAlert("Item Name must be filled!");

        } else if (item_make.isEmpty()) {
            AlertMessage.showErrorAlert("Item Make must be filled!");

        } else if (item_model.isEmpty()) {
            AlertMessage.showErrorAlert("Item Model must be filled!");

        } else if (item_stock.isEmpty()) {
            AlertMessage.showErrorAlert("Stock must be filled!");

        } else if (unit_price.isEmpty()) {
            AlertMessage.showErrorAlert("Price field must be filled!");

        } else if (date_purchased.isEmpty()) {
            AlertMessage.showErrorAlert("Date must be filled!");

        } else if (item_supplier.isEmpty()) {
            AlertMessage.showErrorAlert("Supplier must be filled!");

        } else if (item_delivery.isEmpty()){
            AlertMessage.showErrorAlert("Delivery must be filled!");

        }else {
            if (Database.checkSpareItem(item_code)){
                newStock = String.valueOf(Integer.parseInt(itemStock.getText().trim()) - Integer.parseInt(tableRow1.getItem().getStockSold()));
                int rowsAffected = Database.updateProduct(item_code, item_name, item_make, item_model, newStock, item_stock, unit_price, date_purchased, item_supplier, item_delivery);
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
        String item_code = itemCode.getText().trim().toUpperCase();
        String item_name = itemName.getText().trim().toUpperCase();
        String item_make = itemMake.getText().trim().toUpperCase();
        String item_model = itemModel.getText().trim().toUpperCase();
        String item_stock = itemStock.getText().trim();
        String unit_price = unitPrice.getText().trim();
        String date_purchased = datePurchased.getText().trim();
        String item_supplier = itemSupplier.getText().trim().toUpperCase();
        String item_delivery = itemDelivery.getText().trim().toUpperCase();

        if (item_code.isEmpty() && item_name.isEmpty() && item_make.isEmpty() && item_model.isEmpty()
                && item_stock.isEmpty() && unit_price.isEmpty() && date_purchased.isEmpty() && item_supplier.isEmpty()
                && item_delivery.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (item_code.isEmpty()) {
            AlertMessage.showErrorAlert("Item Code must be filled!");

        } else if (item_name.isEmpty()) {
            AlertMessage.showErrorAlert("Item Name must be filled!");

        } else if (item_make.isEmpty()) {
            AlertMessage.showErrorAlert("Item Make must be filled!");

        } else if (item_model.isEmpty()) {
            AlertMessage.showErrorAlert("Item Model must be filled!");

        } else if (item_stock.isEmpty()) {
            AlertMessage.showErrorAlert("Stock must be filled!");

        } else if (unit_price.isEmpty()) {
            AlertMessage.showErrorAlert("Price field must be filled!");

        } else if (date_purchased.isEmpty()) {
            AlertMessage.showErrorAlert("Date must be filled!");

        } else if (item_supplier.isEmpty()) {
            AlertMessage.showErrorAlert("Supplier must be filled!");

        } else if (item_delivery.isEmpty()){
            AlertMessage.showErrorAlert("Delivery must be filled!");

        } else {
            if (Database.checkSpareItem(item_code)){
                Database.deleteProduct(item_code);
                clearProductFields();
                refreshProductsTable();
                filterProducts();
                AlertMessage.showSuccessAlert("Spare item successfully deleted!");
            }else {
                AlertMessage.showErrorAlert("Spare item cannot be found!");
                clearProductFields();
            }
        }
    }

    public void saveButton() throws Exception {
        String item_code = itemCode.getText().trim().toUpperCase();
        String item_name = itemName.getText().trim().toUpperCase();
        String item_make = itemMake.getText().trim().toUpperCase();
        String item_model = itemModel.getText().trim().toUpperCase();
        String item_stock = itemStock.getText().trim();
        String unit_price = unitPrice.getText().trim();
        String date_purchased = datePurchased.getText().trim();
        String item_supplier = itemSupplier.getText().trim().toUpperCase();
        String item_delivery = itemDelivery.getText().trim().toUpperCase();

        if (item_code.isEmpty() && item_name.isEmpty() && item_make.isEmpty() && item_model.isEmpty()
                && item_stock.isEmpty() && unit_price.isEmpty() && date_purchased.isEmpty() && item_supplier.isEmpty()
                && item_delivery.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        } else if (item_code.isEmpty()) {
            AlertMessage.showErrorAlert("Item Code must be filled!");

        } else if (item_name.isEmpty()) {
            AlertMessage.showErrorAlert("Item Name must be filled!");

        } else if (item_make.isEmpty()) {
            AlertMessage.showErrorAlert("Item Make must be filled!");

        } else if (item_model.isEmpty()) {
            AlertMessage.showErrorAlert("Item Model must be filled!");

        } else if (item_stock.isEmpty()) {
            AlertMessage.showErrorAlert("Stock must be filled!");

        } else if (unit_price.isEmpty()) {
            AlertMessage.showErrorAlert("Price field must be filled!");

        } else if (date_purchased.isEmpty()) {
            AlertMessage.showErrorAlert("Date must be filled!");

        } else if (item_supplier.isEmpty()) {
            AlertMessage.showErrorAlert("Supplier must be filled!");

        } else if (item_delivery.isEmpty()){
            AlertMessage.showErrorAlert("Delivery must be filled!");

        } else {
            if (!Database.checkSpareItem(item_code)){
                int rowsAffected = Database.insertProduct(item_code, item_name, item_make, item_model, item_stock, item_stock, "0", unit_price, date_purchased, item_supplier, item_delivery, cashierID);
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

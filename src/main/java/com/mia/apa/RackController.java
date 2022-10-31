package com.mia.apa;

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

public class RackController implements Initializable {
    public  VBox vbox;

    public Button save, update, clear, delete, exit, refresh, stockList;

    public TextField code, name, make, model, stock, cost, rackNumber, search;
    public TableView<Rack> rackTable;
    public TableColumn<Rack, String> codeColumn, nameColumn, makeColumn, modelColumn, stockColumn, costColumn, numberColumn, cashierColumn;
    ImageView saveImage, updateImage, clearImage, exitImage, deleteImage, refreshImage, stockListImage;
    String cashierID;
    ObservableList<Rack> rackObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            rackObservableList = Database.fetchRackDetails();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cashierID = LogInController.getIdText();
        createImageButtons();
        setRackTable();
        try {
            filterRack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        fetchRackData();
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

        stockListImage = new ImageView("icons8-sell-stock-96.png");
        stockListImage.setFitHeight(30.0);
        stockListImage.setPreserveRatio(true);

        save.setGraphic(saveImage);
        clear.setGraphic(clearImage);
        update.setGraphic(updateImage);
        delete.setGraphic(deleteImage);
        exit.setGraphic(exitImage);
        refresh.setGraphic(refreshImage);
        stockList.setGraphic(stockListImage);
    }
    public void exitRack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.load();
        LogInController controller = loader.getController();
        controller.logInButton();
        vbox.getScene().getWindow().hide();
    }

    public void saveButton() throws Exception {
        String item_code = code.getText().trim().toUpperCase();
        String item_name = name.getText().trim().toUpperCase();
        String item_make = make.getText().trim().toUpperCase();
        String item_model = model.getText().trim().toUpperCase();
        String item_stock = stock.getText().trim();
        String unit_price = cost.getText().trim();
        String rack_number = rackNumber.getText().trim();

        if (item_code.isEmpty() && item_name.isEmpty() && item_make.isEmpty() && item_model.isEmpty()
                && item_stock.isEmpty() && unit_price.isEmpty()){
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

        } else if (rack_number.isEmpty()) {
            AlertMessage.showErrorAlert("Rack Number must be filled!");

        } else {
            boolean found = Database.checkSpareItemInRack(item_code);
            if (!found){
                int rowsAffected = Database.insertRack(item_code, item_name, item_make, item_model, item_stock, unit_price, rack_number, cashierID);
                if (rowsAffected > 0){
                    clearButton();
                    refreshRack();
                    filterRack();
                    AlertMessage.showSuccessAlert("Spare item successfully added to rack!");
                } else {
                    AlertMessage.showErrorAlert("An error has occurred!");
                }
            } else {
                AlertMessage.showErrorAlert("Spare item already exists in rack!");
            }
        }
    }

    public void clearButton(){
        code.clear();
        name.clear();
        make.clear();
        model.clear();
        stock.clear();
        cost.clear();
        rackNumber.clear();
    }

    public void setRackTable(){
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("rackNUmber"));
        cashierColumn.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        
        rackTable.setItems(rackObservableList);
        rackTable.getColumns().clear();
        rackTable.getColumns().add(codeColumn);
        rackTable.getColumns().add(nameColumn);
        rackTable.getColumns().add(makeColumn);
        rackTable.getColumns().add(modelColumn);
        rackTable.getColumns().add(stockColumn);
        rackTable.getColumns().add(costColumn);
        rackTable.getColumns().add(numberColumn);
        rackTable.getColumns().add(cashierColumn);
    }

    public void refreshRack() throws Exception {
        rackObservableList = Database.fetchRackDetails();
        filterRack();
    }

    public void filterRack() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("rackNUmber"));
        cashierColumn.setCellValueFactory(new PropertyValueFactory<>("cashier"));

        FilteredList<Rack> rackFilteredList = new FilteredList<>(rackObservableList, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> rackFilteredList.setPredicate(Rack -> {
            if (newValue == null || newValue.isEmpty() || newValue.isBlank()){
                return true;
            }
            String searchKeyWord = newValue.toLowerCase();
            if (Rack.getItemCode().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Rack.getItemName().toLowerCase().contains(searchKeyWord)){
                return true;

            } else if (Rack.getMake().toLowerCase().contains(searchKeyWord)){
                return true;

            } else if (Rack.getModel().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Rack.getStock().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Rack.getCost().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Rack.getRackNUmber().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else { return Rack.getCashier().toLowerCase().contains(searchKeyWord); }
        }));
        SortedList<Rack> sortedList = new SortedList<>(rackFilteredList);
        sortedList.comparatorProperty().bind(rackTable.comparatorProperty());
        rackTable.setItems(sortedList);
    }

    public void deleteButton() throws Exception {
        String item_code = code.getText().trim().toUpperCase();
        String item_name = name.getText().trim().toUpperCase();
        String item_make = make.getText().trim().toUpperCase();
        String item_model = model.getText().trim().toUpperCase();
        String item_stock = stock.getText().trim();
        String unit_price = cost.getText().trim();
        String rack_number = rackNumber.getText().trim();

        if (item_code.isEmpty() && item_name.isEmpty() && item_make.isEmpty() && item_model.isEmpty()
                && item_stock.isEmpty() && unit_price.isEmpty()){
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

        } else if (rack_number.isEmpty()) {
            AlertMessage.showErrorAlert("Rack Number must be filled!");

        } else {
            boolean found = Database.checkSpareItemInRack(item_code);
            if (found){
                Database.deleteRack(rack_number);
                clearButton();
                refreshRack();
                filterRack();
                AlertMessage.showSuccessAlert("Rack successfully deleted!");
            }else {
                AlertMessage.showErrorAlert("Spare item not found!");
            }
        }
    }

    public void fetchRackData(){
        rackTable.setRowFactory(tv -> {
            TableRow<Rack> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2){
                    code.setText(tableRow.getItem().getItemCode());
                    name.setText(tableRow.getItem().getItemName());
                    make.setText(tableRow.getItem().getMake());
                    model.setText(tableRow.getItem().getModel());
                    stock.setText(tableRow.getItem().getStock());
                    cost.setText(tableRow.getItem().getCost().replaceFirst("Kshs.", ""));
                    rackNumber.setText(tableRow.getItem().getRackNUmber());
                }
            });
            return tableRow;
        });
    }

    public void updateButton() throws Exception {
        String item_code = code.getText().trim().toUpperCase();
        String item_name = name.getText().trim().toUpperCase();
        String item_make = make.getText().trim().toUpperCase();
        String item_model = model.getText().trim().toUpperCase();
        String item_stock = stock.getText().trim();
        String unit_price = cost.getText().trim();
        String rack_number = rackNumber.getText().trim();

        if (item_code.isEmpty() && item_name.isEmpty() && item_make.isEmpty() && item_model.isEmpty()
                && item_stock.isEmpty() && unit_price.isEmpty()){
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

        } else if (rack_number.isEmpty()) {
            AlertMessage.showErrorAlert("Rack Number must be filled!");

        } else {
            boolean found = Database.checkSpareItemInRack(item_code);
            if (found){
                int rowsAffected = Database.updateRack(item_code, item_name, item_make,item_model, item_stock, unit_price, rack_number, cashierID);
                if (rowsAffected > 0){
                    clearButton();
                    refreshRack();
                    filterRack();
                    AlertMessage.showSuccessAlert("Rack successfully updated!");
                }else {
                    AlertMessage.showErrorAlert("An error has occurred!");
                }
            }else {
                AlertMessage.showErrorAlert("Spare item not found!");
            }
        }
    }
}

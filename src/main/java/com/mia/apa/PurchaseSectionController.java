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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseSectionController implements Initializable {

    public Button add, clear, clearOrder, generate, purchase, exit, total;

    public VBox vbox;

    public Button refresh;
    public TableView<Spare> spareTable;

    public TextField itemCode;

    public TextField itemName, ref1, ref2, quantity, unitPrice, totalPrice;
    public TableColumn<Spare, String>  item_id, item_name, make, model,stockPurchased, stockAvailable, stockSold, unit_price, supplier, action;

    public TextField search;
    public CheckBox selectAll;
    ImageView addImage, clearImage, clearOrderImage, generateImage, purchaseImage, exitImage, totalImage, refreshImage;
    ObservableList<Spare> spares;
    static ObservableList<Item> queue;
    static String spareCode, spareName, spareRef1, spareRef2, spareQuantity, spareCost, spareTotal;
    static int newStockAvailable = 0, newStockSold = 0;
    static ObservableList<Stock> stockObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            spares = Database.fetchItems();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        totalPrice.setEditable(false);
        queue = FXCollections.observableArrayList();
        stockObservableList = FXCollections.observableArrayList();
        createControlButtons();
        setTable();
        fetchTableData();
        checkBoxFont();
        try {
            filterSpare();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createControlButtons(){
        addImage = new ImageView("icons8-plus-96.png");
        addImage.setFitHeight(30.0);
        addImage.setPreserveRatio(true);

        clearImage = new ImageView("icons8-delete-96.png");
        clearImage.setFitHeight(30.0);
        clearImage.setPreserveRatio(true);

        clearOrderImage = new ImageView("icons8-clear-symbol-96.png");
        clearOrderImage.setFitHeight(30.0);
        clearOrderImage.setPreserveRatio(true);

        generateImage = new ImageView("icons8-general-ledger-96.png");
        generateImage.setFitHeight(30.0);
        generateImage.setPreserveRatio(true);

        purchaseImage = new ImageView("icons8-done-96.png");
        purchaseImage.setFitHeight(30.0);
        purchaseImage.setPreserveRatio(true);

        exitImage = new ImageView("icons8-logout-96.png");
        exitImage.setFitHeight(30.0);
        exitImage.setPreserveRatio(true);

        totalImage = new ImageView("icons8-plus-math-96.png");
        totalImage.setFitHeight(30.0);
        totalImage.setPreserveRatio(true);

        refreshImage = new ImageView("icons8-refresh-96.png");
        refreshImage.setFitHeight(30.0);
        refreshImage.setPreserveRatio(true);

        add.setGraphic(addImage);
        clear.setGraphic(clearImage);
        clearOrder.setGraphic(clearOrderImage);
        generate.setGraphic(generateImage);
        purchase.setGraphic(purchaseImage);
        exit.setGraphic(exitImage);
        total.setGraphic(totalImage);
        refresh.setGraphic(refreshImage);

//        generate.setDisable(true);
    }

    public void checkBoxFont(){
        Font font = Font.font("System", FontWeight.BOLD, 14.0);
        selectAll.setFont(font);
    }

    public void exitButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.load();
        LogInController controller = loader.getController();
        controller.logInButton();
        vbox.getScene().getWindow().hide();
    }

    public void generate(){
        CreateScene.changeSceneModal("print.fxml","Generate", false);
    }

    public void print(){
        CreateScene.changeSceneModal("print1.fxml","Print", false);
    }

    public void calculateTotalPrice(){
        String unitCost = unitPrice.getText().trim();
        String qnty = quantity.getText().trim();

        if (!unitCost.isEmpty() && !qnty.isEmpty()){
            spareQuantity = qnty;
            int totalCost = Integer.parseInt(unitCost) * Integer.parseInt(qnty);
            totalPrice.setText(totalCost + "");
            spareTotal = String.valueOf(totalCost);

        } else if (unitCost.isEmpty() && qnty.isEmpty()) {
            AlertMessage.showErrorAlert("Fields Unit Price and Quantity cannot be empty!");

        } else if (unitCost.isEmpty()) {
            AlertMessage.showErrorAlert("Field Unit Price cannot be empty!");

        } else {
            AlertMessage.showErrorAlert("Field Quantity cannot be empty!");
        }
    }

    public void clearFields(){
        itemCode.clear();
        itemName.clear();
        ref1.clear();
        ref2.clear();
        quantity.clear();
        unitPrice.clear();
        totalPrice.clear();
    }

    public void setTable(){
        item_id.setCellValueFactory(new PropertyValueFactory<>("code"));
        item_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        stockPurchased.setCellValueFactory(new PropertyValueFactory<>("stock_purchased"));
        stockAvailable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockSold.setCellValueFactory(new PropertyValueFactory<>("stock_sold"));
        unit_price.setCellValueFactory(new PropertyValueFactory<>("cost"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        action.setCellValueFactory(new PropertyValueFactory<>("action"));

        spareTable.setItems(spares);
        spareTable.getColumns().clear();
        spareTable.getColumns().add(item_id);
        spareTable.getColumns().add(item_name);
        spareTable.getColumns().add(make);
        spareTable.getColumns().add(model);
        spareTable.getColumns().add(stockPurchased);
        spareTable.getColumns().add(stockAvailable);
        spareTable.getColumns().add(stockSold);
        spareTable.getColumns().add(unit_price);
        spareTable.getColumns().add(supplier);
        spareTable.getColumns().add(action);
    }

    public void refreshTable() throws Exception {
        spares = Database.fetchItems();
        filterSpare();
    }

    public void filterSpare() {
        item_id.setCellValueFactory(new PropertyValueFactory<>("code"));
        item_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        stockPurchased.setCellValueFactory(new PropertyValueFactory<>("stock_purchased"));
        stockAvailable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockSold.setCellValueFactory(new PropertyValueFactory<>("stock_sold"));
        unit_price.setCellValueFactory(new PropertyValueFactory<>("cost"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        FilteredList<Spare> spareFilteredList = new FilteredList<>(spares, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> spareFilteredList.setPredicate(Spare -> {
            if (newValue == null || newValue.isEmpty() || newValue.isBlank()){
                return true;
            }
            String searchKeyWord = newValue.toLowerCase();
            if (Spare.getName().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Spare.getCode().toLowerCase().contains(searchKeyWord)){
                return true;

            } else if (Spare.getMake().toLowerCase().contains(searchKeyWord)){
                return true;

            } else if (Spare.getModel().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Spare.getStock().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Spare.getStock_sold().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else if (Spare.getCost().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else { return Spare.getSupplier().toLowerCase().contains(searchKeyWord); }
        }));
        SortedList<Spare> sortedList = new SortedList<>(spareFilteredList);
        sortedList.comparatorProperty().bind(spareTable.comparatorProperty());
        spareTable.setItems(sortedList);
    }

    public void fetchTableData(){
        spareTable.setRowFactory(tv -> {
            TableRow<Spare> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2){
                    clearFields();
                    itemCode.setText(tableRow.getItem().getCode());
                    itemName.setText(tableRow.getItem().getName());
                    ref1.setText(tableRow.getItem().getMake());
                    ref2.setText(tableRow.getItem().getModel());
                    quantity.setText("1");
                    unitPrice.setText(tableRow.getItem().getCost().replaceFirst("Kshs.", ""));
                    spareCode = itemCode.getText().trim();
                    spareName = itemName.getText().trim();
                    spareRef1 = ref1.getText().trim();
                    spareRef2 = ref2.getText().trim();
                    spareCost = unitPrice.getText().trim();
                    newStockAvailable = Integer.parseInt(tableRow.getItem().getStock());
                    newStockSold = Integer.parseInt(tableRow.getItem().getStock_sold());
                }
            });
            return tableRow;
        });
    }

    public void addToQueue(){
        if (spareCode == null || spareName == null || spareRef1 == null || spareRef2 == null
                || spareQuantity == null || spareCost == null || spareTotal == null){
            AlertMessage.showErrorAlert("Please ensure that all fields are  filled!");

        }else {
            queue.add(new Item(spareCode,spareName,spareRef1,spareRef2, spareQuantity,spareCost, spareTotal));
            clearFields();
            AlertMessage.showSuccessAlert("Product successfully added to queue!");
            addStockDetails();
        }

    }

    public void emptyQueue(){
        spareName = null;
        spareCode = null;
        spareRef1 = null;
        spareRef2 = null;
        spareQuantity = null;
        spareCost = null;
        spareTotal = null;

        if (!queue.isEmpty()){
            queue.clear();
        }
    }

    public static void addStockDetails(){
        stockObservableList.add(new Stock(newStockAvailable + "", newStockSold + "", spareQuantity, spareCode));
    }

    public static String getCode(){
        return spareCode;
    }

    public static String getSpareName() {
        return spareName;
    }

    public static String getSpareRef1() {
        return spareRef1;
    }

    public static String getSpareRef2() {
        return spareRef2;
    }

    public static String getSpareQuantity() {
        return spareQuantity;
    }

    public static String getSpareCost() {
        return spareCost;
    }

    public static String getSpareTotal() {
        return spareTotal;
    }

    public static ObservableList<Item> getQueue() {
        return queue;
    }

    public static ObservableList<Stock> getStockObservableList(){
        return stockObservableList;
    }

    public static int getNewStockAvailable(){
        int calculation = 0;
        if (getSpareQuantity() != null){
            calculation =  newStockAvailable - Integer.parseInt(getSpareQuantity());
        }
        return calculation;
    }

    public static int getNewStockSold() {
        int calculation = 0;
        if (getSpareQuantity() != null && !getSpareQuantity().isEmpty()){
            calculation = newStockSold + Integer.parseInt(getSpareQuantity());
        }
        return calculation;
    }

}

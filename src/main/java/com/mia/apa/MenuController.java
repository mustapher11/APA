package com.mia.apa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public TableView<Food> menuTable;

    public TextField search;
    ObservableList<Food> foods;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createMenu();
        filterMenu();
    }

    public void createMenu(){
        foods = FXCollections.observableArrayList();
        foods.add(new Food("Chips", "Kshs.150.00", "Snack"));
        foods.add(new Food("Pilau", "Kshs.300.00", "Lunch"));
        foods.add(new Food("Tea", "Kshs.50.00", "Breakfast"));
        foods.add(new Food("Chicken", "Kshs.250.00", "Lunch"));
        foods.add(new Food("Chapati", "Kshs.30.00", "Lunch"));
        foods.add(new Food("Boiled eggs", "Kshs.25.00", "Breakfast"));

        TableColumn<Food, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200.0);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Food, String> costColumn = new TableColumn<>("Cost");
        costColumn.setMinWidth(150.0);
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<Food, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setMinWidth(200.0);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        menuTable.setItems(foods);
        menuTable.getColumns().add(nameColumn);
        menuTable.getColumns().add(costColumn);
        menuTable.getColumns().add(categoryColumn);
    }

    public void filterMenu(){
        FilteredList<Food> foodFilteredList = new FilteredList<>(foods, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> foodFilteredList.setPredicate(Food -> {
            if (newValue == null || newValue.isEmpty() || newValue.isBlank()){
                return true;
            }

            String searchKeyWord = newValue.toLowerCase();
            if (Food.getName().toLowerCase().contains(searchKeyWord)){
                return true;

            } else if (Food.getCost().toLowerCase().contains(searchKeyWord)) {
                return true;

            } else return Food.getCategory().toLowerCase().contains(searchKeyWord);
        }));

        SortedList<Food> sortedList = new SortedList<>(foodFilteredList);
        sortedList.comparatorProperty().bind(menuTable.comparatorProperty());
        menuTable.setItems(sortedList);
    }
}
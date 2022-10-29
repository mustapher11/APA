package com.mia.apa;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTableController implements Initializable {
    public TextField table;

    public Button add, delete;
    String id, tableText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableText = table.getText().trim();
        id = ProductsSectionController.getId();
        System.out.println(id);
    }
    public void addTable() throws Exception {
        if (tableText.isEmpty()){
            AlertMessage.showErrorAlert("Please insert a table name!");

        }else if (InputValidation.validateTextInput(tableText)) {
            AlertMessage.showErrorAlert("Wrong input! Please enter a valid table name of length 3-15.");

        } else if (Database.checkTable(tableText)) {
            table.clear();
            AlertMessage.showErrorAlert("Table already exists!");

        } else {
            int affectedRows = Database.addTable(tableText, id);
            table.clear();
            if (affectedRows > 0){
                AlertMessage.showSuccessAlert("Table added successfully!");
            }else {
                AlertMessage.showErrorAlert("An error has occurred! Please try again.");
            }
        }
    }

    public void deleteButton() throws Exception{
        if (!tableText.isEmpty()){
            if (Database.checkTable(tableText)){
                int affectedRows = Database.deleteTable(tableText);
                table.clear();
                if (affectedRows > 0){
                    AlertMessage.showSuccessAlert("Table deleted successfully!");
                }
            }else {
                AlertMessage.showErrorAlert("Table does not exist!");
            }
        }else {
            AlertMessage.showErrorAlert("Please insert a table name to be deleted!");
        }
    }
}

package com.mia.apa;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public Button customer, purchase, purchaseOrder, receipts, racking, purchaseReturn, warehouse, suppliers, delivery, users, creditCustomers;
    public Button sms;
    public Button settings;
    public Button logOut;

    public VBox vbox;
    ImageView customerImage, purchaseImage, purchaseOrderImage, receiptImage, rackingImage, purchaseReturnImage, warehouseImage, supplierImage;
    ImageView deliveryImage, userImage, creditCustomerImage, smsImage, settingImage, logoutImage;
    Parent root;
    Stage stage;
    Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createDashBoard();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDashBoard() throws URISyntaxException {
        customerImage = new ImageView(String.valueOf(getClass().getClassLoader().getResource("icons8-product-96.png")));
        customerImage.setFitHeight(30.0);
        customerImage.setPreserveRatio(true);

        purchaseImage = new ImageView(String.valueOf(getClass().getClassLoader().getResource("icons8-add-shopping-cart-96.png")));
        purchaseImage.setFitHeight(30.0);
        purchaseImage.setPreserveRatio(true);

        purchaseOrderImage = new ImageView(String.valueOf(getClass().getClassLoader().getResource("icons8-purchase-order-96.png")));
        purchaseOrderImage.setFitHeight(30.0);
        purchaseOrderImage.setPreserveRatio(true);

        receiptImage = new ImageView(String.valueOf(getClass().getClassLoader().getResource("icons8-receipt-96.png")));
        receiptImage.setFitHeight(30.0);
        receiptImage.setPreserveRatio(true);

        rackingImage = new ImageView(String.valueOf(getClass().getClassLoader().getResource("icons8-rack-96.png")));
        rackingImage.setFitHeight(30.0);
        rackingImage.setPreserveRatio(true);

        purchaseReturnImage = new ImageView(String.valueOf(getClass().getClassLoader().getResource("icons8-transaction-96.png")));
        purchaseReturnImage.setFitHeight(30.0);
        purchaseReturnImage.setPreserveRatio(true);

        warehouseImage = new ImageView("icons8-online-store-96.png");
        warehouseImage.setFitHeight(30.0);
        warehouseImage.setPreserveRatio(true);

        supplierImage = new ImageView("icons8-supplier-96.png");
        supplierImage.setFitHeight(30.0);
        supplierImage.setPreserveRatio(true);

        deliveryImage = new ImageView("icons8-in-transit-48.png");
        customerImage.setFitHeight(10.0);
        customerImage.setPreserveRatio(true);

        userImage = new ImageView("icons8-account-96.png");
        userImage.setFitHeight(30.0);
        userImage.setPreserveRatio(true);

        creditCustomerImage = new ImageView("icons8-user-male-48.png");
        customerImage.setFitHeight(30.0);
        customerImage.setPreserveRatio(true);

        smsImage = new ImageView("icons8-sms-96.png");
        smsImage.setFitHeight(30.0);
        smsImage.setPreserveRatio(true);

        settingImage = new ImageView("icons8-settings-144.png");
        settingImage.setFitHeight(30.0);
        settingImage.setPreserveRatio(true);

        logoutImage = new ImageView("icons8-logout-96.png");
        logoutImage.setFitHeight(30.0);
        logoutImage.setPreserveRatio(true);

        customer.setGraphic(customerImage);
        purchase.setGraphic(purchaseImage);
        purchaseOrder.setGraphic(purchaseOrderImage);
        receipts.setGraphic(receiptImage);
        racking.setGraphic(rackingImage);
        purchaseReturn.setGraphic(purchaseReturnImage);
        warehouse.setGraphic(warehouseImage);
        suppliers.setGraphic(supplierImage);
        delivery.setGraphic(deliveryImage);
        users.setGraphic(userImage);
        creditCustomers.setGraphic(creditCustomerImage);
        sms.setGraphic(smsImage);
        settings.setGraphic(settingImage);
        logOut.setGraphic(logoutImage);
    }

    public void purchaseButton() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("purchase-section.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("Purchase Section");
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            e.consume();
            if (AlertMessage.exitSection("Do you want to exit the system?")) {
                exitSections();
            }
        });
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        vbox.getScene().getWindow().hide();
    }

    public void logoutButton() {
        CreateScene.changeSceneModal("login.fxml", "Log In", false);
        vbox.getScene().getWindow().hide();
    }

    public void productsSection() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("products.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("Products Section");
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            e.consume();
            if (AlertMessage.exitSection("Do you want to exit the system?")) {
                exitSections();
            }
        });
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        vbox.getScene().getWindow().hide();
    }

    public void settings() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("settings.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void delivery() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("delivery.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setTitle("Delivery");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void supplier() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("supplier.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("Suppliers");
        stage.setScene(scene);
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setOnCloseRequest(e -> {
            e.consume();
            if (AlertMessage.exitSection("Do you want to exit the system?")) {
                exitSections();
            }
        });
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        vbox.getScene().getWindow().hide();
    }

    public void rack() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rack.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("Rack");
        stage.setScene(scene);
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setOnCloseRequest(e -> {
            e.consume();
            if (AlertMessage.exitSection("Do you want to exit the system?")) {
                exitSections();
            }
        });
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        vbox.getScene().getWindow().hide();
    }

    public void receipt() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("receipt.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("Receipt Section");
        stage.setScene(scene);
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setOnCloseRequest(e -> {
            e.consume();
            if (AlertMessage.exitSection("Do you want to exit the system?")) {
                exitSections();
            }
        });
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        vbox.getScene().getWindow().hide();
        vbox.getScene().getWindow().hide();
    }

    public void exitSections(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        LogInController controller = loader.getController();
        controller.logInButton();
        vbox.getScene().getWindow().hide();
        stage.close();
    }
}

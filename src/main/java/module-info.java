module com.mia.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires mysql.connector.java;

    opens com.mia.apa to javafx.fxml;
    exports com.mia.apa;
}
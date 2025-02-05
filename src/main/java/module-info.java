module lk.ijse.gdse72.sisiralearners {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.gdse72.sisiralearners to javafx.fxml;
    exports lk.ijse.gdse72.sisiralearners;
    exports lk.ijse.gdse72.sisiralearners.controller;
    opens lk.ijse.gdse72.sisiralearners.controller to javafx.fxml;
}
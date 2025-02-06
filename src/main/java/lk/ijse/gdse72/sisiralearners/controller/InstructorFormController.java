package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class InstructorFormController {

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colVehicleClass;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private TableView<?> tblInstructor;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtInstructorEmail;

    @FXML
    private JFXTextField txtInstructorId;

    @FXML
    private JFXTextField txtInstructorName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(MouseEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {

    }

}

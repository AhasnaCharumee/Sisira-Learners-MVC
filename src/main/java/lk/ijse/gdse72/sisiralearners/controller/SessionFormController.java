package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SessionFormController {

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<?> cmbDay;

    @FXML
    private JFXComboBox<?> cmbInstructorNames;

    @FXML
    private JFXComboBox<?> cmbVehicleName;

    @FXML
    private TableColumn<?, ?> colDay;

    @FXML
    private TableColumn<?, ?> colEnadTime;

    @FXML
    private TableColumn<?, ?> colInstructor;

    @FXML
    private TableColumn<?, ?> colSessionId;

    @FXML
    private TableColumn<?, ?> colStartTime;

    @FXML
    private TableColumn<?, ?> colVehicle;

    @FXML
    private AnchorPane paneSession;

    @FXML
    private TableView<?> tblSession;

    @FXML
    private JFXTextField txtEndTime;

    @FXML
    private JFXTextField txtSessionId;

    @FXML
    private JFXTextField txtStartTime;

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

    @FXML
    void cmbDayOnAction(ActionEvent event) {

    }

}

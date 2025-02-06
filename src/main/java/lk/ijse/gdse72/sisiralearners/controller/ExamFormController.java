package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ExamFormController {

    @FXML
    private DatePicker DatePickerExamDate;

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<?> cmbResult;

    @FXML
    private TableColumn<?, ?> colExamDate;

    @FXML
    private TableColumn<?, ?> colExamId;

    @FXML
    private TableColumn<?, ?> colExamName;

    @FXML
    private TableColumn<?, ?> colResult;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private AnchorPane paneExam;

    @FXML
    private TableView<?> tblExam;

    @FXML
    private JFXTextField txtExamName;

    @FXML
    private JFXTextField txtExamName1;

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
    void cmbResultOnAction(ActionEvent event) {

    }

}


package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.sisiralearners.dto.InstructorDto;
import lk.ijse.gdse72.sisiralearners.dto.tm.InstructorTM;
import lk.ijse.gdse72.sisiralearners.model.InstructorModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructorFormController implements Initializable {

    private InstructorModel instructorModel = new InstructorModel();

    @FXML
    private JFXComboBox<String> cmbVehicleClass;

    @FXML
    private AnchorPane paneInstructor;

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<InstructorTM, String> colInstructorID;

    @FXML
    private TableColumn<InstructorTM, String> colName;

    @FXML
    private TableColumn<InstructorTM, String> colEmail;

    @FXML
    private TableColumn<InstructorTM, String> colContact;

    @FXML
    private TableColumn<InstructorTM, String> colVehicleClass;

    @FXML
    private TableView<InstructorTM> tblInstructor;

    @FXML
    private JFXTextField txtInstructorId;

    @FXML
    private JFXTextField txtInstructorName;

    @FXML
    private JFXTextField txtInstructorEmail;

    @FXML
    private JFXTextField txtContact;

    @FXML
    void tblInstructorOnClick(MouseEvent event) {
        InstructorTM selectedItem = tblInstructor.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtInstructorId.setText(selectedItem.getInstructor_id());
            txtInstructorName.setText(selectedItem.getName());
            txtInstructorEmail.setText(selectedItem.getEmail());
            txtContact.setText(selectedItem.getContact());
            cmbVehicleClass.getSelectionModel().select(selectedItem.getVehicle_class());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String instructorId = txtInstructorId.getText();
        String name = txtInstructorName.getText();
        String email = txtInstructorEmail.getText();
        String contact = txtContact.getText();
        String vehicleClass = cmbVehicleClass.getValue();

        InstructorDto instructorDto = new InstructorDto(instructorId, name, email, contact, vehicleClass);

        try {
            boolean isSaved = instructorModel.saveInstructor(instructorDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save instructor!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the instructor: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String instructorId = txtInstructorId.getText();
        String name = txtInstructorName.getText();
        String email = txtInstructorEmail.getText();
        String contact = txtContact.getText();
        String vehicleClass = cmbVehicleClass.getValue();

        InstructorDto instructorDto = new InstructorDto(instructorId, name, email, contact, vehicleClass);
        boolean isUpdated = instructorModel.updateInstructor(instructorDto);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Instructor updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update instructor!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String instructorId = txtInstructorId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this instructor?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = instructorModel.deleteInstructor(instructorId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete instructor!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnResetOnAction(MouseEvent event) throws SQLException {
        refreshPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInstructorID.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colVehicleClass.setCellValueFactory(new PropertyValueFactory<>("vehicle_class"));

        try {
            refreshPage();
            loadVehicleClass();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextInstructorId = instructorModel.getNextInstructorId();
        txtInstructorId.setText(nextInstructorId);

        txtInstructorName.setText("");
        txtInstructorEmail.setText("");
        txtContact.setText("");
        cmbVehicleClass.getSelectionModel().clearSelection();
        loadVehicleClass();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<InstructorDto> instructorDtos = instructorModel.getAllInstructors();
        ObservableList<InstructorTM> instructorTMS = FXCollections.observableArrayList();

        for (InstructorDto instructorDto : instructorDtos) {
            InstructorTM instructorTM = new InstructorTM(
                    instructorDto.getInstructor_id(),
                    instructorDto.getName(),
                    instructorDto.getEmail(),
                    instructorDto.getContact(),
                    instructorDto.getVehicle_class()
            );
            instructorTMS.add(instructorTM);
        }
        tblInstructor.setItems(instructorTMS);
    }

    private void loadVehicleClass() {
        ObservableList<String> Vclass = FXCollections.observableArrayList("A", "B", "A1", "B", "B1", "B2", "C", "C1", "C2", "G1", "D1", "J", "H");
        cmbVehicleClass.setItems(Vclass);
    }
}

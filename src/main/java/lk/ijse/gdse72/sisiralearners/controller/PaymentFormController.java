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
import lk.ijse.gdse72.sisiralearners.dto.PaymentDto;
import lk.ijse.gdse72.sisiralearners.dto.tm.PaymentTM;
import lk.ijse.gdse72.sisiralearners.model.PaymentModel;
import lk.ijse.gdse72.sisiralearners.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    private PaymentModel paymentModel = new PaymentModel();
    private StudentModel studentModel = new StudentModel();

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbStudentNames;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, java.sql.Date> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colStudentName;

    @FXML
    private TableColumn<?, ?> colNote;

    @FXML
    private AnchorPane paneExam;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtNote;

    @FXML
    private JFXTextField txtPaymentId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = paymentModel.deletePayment(paymentId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete payment!").show();
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

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        String studentName = cmbStudentNames.getValue();
        String note = txtNote.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        java.sql.Date paymentDate = new java.sql.Date(System.currentTimeMillis());

        try {
            String studentId = studentModel.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            PaymentDto paymentDto = new PaymentDto(paymentId, studentId, note, amount, paymentDate);
            boolean isSaved = paymentModel.savePayment(paymentDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save payment!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the payment: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String paymentId = txtPaymentId.getText();
        String studentName = cmbStudentNames.getValue();
        String note = txtNote.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        java.sql.Date paymentDate = new java.sql.Date(System.currentTimeMillis());

        try {
            String studentId = studentModel.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            PaymentDto paymentDto = new PaymentDto(paymentId, studentId, note, amount, paymentDate);
            boolean isUpdated = paymentModel.updatePayment(paymentDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update payment!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the payment: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblPaymentOnClicked(MouseEvent event) {
        PaymentTM selectedItem = tblPayment.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtPaymentId.setText(selectedItem.getPayment_id());
            cmbStudentNames.getSelectionModel().select(selectedItem.getStudent_id());
            txtNote.setText(selectedItem.getNote());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));

            // Convert java.util.Date to java.time.LocalDate
            java.util.Date paymentDate = selectedItem.getPayment_date();
            if (paymentDate != null) {
                datepicker.setValue(new java.sql.Date(paymentDate.getTime()).toLocalDate());
            }

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("payment_date"));

        datepicker.setValue(LocalDate.now());

        try {
            refreshPage();
            loadStudentNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextPaymentId = paymentModel.getNextPaymentId();
        txtPaymentId.setText(nextPaymentId);
        txtNote.setText("");
        txtAmount.setText("");
        cmbStudentNames.getSelectionModel().clearSelection();
        loadStudentNames();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<PaymentDto> paymentDtos = paymentModel.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDto paymentDto : paymentDtos) {
            String studentName = studentModel.getAllStudents().stream()
                    .filter(student -> student.getStudent_id().equals(paymentDto.getStudent_id()))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getName();

            PaymentTM paymentTM = new PaymentTM(
                    paymentDto.getPayment_id(),
                    studentName,
                    paymentDto.getNote(),
                    paymentDto.getAmount(),
                    paymentDto.getPayment_date()
            );
            paymentTMS.add(paymentTM);
        }
        tblPayment.setItems(paymentTMS);
    }

    private void loadStudentNames() throws SQLException {
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        studentNames.addAll(studentModel.getAllStudentNames());
        cmbStudentNames.setItems(studentNames);
    }
}
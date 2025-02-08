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
import lk.ijse.gdse72.sisiralearners.dto.BookingDto;
import lk.ijse.gdse72.sisiralearners.dto.SessionDto;
import lk.ijse.gdse72.sisiralearners.dto.tm.BookingTM;
import lk.ijse.gdse72.sisiralearners.model.BookingModel;
import lk.ijse.gdse72.sisiralearners.model.SessionModel;
import lk.ijse.gdse72.sisiralearners.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookingFormController implements Initializable {

    private BookingModel bookingModel = new BookingModel();
    private StudentModel studentModel = new StudentModel();
    private SessionModel sessionModel = new SessionModel();
    private Map<String, String> sessionMap = new HashMap<>();

    @FXML
    private DatePicker DPbookingDate;

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbPractiseSession;

    @FXML
    private JFXComboBox<String> cmbStudent;

    @FXML
    private TableColumn<BookingTM, String> colBookingDate;

    @FXML
    private TableColumn<BookingTM, String> colBookingId;

    @FXML
    private TableColumn<BookingTM, String> colPractiseSession;

    @FXML
    private TableColumn<BookingTM, String> colStudentName;

    @FXML
    private AnchorPane paneSession;

    @FXML
    private TableView<BookingTM> tblBooking;

    @FXML
    private JFXTextField txtBookingId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookingId = txtBookingId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this booking?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = bookingModel.deleteBooking(bookingId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Booking deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete booking!").show();
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
        String bookingId = txtBookingId.getText();
        String studentName = cmbStudent.getValue();
        String practiseSession = cmbPractiseSession.getValue();
        String sessionId = getSelectedSessionId();
        java.sql.Date bookingDate = java.sql.Date.valueOf(DPbookingDate.getValue());

        try {
            String studentId = studentModel.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            BookingDto bookingDto = new BookingDto(bookingId, studentId, sessionId, bookingDate, practiseSession);
            boolean isSaved = bookingModel.saveBooking(bookingDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Booking saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save booking!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the booking: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String bookingId = txtBookingId.getText();
        String studentName = cmbStudent.getValue();
        String practiseSession = cmbPractiseSession.getValue();
        String sessionId = getSelectedSessionId();
        java.sql.Date bookingDate = java.sql.Date.valueOf(DPbookingDate.getValue());

        try {
            String studentId = studentModel.getAllStudents().stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getStudent_id();

            BookingDto bookingDto = new BookingDto(bookingId, studentId, sessionId, bookingDate, practiseSession);
            boolean isUpdated = bookingModel.updateBooking(bookingDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Booking updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update booking!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the booking: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblBookingOnClick(MouseEvent event) {
        BookingTM selectedItem = tblBooking.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtBookingId.setText(selectedItem.getBooking_id());
            cmbStudent.getSelectionModel().select(selectedItem.getStudent_name());
            cmbPractiseSession.getSelectionModel().select(selectedItem.getPractise_session());

            java.util.Date paymentDate = selectedItem.getBooking_date();
            if (paymentDate != null) {
                DPbookingDate.setValue(new java.sql.Date(paymentDate.getTime()).toLocalDate());
            }

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colPractiseSession.setCellValueFactory(new PropertyValueFactory<>("practise_session"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("booking_date"));

        try {
            refreshPage();
            loadStudents();
            loadPractiseSessions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextBookingId = bookingModel.getNextBookingId();
        txtBookingId.setText(nextBookingId);

        DPbookingDate.setValue(null);
        cmbStudent.getSelectionModel().clearSelection();
        cmbPractiseSession.getSelectionModel().clearSelection();
        loadStudents();
        loadPractiseSessions();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<BookingDto> bookingDtos = bookingModel.getAllBookings();
        ObservableList<BookingTM> bookingTMS = FXCollections.observableArrayList();

        for (BookingDto bookingDto : bookingDtos) {
            String studentName = studentModel.getAllStudents().stream()
                    .filter(student -> student.getStudent_id().equals(bookingDto.getStudent_id()))
                    .findFirst()
                    .orElseThrow(() -> new SQLException("Student not found"))
                    .getName();

            BookingTM bookingTM = new BookingTM(
                    bookingDto.getBooking_id(),
                    studentName,
                    bookingDto.getPractise_session(),
                    bookingDto.getBooking_date()
            );
            bookingTMS.add(bookingTM);
        }
        tblBooking.setItems(bookingTMS);
    }

    private void loadStudents() throws SQLException {
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        studentNames.addAll(studentModel.getAllStudentNames());
        cmbStudent.setItems(studentNames);
    }

    private void loadPractiseSessions() throws SQLException {
        ObservableList<String> practiseSessions = FXCollections.observableArrayList();
        ArrayList<SessionDto> sessions = sessionModel.getAllSessions();

        for (SessionDto session : sessions) {
            String formattedSession = String.format("%s %s - %s", session.getDay(), session.getStart_time(), session.getEnd_time());
            practiseSessions.add(formattedSession);
            sessionMap.put(formattedSession, session.getSession_id());
        }

        cmbPractiseSession.setItems(practiseSessions);
    }

    private String getSelectedSessionId() {
        String selectedSession = cmbPractiseSession.getValue();
        return sessionMap.get(selectedSession);
    }
}
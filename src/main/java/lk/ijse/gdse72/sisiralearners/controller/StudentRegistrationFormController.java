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
import lk.ijse.gdse72.sisiralearners.db.DBConnection;
import lk.ijse.gdse72.sisiralearners.dto.PaymentDto;
import lk.ijse.gdse72.sisiralearners.dto.StudentDto;
import lk.ijse.gdse72.sisiralearners.dto.StudentRegistrationDto;
import lk.ijse.gdse72.sisiralearners.dto.tm.StudentRegistrationTM;
import lk.ijse.gdse72.sisiralearners.model.CourseModel;
import lk.ijse.gdse72.sisiralearners.model.PaymentModel;
import lk.ijse.gdse72.sisiralearners.model.StudentModel;
import lk.ijse.gdse72.sisiralearners.model.StudentRegistrationModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentRegistrationFormController implements Initializable {

    @FXML
    private Button btnRegister;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private JFXComboBox<String> cmbCourse;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private Label lblCourseFee;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtInitialPayment;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private JFXTextField txtStudentRegId;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colCourseName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colReg;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableView<StudentRegistrationTM> tblStudentRegistration;

    private final StudentModel studentModel = new StudentModel();
    private final StudentRegistrationModel studentRegistrationModel = new StudentRegistrationModel();
    private final PaymentModel paymentModel = new PaymentModel();
    private final CourseModel courseModel = new CourseModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            txtStudentRegId.setText(studentRegistrationModel.getNextStudentRegistrationId());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load next student registration ID: " + e.getMessage()).show();
        }
        loadActiveCourses();
        loadStatus();
        cmbStatus.getSelectionModel().select("Active");
        cmbCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setCourseFee(newValue);
            }
        });
        initializeTableColumns();
        loadStudentRegistrations();
    }

    private void initializeTableColumns() {
        colReg.setCellValueFactory(new PropertyValueFactory<>("registration_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("student_email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("student_contact"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("pay_balance"));
    }

    private void loadStudentRegistrations() {
        try {
            List<StudentRegistrationTM> studentRegistrations = studentRegistrationModel.getAllStudentRegistrations();
            ObservableList<StudentRegistrationTM> observableList = FXCollections.observableArrayList(studentRegistrations);
            tblStudentRegistration.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load student registrations: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException {
        String studentId = studentModel.getNextStudentId();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String nic = txtNIC.getText();
        String contact = txtPhone.getText();
        double initialPayment = Double.parseDouble(txtInitialPayment.getText());
        String courseName = cmbCourse.getSelectionModel().getSelectedItem();
        String courseId = courseModel.getCourseId(courseName);
        Date registrationDate = java.sql.Date.valueOf(LocalDate.now());
        String registrationId = studentRegistrationModel.getNextStudentRegistrationId();
        String paymentId = paymentModel.getNextPaymentId();
        String status = cmbStatus.getSelectionModel().getSelectedItem();

        StudentDto studentDto = new StudentDto(studentId, name, email, nic, contact, 0.0);
        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto(registrationId, studentId, courseId, registrationDate, status);
        PaymentDto paymentDto = new PaymentDto(paymentId, studentId, "Initial Payment", initialPayment, registrationDate);

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isStudentInserted = studentModel.saveStudent(studentDto);
            if (!isStudentInserted) throw new SQLException("Failed to insert into Student");

            boolean isStudentRegistrationInserted = studentRegistrationModel.saveStudentRegistration(studentRegistrationDto);
            if (!isStudentRegistrationInserted) throw new SQLException("Failed to insert into StudentRegistration");

            boolean isPaymentInserted = paymentModel.savePayment(paymentDto);
            if (!isPaymentInserted) throw new SQLException("Failed to insert into Payment");

            connection.commit();
            connection.setAutoCommit(true);
            new Alert(Alert.AlertType.INFORMATION, "Student registered successfully!").show();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Failed to register student: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnResetOnAction(MouseEvent event) {
        try {
            txtStudentRegId.setText(studentRegistrationModel.getNextStudentRegistrationId());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load next student registration ID: " + e.getMessage()).show();
        }
        txtName.clear();
        txtEmail.clear();
        txtNIC.clear();
        txtPhone.clear();
        txtInitialPayment.clear();
        cmbCourse.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().select("Active");
        lblCourseFee.setText("00 000 .00");
    }

    private void loadActiveCourses() {
        try {
            List<String> courses = courseModel.getActiveCourses();
            cmbCourse.getItems().addAll(courses);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load courses: " + e.getMessage()).show();
        }
    }

    private void setCourseFee(String courseName) {
        try {
            double courseFee = courseModel.getCourseFee(courseName);
            lblCourseFee.setText(String.format("%.2f", courseFee));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load course fee: " + e.getMessage()).show();
        }
    }

    private void loadStatus() {
        ObservableList<String> status = FXCollections.observableArrayList("Inactive", "Active");
        cmbStatus.setItems(status);
    }

    @FXML
    public void tblSROnClicked(MouseEvent mouseEvent) throws SQLException {
        StudentRegistrationTM selectedRegistration = tblStudentRegistration.getSelectionModel().getSelectedItem();
        if (selectedRegistration != null) {
            String studentId = studentModel.getStudentId(selectedRegistration.getStudent_name());
            String courseName = courseModel.getCourseNameByStudentId(studentId);

            txtStudentRegId.setText(selectedRegistration.getRegistration_id());
            txtName.setText(selectedRegistration.getStudent_name());
            txtEmail.setText(selectedRegistration.getStudent_email());
            txtNIC.setText(selectedRegistration.getStudent_contact());
            txtPhone.setText(selectedRegistration.getStudent_contact());
            cmbCourse.getSelectionModel().select(courseName);
            cmbStatus.getSelectionModel().select(selectedRegistration.getStatus());
            lblCourseFee.setText(String.format("%.2f", selectedRegistration.getPay_balance()));

            txtInitialPayment.setDisable(true);
            btnRegister.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String registrationId = txtStudentRegId.getText();
        String studentId = studentModel.getStudentId(txtName.getText());
        String name = txtName.getText();
        String email = txtEmail.getText();
        String nic = txtNIC.getText();
        String contact = txtPhone.getText();
        String courseName = cmbCourse.getSelectionModel().getSelectedItem();
        String courseId = courseModel.getCourseId(courseName);
        String status = cmbStatus.getSelectionModel().getSelectedItem();

        StudentDto studentDto = new StudentDto(studentId, name, email, nic, contact, 0.0);
        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto(registrationId, studentId, courseId, null, status);

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isStudentUpdated = studentModel.updateStudent(studentDto);
            if (!isStudentUpdated) throw new SQLException("Failed to update Student");

            boolean isStudentRegistrationUpdated = studentRegistrationModel.updateStudentRegistration(studentRegistrationDto);
            if (!isStudentRegistrationUpdated) throw new SQLException("Failed to update StudentRegistration");

            connection.commit();
            connection.setAutoCommit(true);
            new Alert(Alert.AlertType.INFORMATION, "Student updated successfully!").show();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Failed to update student: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String registrationId = txtStudentRegId.getText();

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isPaymentDeleted = paymentModel.deletePaymentByRegistrationId(registrationId);
            if (!isPaymentDeleted) throw new SQLException("Failed to delete from Payment");

            boolean isStudentRegistrationDeleted = studentRegistrationModel.deleteStudentRegistration(registrationId);
            if (!isStudentRegistrationDeleted) throw new SQLException("Failed to delete from StudentRegistration");

            boolean isStudentDeleted = studentModel.deleteStudent(studentModel.getStudentId(txtName.getText()));
            if (!isStudentDeleted) throw new SQLException("Failed to delete from Student");

            connection.commit();
            connection.setAutoCommit(true);
            new Alert(Alert.AlertType.INFORMATION, "Student registration deleted successfully!").show();
            btnResetOnAction(null); // Reset the form after deletion
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Failed to delete student registration: " + e.getMessage()).show();
        }
    }
}
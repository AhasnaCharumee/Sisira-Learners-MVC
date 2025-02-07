package lk.ijse.gdse72.sisiralearners.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashBoardFormController {

    @FXML
    private ImageView btnBooking;

    @FXML
    private ImageView btnCourses;

    @FXML
    private ImageView btnInstructor;

    @FXML
    private ImageView btnLogOt;

    @FXML
    private ImageView btnPayment;

    @FXML
    private ImageView btnSession;

    @FXML
    private ImageView btnStudent;

    @FXML
    private ImageView btnUsersettigs;

    @FXML
    private ImageView btnVehicle;

    @FXML
    private ImageView btndashboard;

    @FXML
    private AnchorPane dashBoardpane;

    @FXML
    private AnchorPane homepage;

    @FXML
    private AnchorPane navpanel;

    @FXML
    private ImageView btnExam;

    @FXML
    void btnBookingOnAction(MouseEvent event) {

    }

    @FXML
    void btnCoursesOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CourseForm.fxml"));
        homepage.getChildren().add(anchorPane);

    }

    @FXML
    void btnInstructorOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/InstructorForm.fxml"));
        homepage.getChildren().add(anchorPane);

    }

    @FXML
    void btnLogOtOnAction(MouseEvent event) throws IOException {
        Window window = dashBoardpane.getScene().getWindow();
        window.hide();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setTitle("Sisira Learners");
        stage.setScene(new Scene(anchorPane));
        stage.show();

    }

    @FXML
    void btnPaymentOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/PaymentForm.fxml"));
        homepage.getChildren().add(anchorPane);

    }

    @FXML
    void btnSessionOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SessionForm.fxml"));
        homepage.getChildren().add(anchorPane);

    }

    @FXML
    void btnStudentOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentRegistrationForm.fxml"));
        homepage.getChildren().add(anchorPane);

    }

    @FXML
    void btnUsersettigsOnAction(MouseEvent event) {

    }

    @FXML
    void btnVehicleOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/VehicleForm.fxml"));
        homepage.getChildren().add(anchorPane);

    }

    @FXML
    void btnExamOnAction(MouseEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/ExamForm.fxml"));
        homepage.getChildren().add(anchorPane);

    }

    @FXML
    void btndashboardOnAction(MouseEvent event) {

    }

}

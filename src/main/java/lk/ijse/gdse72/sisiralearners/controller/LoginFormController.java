package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse72.sisiralearners.dto.UserDto;
import lk.ijse.gdse72.sisiralearners.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {


    @FXML
    private AnchorPane loginpane;

    @FXML
    private Button signInbtn;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    private UserModel userModel = new UserModel();

    @FXML
    void signInbtnOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            boolean isauthenticate = userModel.authenticateUser(userName, password);
            if (isauthenticate) {
                // Hide the login window
                Window window = loginpane.getScene().getWindow();
                window.hide();

                // Load the dashboard
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Sisira Learners");
                stage.setFullScreen(true);
                stage.setScene(new Scene(anchorPane));
                stage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid email or password!").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }
}
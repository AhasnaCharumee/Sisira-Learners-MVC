package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private ImageView NameImage;

    @FXML
    private JFXTextField NameTxt;

    @FXML
    private ImageView PasswordImage;

    @FXML
    private AnchorPane loginpane;

    @FXML
    private Button signInbtn;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    void signInbtnOnAction(ActionEvent event) throws IOException {
        Window window = loginpane.getScene().getWindow();
        window.hide();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"));
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setTitle("Sisira Learners");
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

}

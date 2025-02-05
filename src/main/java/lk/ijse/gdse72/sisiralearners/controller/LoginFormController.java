package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
        loginpane.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"));
        loginpane.getChildren().add(anchorPane);
    }

}

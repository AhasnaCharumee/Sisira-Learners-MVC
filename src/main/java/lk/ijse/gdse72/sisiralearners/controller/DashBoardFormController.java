package lk.ijse.gdse72.sisiralearners.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardFormController {

    @FXML
    private Button btnStudent;

    @FXML
    private AnchorPane dashBoardpane;

    @FXML
    private AnchorPane homepage;

    @FXML
    private ImageView userbtn;

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        homepage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml"));
        homepage.getChildren().add(anchorPane);
    }

    @FXML
    void userbtnOnAction(MouseEvent event) {

    }

}

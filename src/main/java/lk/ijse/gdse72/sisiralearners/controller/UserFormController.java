package lk.ijse.gdse72.sisiralearners.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse72.sisiralearners.dto.UserDto;
import lk.ijse.gdse72.sisiralearners.dto.tm.UserTM;
import lk.ijse.gdse72.sisiralearners.model.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFormController {

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    private UserModel userModel = new UserModel();

    @FXML
    void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadRoles();
        loadUsers();
    }

    private void loadRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");
        cmbRole.setItems(roles);
    }

    private void loadUsers() {
        try {
            List<UserDto> userDtos = userModel.getAllUsers();
            ObservableList<UserTM> userTMS = FXCollections.observableArrayList();
            for (UserDto userDto : userDtos) {
                userTMS.add(new UserTM(
                        userDto.getUser_id(),
                        userDto.getUser_name(),
                        userDto.getEmail(),
                        userDto.getPassword(),
                        userDto.getRole()
                ));
            }
            tblUser.setItems(userTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load users: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        try {
            boolean isDeleted = userModel.deleteUser(userId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!").show();
                loadUsers();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete user!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete user: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnResetOnAction(MouseEvent event) {
        resetForm();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getValue();

        UserDto userDto = new UserDto(userId, userName, email, password, role);
        try {
            boolean isSaved = userModel.saveUser(userDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
                loadUsers();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save user!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save user: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getValue();

        UserDto userDto = new UserDto(userId, userName, email, password, role);
        try {
            boolean isUpdated = userModel.updateUser(userDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
                loadUsers();
                resetForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update user: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblSessionOnClick(MouseEvent event) {
        UserTM selectedUser = tblUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            txtUserId.setText(selectedUser.getUser_id());
            txtUserName.setText(selectedUser.getUser_name());
            txtEmail.setText(selectedUser.getEmail());
            txtPassword.setText(selectedUser.getPassword());
            cmbRole.setValue(selectedUser.getRole());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void resetForm() {
        txtUserId.setText("");
        txtUserName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        cmbRole.setValue(null);

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
}
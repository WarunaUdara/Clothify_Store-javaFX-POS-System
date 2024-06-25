package org.icet.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import lombok.Setter;
import org.icet.pos.bo.custom.impl.ChangePasswordBoImpl;
import org.icet.pos.util.FormUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtRetypePassword;

    @FXML
    private JFXButton btnChangePassword;

    @FXML
    private JFXButton btnBackToLogin;

    @FXML
    private Label lblNotification;

    private ChangePasswordBoImpl bo = new ChangePasswordBoImpl();
    // Setter to receive email from the previous form
    @Setter
    private String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization code, if needed
    }

    @FXML
    public void btnChangePasswordOnAction(ActionEvent actionEvent) {
        System.out.println(email);
        String newPassword = txtNewPassword.getText().trim();
        String retypePassword = txtRetypePassword.getText().trim();

        if (newPassword.isEmpty() || retypePassword.isEmpty()) {
            lblNotification.setText("Password fields cannot be empty.");
            lblNotification.setStyle("-fx-text-fill: red;");
        } else if (!newPassword.equals(retypePassword)) {
            lblNotification.setText("Passwords do not match.");
            lblNotification.setStyle("-fx-text-fill: red;");
        } else {
            // Implement password change logic here
            bo.changePassword(email, retypePassword);
            lblNotification.setText("Password changed successfully.");
            lblNotification.setStyle("-fx-text-fill: green;");
        }
    }

    @FXML
    public void btnBackToLoginOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        FormUtil.switchScene(stage, "loginForm.fxml");
    }

}

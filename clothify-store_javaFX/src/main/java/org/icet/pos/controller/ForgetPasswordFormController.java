package org.icet.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.icet.pos.bo.custom.ForgetPasswordBo;
import org.icet.pos.bo.custom.impl.EmailSender;
import org.icet.pos.bo.custom.impl.ForgetPasswordBoImpl;
import org.icet.pos.util.FormUtil;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class ForgetPasswordFormController implements Initializable {

    public TextField txtOTP;
    @FXML
    private TextField txtEmail;

    @FXML
    private JFXButton btnSendEmail;

    @FXML
    private JFXButton btnBackToLogin;

    @FXML
    private Label lblNotification;
    private final ForgetPasswordBo bo = new ForgetPasswordBoImpl();
    private String otp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        otp = bo.generateOtp();
        txtOTP.setVisible(false);
    }

    @FXML
    public void txtEmailKeyReleasedOnAction(KeyEvent keyEvent) {
        // Email validation logic here (optional)
    }

    @FXML
    public void btnSendEmailOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            lblNotification.setText("Please enter your email address.");
        } else {
            // Send email
            try {
                txtOTP.setVisible(true);
                String subject = "Password Reset OTP";
                otp = bo.generateOtp();
                EmailSender.sendEmail(email, subject, otp);
                FormUtil.showAlert(Alert.AlertType.INFORMATION,
                        "Success", "Email Sent",
                        "An email has been sent to \n" + email);
            } catch (Exception e) {
                txtOTP.setVisible(false);
                log.error("Exception : ", e);
                FormUtil.showAlert(Alert.AlertType.ERROR,
                        "Error", "Failed to Send Email",
                        "Please try again.");
            }
        }
    }

    @FXML
    public void btnBackToLoginOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        FormUtil.switchScene(stage, "loginForm.fxml");
    }

    @FXML
    public void txtOTPKeyReleasedOnAction(KeyEvent keyEvent) {
        if (txtOTP.getText().equals(otp)) {
            Stage stage = (Stage) ((javafx.scene.Node) keyEvent.getSource()).getScene().getWindow();
            FormUtil.switchScene(stage, "changePasswordForm.fxml", txtEmail.getText().trim());
        } else if (txtOTP.getText().length() == 6) {
            showNotification("try to resend otp");
        }
    }

    private void showNotification(String message) {
        lblNotification.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> lblNotification.setText(""));
        delay.play();
    }
}

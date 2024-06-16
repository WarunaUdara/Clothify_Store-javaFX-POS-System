package org.icet.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.icet.pos.bo.BoFactory;
import org.icet.pos.bo.custom.login_page.LoginControllerImpl;
import org.icet.pos.model.EmployeeModel;
import org.icet.pos.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LoginFormController implements Initializable {


    public TextField txtEmail;
    public JFXButton btnLogin;
    LoginControllerImpl controller = BoFactory.getInstance().getBo(BoType.LOGIN);
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    public PasswordField txtPassword;
    public Label lblNotification;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //EmailSender.sendEmail("warunaudarasam2003@gmail.com","123456");

        btnLogin.setDisable(true);


    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        try {
            AtomicBoolean isExistingEmployee = new AtomicBoolean(false);

            List<EmployeeModel> employeeDetail = controller.getEmployeeDetail();
            for (EmployeeModel employee : employeeDetail) {
                if (txtEmail.getText().equals(employee.getEmail())) {
                    if (txtPassword.getText().equals(employee.getPassword())) {
                        isExistingEmployee.set(true);
                        btnLogin.getScene().getWindow().hide();
                        Parent root = null;

                        root = FXMLLoader.load(getClass().getResource("/view/dashBoardForm.fxml"));

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                        break;

                    }

                }
            }
            if (!isExistingEmployee.get()) {
                new Alert(Alert.AlertType.ERROR, "Invalid Email or Password").show();
            }
        } catch (IOException e) {
            log.error("Exception : ", e);
        }


    }

    public static boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void btnForgetPasswordOnAction(ActionEvent actionEvent) {
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
    }


    public void txtPasswordKeyReleasedOnAction(KeyEvent keyEvent) {
        boolean isEmailValid = validateEmail(txtEmail.getText());
        boolean isPasswordValid = validatePassword(txtPassword.getText());

        if (!isEmailValid) {
            lblNotification.setText("Invalid email format.");
        } else if (!isPasswordValid) {
            lblNotification.setText("Password must be at least 6 characters.");
        } else {
            lblNotification.setText("");
        }

        btnLogin.setDisable(!(isEmailValid && isPasswordValid));

    }


    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }


    public void txtEmailKeyReleasedOnAction(KeyEvent keyEvent) {
        boolean isEmailValid = validateEmail(txtEmail.getText());
        boolean isPasswordValid = validatePassword(txtPassword.getText());

        if (!isEmailValid) {
            lblNotification.setText("Invalid email format.");
        } else if (!isPasswordValid) {
            lblNotification.setText("Password must be at least 6 characters.");
        } else {
            lblNotification.setText("");
        }

        btnLogin.setDisable(!(isEmailValid && isPasswordValid));
    }

}
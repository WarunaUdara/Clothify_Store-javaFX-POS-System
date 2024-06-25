package org.icet.pos.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.icet.pos.controller.ChangePasswordController;

import java.io.IOException;

@Slf4j
public class FormUtil {

    public static void switchScene(Stage stage, String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(FormUtil.class.getResource("/view/" + fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            log.error("Failed to load FXML file: {}", fxmlFileName, e);
        }
    }
    public static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public static void switchScene(Stage stage, String fxmlFile, String email) {
        try {
            FXMLLoader loader = new FXMLLoader(FormUtil.class.getResource("/view/" +fxmlFile));
            Parent root = loader.load();

            // Set email in the controller
            if (fxmlFile.equals("changePasswordForm.fxml")) {
                ChangePasswordController controller = loader.getController();
                controller.setEmail(email);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

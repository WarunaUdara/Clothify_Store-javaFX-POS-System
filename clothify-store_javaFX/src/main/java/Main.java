import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.icet.pos.dao.custom.impl.CustomerDaoImpl;
import org.icet.pos.dao.factory.DaoFactory;
import org.icet.pos.dao.factory.DaoType;
import org.icet.pos.entity.CustomerEntity;

import java.util.List;

public class Main extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load your FXML file (assuming it's named "addCustomerForm.fxml")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Scene scene = new Scene(loader.load()); // Set the initial size

        // Set the scene to the stage
        stage.setScene(scene);

        // Set the stage properties
        stage.setTitle("Clothify");

        // Handle window resize event
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (stage.isMaximized()) {
                // Set a minimum width (optional)
                double newWidth = Math.max(newValue.doubleValue(), 830);
                stage.setWidth(newWidth);
            }
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (stage.isMaximized()) {
                // Set a minimum height (optional)
                double newHeight = Math.max(newValue.doubleValue(), 500);
                stage.setHeight(newHeight);
            }
        });

        // Show the stage
        stage.show();



    }
}
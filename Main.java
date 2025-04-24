import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene-buldier.fxml"));
        stage.setTitle("JavaFX App");
        stage.setScene(new Scene(root));
        stage.show();
        stage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());



    }
    

    public static void main(String[] args) {



        // example Usage of Notification class
        /*
        
        Parser parser = new Parser();
        
        Notification notification = new Notification(
            "admin",
            "all",
            "System maintenance at 10pm",
            LocalDateTime.of(2025, 4, 23, 18, 30)
         );

            parser.writeNotificationToFile(notification, "notifications.txt");
        */

        
        launch(args);
    }
}

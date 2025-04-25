import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainThemeDesign.fxml"));
        stage.setTitle("JavaFX App");
        stage.setScene(new Scene(root));
        stage.show();
        stage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }
    public static void main(String[] args) {
        launch(args);
    }
}

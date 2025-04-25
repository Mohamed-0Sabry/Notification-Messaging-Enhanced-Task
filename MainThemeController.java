import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainThemeController implements Initializable {

    @FXML private ComboBox<String> targetRoleComboBox;
    @FXML private TextArea messageTextArea;
    @FXML private Button sendButton;
    @FXML private Button uploadButton; // mesh fahma
    @FXML private ListView<String> historyListView;

    private static final String NOTIFICATIONS_FILE = "notifications.txt";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        targetRoleComboBox.getItems().addAll("Student", "Instructor", "All");
        loadNotifications();
        sendButton.setOnAction(event -> sendAnnouncement());
       // uploadButton.setOnAction(event -> uploadMessageFromFile());
    }

    private void sendAnnouncement() {
        String selectedRole = targetRoleComboBox.getValue();
        String message = messageTextArea.getText().trim();

        if (selectedRole == null || selectedRole.isEmpty()) {
            showAlert("Please select a target role");
            return;
        }
        if (message.isEmpty()) {
            showAlert("Please enter a message");
            return;
        }

        // Create and save the notification
        Notification notification = new Notification(
                "admin",
                selectedRole,
                message,
                LocalDateTime.now()
        );

        Parser parser = new Parser();
        parser.writeNotificationToFile(notification, NOTIFICATIONS_FILE);
        messageTextArea.clear();
        loadNotifications();
    }
/* mesh fahma hahaha
    private void uploadMessageFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Message File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String content = Files.readString(selectedFile.toPath());
                messageTextArea.setText(content);
            } catch (IOException e) {
                showAlert("Failed to read file: " + e.getMessage());
            }
        }
    } */

    private void loadNotifications() {
        File file = new File(NOTIFICATIONS_FILE);
        if (!file.exists()) return;

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            List<String> formattedNotifications = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split("\\|"); //uses "|" delimiter
                if (parts.length >= 4) {
                    String formatted = String.format("[%s] To %s: %s",
                            parts[3], parts[1], parts[2]);
                    formattedNotifications.add(formatted);
                }
            }
            historyListView.getItems().setAll(formattedNotifications);
        } catch (IOException e) {
            showAlert("Failed to load notifications: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Get the dialog pane and apply styles
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm()
        );
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }
}
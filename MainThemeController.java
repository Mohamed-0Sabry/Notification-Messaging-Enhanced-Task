import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainThemeController implements Initializable {

    @FXML private ComboBox<String> targetRoleComboBox;
    @FXML private TextArea messageTextArea;
    @FXML private Button sendButton;
    @FXML private Button uploadButton;
    @FXML private ListView<String> historyListView;

    private NotificationService notificationService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUI();
        initializeService();
        initializeListeners();
        refreshNotificationHistory();
    }

    private void initializeUI() {
        targetRoleComboBox.getItems().addAll("Student", "Instructor", "All");
    }

    private void initializeService() {
        Parser parser = new Parser();
        notificationService = new NotificationService(parser, "notifications.txt");
    }

    private void initializeListeners() {
        sendButton.setOnAction(event -> handleSendNotification());
        uploadButton.setOnAction(event -> handleUploadMessage());
    }

    private void handleSendNotification() {
        String role = targetRoleComboBox.getValue();
        String message = messageTextArea.getText().trim();

        if (!isValidInput(role, message)) return;

        notificationService.sendNotification("admin", role, message);
        messageTextArea.clear();
        refreshNotificationHistory();
    }

    private void handleUploadMessage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Message File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String content = notificationService.readMessageFromFile(selectedFile);
                messageTextArea.setText(content);
            } catch (IOException e) {
                showAlert("Failed to read file: " + e.getMessage());
            }
        }
    }

    private void refreshNotificationHistory() {
        List<String> notifications = notificationService.loadNotifications();
        historyListView.getItems().setAll(notifications);
    }

    private boolean isValidInput(String role, String message) {
        if (role == null || role.isEmpty()) {
            showAlert("Please select a target role");
            return false;
        }
        if (message.isEmpty()) {
            showAlert("Please enter a message");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }
}

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainThemeController implements Initializable {
    @FXML
    private TextArea messageTextArea;

    @FXML
    private ComboBox<String> targetRoleComboBox;

    @FXML
    private Button sendButton;

    @FXML
    private Button uploadButton;

    @FXML
    private ListView<String> historyListView;

    private NotificationService notificationService;
    private Parser parser;
    private boolean isFileuploaded = false;
    private String StudentName; 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUI();
        initializeService();
        setupEventHandlers();
        refreshNotificationHistory();
    }

    private void initializeUI() {
        targetRoleComboBox.getItems().addAll("Student", "Instructor", "All");
    }

    private void initializeService() {
        parser = new Parser();
        notificationService = new NotificationService(parser, "notifications.txt");
    }

    private void setupEventHandlers() {
        sendButton.setOnAction(event -> handleSendNotification());
        uploadButton.setOnAction(event -> handleUploadMessage());
    }

    private void handleSendNotification() {
        String role = isFileuploaded ? StudentName : "Student";
        String message = messageTextArea.getText().trim();

        if (!isValidInput(role, message))
            return;

        notificationService.sendNotification("admin", role, message);
        messageTextArea.clear();
        refreshNotificationHistory();
    }

    private void handleUploadMessage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Student or Notification File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
    
        if (selectedFile != null) {
            try {
                List<Notification> notifications = parser.readNotificationsFromFile(selectedFile.getAbsolutePath());
    
                if (!notifications.isEmpty()) {
                    Notification notification = notifications.get(0);
                    if (!notification.getId().isEmpty()) {
                        this.StudentName = notification.getName();
                        this.isFileuploaded = true; 
                        targetRoleComboBox.setValue("Student");
                    } else {
                        messageTextArea.setText(notification.getMessage());
                        targetRoleComboBox.setValue(notification.getReceiver());
                    }
                } else {
                    showAlert("No valid notifications found in file.");
                }
    
            } catch (Exception e) {
                showAlert("Error reading file: " + e.getMessage());
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
        alert.showAndWait();
    }
}

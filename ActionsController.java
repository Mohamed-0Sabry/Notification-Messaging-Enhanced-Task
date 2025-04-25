/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myfxmlproject;

/**
 *
 * @author Yahya
 */
//just checking
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionsController {
   
    @FXML private ComboBox<String> targetRoleComboBox;
    @FXML private TextArea messageTextArea;
    @FXML private Button sendButton;
    @FXML private ListView<String> historyListView;
    

    private String path = "notifications.txt";
    private DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd, HH:mm");
    
    @FXML
    public void initialize() {
        targetRoleComboBox.getItems().addAll("Student", "Instructor", "All");
        targetRoleComboBox.getSelectionModel().selectFirst();
        
        sendButton.setOnAction(event -> handleSendButton());
        loadNotificationHistory();
    }
    
    
    private void handleSendButton() {
        String receiver = targetRoleComboBox.getValue();
        String message = messageTextArea.getText().trim();
        
        if (message.isEmpty()) {
            showAlert("Error", "Message cannot be empty");
            return;
        }
        
        try {
            Notification notification = new Notification("admin",receiver,message,LocalDateTime.now());
            
            new Parser().writeNotificationToFile(notification,path);
            
            messageTextArea.clear();
            loadNotificationHistory();
        } catch (Exception e) {
            showAlert("Error", "Failed to send notification: " + e.getMessage());
        }
    }
    
    private void loadNotificationHistory() {
    List<String> formattedNotifications = new ArrayList<>();
    File file = new File("notifications.txt");
    
    if (!file.exists()) return;
    
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
            
            if (parts.length >= 4) {
                String sender = parts[0];
                String receiver = parts[1];
                String message = parts[2];
                LocalDateTime timestamp = LocalDateTime.parse(parts[3], 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                
                formattedNotifications.add(
                    String.format("%s | From: %-6s | To: %-9s | %s", 
                        timestamp.format(DISPLAY_FORMAT),
                        sender,
                        receiver,
                        message)
                );
            }
        }
        
        historyListView.getItems().setAll(formattedNotifications);
    } catch (Exception e) {
        showAlert("Error", "Failed to load history: " + e.getMessage());
    }
}
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
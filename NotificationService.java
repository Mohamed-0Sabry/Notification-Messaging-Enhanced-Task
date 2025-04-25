import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

public class NotificationService {
    private final Parser parser;
    private final String notificationFilePath;

    public NotificationService(Parser parser, String notificationFilePath) {
        this.parser = parser;
        this.notificationFilePath = notificationFilePath;
    }

    public void sendNotification(String sender, String receiver, String message) {
        Notification notification = new Notification(sender, receiver, message, LocalDateTime.now());
        parser.writeNotificationToFile(notification, notificationFilePath);
    }

    public List<String> loadNotifications() {
        List<Notification> notifications = parser.readNotificationsFromFile(notificationFilePath);
        List<String> formatted = new ArrayList<>();

        for (Notification notification : notifications) {
            if (!notification.getId().isEmpty()) {
                // Format student data
                formatted.add(String.format("Student: %s (%s) - %s Year %d", 
                    notification.getName(),
                    notification.getMajor(),
                    notification.getEmail(),
                    notification.getYear()));
            } else {
                // Format regular notification
                formatted.add(String.format("[%s] %s -> %s: %s", 
                    notification.getDateTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    notification.getSender(),
                    notification.getReceiver(),
                    notification.getMessage()));
            }
        }

        return formatted;
    }

    public String readMessageFromFile(File file) {
        try {
            List<Notification> notifications = parser.readNotificationsFromFile(file.getAbsolutePath());
            if (!notifications.isEmpty()) {
                Notification notification = notifications.get(0);
                if (!notification.getId().isEmpty()) {
                    // Return student information
                    return String.format("Student: %s\nID: %s\nMajor: %s\nYear: %d",
                        notification.getName(),
                        notification.getId(),
                        notification.getMajor(),
                        notification.getYear());
                } else {
                    // Return notification message
                    return notification.getMessage();
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading message from file: " + e.getMessage());
        }
        return "";
    }
}

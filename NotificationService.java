import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            formatted.add(String.format(
                "[%s] | From: %s | To: %s | Message: %s", 
                notification.getDateTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                notification.getSender(),
                notification.getReceiver(),
                notification.getMessage()
            ));
        }

        return formatted;
    }

    public String readMessageFromFile(File file) {
        try {
            List<Notification> notifications = parser.readNotificationsFromFile(file.getAbsolutePath());
            if (!notifications.isEmpty()) {
                Notification notification = notifications.get(0);
                if (!notification.getId().isEmpty()) {
                    return String.format("Student: %s\nID: %s\nMajor: %s\nYear: %d",
                        notification.getName(),
                        notification.getId(),
                        notification.getMajor(),
                        notification.getYear());
                } else {
                    return notification.getMessage();
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading message from file: " + e.getMessage());
        }
        return "";
    }
}

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

public class NotificationService {
    private final Parser parser;
    private final String filePath;

    public NotificationService(Parser parser, String filePath) {
        this.parser = parser;
        this.filePath = filePath;
    }

    public void sendNotification(String sender, String receiver, String message) {
        Notification notification = new Notification(sender, receiver, message, LocalDateTime.now());
        parser.writeNotificationToFile(notification, filePath);
    }

    public List<String> loadNotifications() {
        List<Notification> notifications = parser.readNotificationsFromFile(filePath);
        List<String> formatted = new ArrayList<>();

        for (Notification n : notifications) {
            formatted.add(String.format("[%s] To %s: %s", n.getDateTime(), n.getReceiver(), n.getMessage()));
        }

        return formatted;
    }

    public String readMessageFromFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}

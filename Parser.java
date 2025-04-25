import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class FileNotificationReader {

    public List<Notification> readNotifications(String filePath) {
        List<Notification> notifications = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists())
            return notifications;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Notification notification = parseNotificationLine(line);
                if (notification != null) {
                    notifications.add(notification);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }

        return notifications;
    }

    private Notification parseNotificationLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, "|");
        if (tokenizer.countTokens() >= 4) {
            try {
                String sender = tokenizer.nextToken();
                String receiver = tokenizer.nextToken();
                String message = tokenizer.nextToken();
                String dateTimestr = tokenizer.nextToken();
                LocalDateTime dateTime = LocalDateTime.parse(dateTimestr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return new Notification(sender, receiver, message, dateTime);
            } catch (Exception e) {
                System.err.println("Invalid date time format: " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}

class FileNotificationWriter {
    public void writeNotification(Notification notification, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(notification.toString() + System.lineSeparator());
            System.out.println("Notification added to file.");
        } catch (IOException e) {
            System.err.println("Failed to write notification: " + e.getMessage());
        }
    }
}

public class Parser {
    private final FileNotificationReader reader;
    private final FileNotificationWriter writer;

    public Parser() {
        this.reader = new FileNotificationReader();
        this.writer = new FileNotificationWriter();
    }

    public void parseInputFile(String filePath) throws FileNotFoundException {
        reader.readNotifications(filePath).forEach(notification -> {
            System.out.println("----------");
            System.out.println("Sender: " + notification.getSender());
            System.out.println("Receiver: " + notification.getReceiver());
            System.out.println("Message: " + notification.getMessage());
            System.out.println("DateTime: " + notification.getDateTime());
            System.out.println("----------");
        });
    }
    
    public List<Notification> readNotificationsFromFile(String filePath) {
        return reader.readNotifications(filePath);
    }

    public void writeNotificationToFile(Notification notification, String filePath) {
        writer.writeNotification(notification, filePath);
    }

}

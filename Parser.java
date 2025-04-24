import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

class FileNotificationReader {
    public void readNotifications(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseNotificationLine(line);
            }
        }
    }

    private void parseNotificationLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, "|");
        if (tokenizer.countTokens() >= 4) {
            String sender = tokenizer.nextToken();
            String receiver = tokenizer.nextToken();
            String message = tokenizer.nextToken();
            String dateTime = tokenizer.nextToken();
            
            System.out.println("----------");
            System.out.println("Sender: " + sender);
            System.out.println("Receiver: " + receiver);
            System.out.println("Message: " + message);
            System.out.println("DateTime: " + dateTime);
            System.out.println("----------");
        }
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
        reader.readNotifications(filePath);
    }

    public void writeNotificationToFile(Notification notification, String filePath) {
        writer.writeNotification(notification, filePath);
    }
}

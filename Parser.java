
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser {
    private String receiverName;
    private String senderName;
    private String message;
    private String date;
    private String time;
    private String email;

    public void parseInputFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, "|");
            
            if (tokenizer.countTokens() >= 6) {
                tokenizer.nextToken(); 

                receiverName = tokenizer.nextToken();
                senderName = tokenizer.nextToken();
                message = tokenizer.nextToken();
                date = tokenizer.nextToken();
                email = tokenizer.nextToken();
                System.out.println("----------");
                System.out.println("Receiver: " + receiverName);
                System.out.println("Sender: " + senderName);
                System.out.println("Message: " + message);
                System.out.println("Date: " + date);
                System.out.println("Email: " + email);
                System.out.println("----------");
            }
        }
        scanner.close();
    }


    public void writeNotificationToFile(Notification notification, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) { // true = append mode
            writer.write(notification.toString() + System.lineSeparator());
            System.out.println("Notification added to file.");
        } catch (IOException e) {
            System.err.println("Failed to write notification: " + e.getMessage());
        }
    }
    
}

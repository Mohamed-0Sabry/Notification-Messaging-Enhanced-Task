
import java.io.File;
import java.io.FileNotFoundException;
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


    public void parseOutputFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
        }
        scanner.close();
    }
    
}

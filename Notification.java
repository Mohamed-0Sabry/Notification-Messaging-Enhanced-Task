import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {
    private String sender;
    private String receiver;
    private String message;
    private LocalDateTime dateTime;

    public Notification(String sender, String receiver, String message, LocalDateTime dateTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.dateTime = dateTime;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.join("|", sender, receiver, message, dateTime.format(formatter));
    }
}

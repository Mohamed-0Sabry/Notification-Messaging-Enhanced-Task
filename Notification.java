import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Notification {
    private final String sender;
    private final String receiver;
    private final String message;
    private final LocalDateTime dateTime;

    public Notification(String sender, String receiver, String message, LocalDateTime dateTime) {
        this.sender = validateString(sender, "Sender");
        this.receiver = validateString(receiver, "Receiver");
        this.message = validateString(message, "Message");
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime cannot be null");
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        return value.trim();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.join("|", sender, receiver, message, dateTime.format(formatter));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return sender.equals(that.sender) &&
               receiver.equals(that.receiver) &&
               message.equals(that.message) &&
               dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, message, dateTime);
    }
}

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Notification {
    private final String sender;
    private final String receiver;
    private final String message;
    private final LocalDateTime dateTime;
    private final String id;
    private final String name;
    private final String gender;
    private final LocalDate dob;
    private final String email;
    private final String major;
    private final int year;

    public Notification(String sender, String receiver, String message, LocalDateTime dateTime) {
        this.sender = validateString(sender, "Sender");
        this.receiver = validateString(receiver, "Receiver");
        this.message = validateString(message, "Message");
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime cannot be null");
        this.id = "";
        this.name = "";
        this.gender = "";
        this.dob = LocalDate.now();
        this.email = "";
        this.major = "";
        this.year = 0;
    }

    public Notification(String id, String name, String gender, LocalDate dob, String email, String major, int year) {
        this.id = validateString(id, "ID");
        this.name = validateString(name, "Name");
        this.gender = validateString(gender, "Gender");
        this.dob = Objects.requireNonNull(dob, "Date of birth cannot be null");
        this.email = validateString(email, "Email");
        this.major = validateString(major, "Major");
        this.year = year;
        this.sender = "System";
        this.receiver = this.name;
        this.message = "";
        this.dateTime = LocalDateTime.now();
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getMajor() {
        return major;
    }

    public int getYear() {
        return year;
    }

    public String toString() {
        if (!id.isEmpty()) {
            return String.join("|", id, name, gender, dob.toString(), email, major, String.valueOf(year));
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return String.join("|", sender, receiver, message, dateTime.format(formatter));
        }
    }

}

import java.io.Serializable;
import java.time.LocalDateTime;
public class ActivityLog implements Serializable {
    private static final long serialVersionUID = 1L;
    public LocalDateTime timestamp;
    public String action;
    public String details;
    public ActivityLog(LocalDateTime timestamp, String action, String details) {
        this.timestamp = timestamp;
        this.action = action;
        this.details = details;
    }
    @Override
    public String toString() {
        return String.format("[%s] %s: %s", timestamp, action, details);
    }
}

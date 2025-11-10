import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    public int userId;
    public int bookId;
    public LocalDate reserveDate;

    public Reservation(int userId, int bookId, LocalDate reserveDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.reserveDate = reserveDate;
    }

    @Override
    public String toString() {
        return String.format("Reservation: User %d reserved Book %d on %s", userId, bookId, reserveDate);
    }
}

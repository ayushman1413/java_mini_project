import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public String title;
    public String author;
    public String category;
    public String isbn;
    public int copiesAvailable;
    public boolean isBorrowed;
    public Integer borrowedByUserId; // null if available
    public LocalDate borrowDate; // use LocalDate for better date handling
    public LocalDate dueDate; // due date for borrowed books
    public String coverImagePath;
    public List<String> similarBooks; // list of similar book titles

    public Book(int id, String title, String author, String category, String isbn, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.copiesAvailable = copies;
        this.isBorrowed = false;
        this.borrowedByUserId = null;
        this.borrowDate = null;
        this.dueDate = null;
        this.coverImagePath = null;
        this.similarBooks = new ArrayList<>();
    }

    public boolean isLowStock() {
        return copiesAvailable < 2;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s — %s (%s) %s", id, title, author, category,
            (isBorrowed ? "(Borrowed by " + borrowedByUserId + ", Due: " + dueDate + ")" : "(Available: " + copiesAvailable + " copies)"));
    }
}

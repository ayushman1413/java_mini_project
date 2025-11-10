import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<Integer, Book> books = new HashMap<>();
    public Map<Integer, User> users = new HashMap<>();
    public int nextBookId = 1;
    public int nextUserId = 1;

    // Books
    public Book addBook(String title, String author) {
        Book b = new Book(nextBookId++, title, author);
        books.put(b.id, b);
        return b;
    }

    public boolean removeBook(int id) {
        return books.remove(id) != null;
    }

    public List<Book> listAllBooks() {
        List<Book> list = new ArrayList<>(books.values());
        list.sort(Comparator.comparingInt(b -> b.id));
        return list;
    }

    // Users
    public User addUser(String name, String email, String phone) {
        User u = new User(nextUserId++, name, email, phone);
        users.put(u.id, u);
        return u;
    }

    public List<User> listAllUsers() {
        List<User> list = new ArrayList<>(users.values());
        list.sort(Comparator.comparingInt(u -> u.id));
        return list;
    }

    // Borrow/Return
    public boolean borrowBook(int userId, int bookId, String borrowDate) {
        Book b = books.get(bookId);
        User u = users.get(userId);
        if (b == null || u == null) return false;
        if (b.isBorrowed) return false;
        b.isBorrowed = true;
        b.borrowedByUserId = userId;
        b.borrowDate = borrowDate;
        return true;
    }

    public boolean returnBook(int bookId) {
        Book b = books.get(bookId);
        if (b == null || !b.isBorrowed) return false;
        b.isBorrowed = false;
        b.borrowedByUserId = null;
        b.borrowDate = "";
        return true;
    }
}

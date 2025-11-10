import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<Integer, Book> books = new HashMap<>();
    public Map<Integer, User> users = new HashMap<>();
    public List<Reservation> reservations = new ArrayList<>();
    public List<ActivityLog> activityLogs = new ArrayList<>();
    public int nextBookId = 1;
    public int nextUserId = 1;

    // Books
    public Book addBook(String title, String author, String category, String isbn, int copies) {
        Book b = new Book(nextBookId++, title, author, category, isbn, copies);
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
    public User addUser(String name, String email, String phone, String role, String studentId) {
        User u = new User(nextUserId++, name, email, phone, role, studentId);
        users.put(u.id, u);
        return u;
    }

    public List<User> listAllUsers() {
        List<User> list = new ArrayList<>(users.values());
        list.sort(Comparator.comparingInt(u -> u.id));
        return list;
    }

    // Borrow/Return with copies, due dates, limits, logs
    public boolean borrowBook(int userId, int bookId) {
        Book b = books.get(bookId);
        User u = users.get(userId);
        if (b == null || u == null) return false;
        if (b.copiesAvailable <= 0) return false;
        // Check borrowing limit
        int currentBorrowed = (int) books.values().stream().filter(book -> book.borrowedByUserId != null && book.borrowedByUserId == userId).count();
        if (currentBorrowed >= u.getBorrowingLimit()) return false;
        b.copiesAvailable--;
        u.totalBooksBorrowed++;
        if (b.copiesAvailable == 0) {
            b.isBorrowed = true;
            b.borrowedByUserId = userId;
            b.borrowDate = LocalDate.now();
            b.dueDate = b.borrowDate.plusDays(7); // 7 days due
        }
        // Log activity
        activityLogs.add(new ActivityLog(LocalDateTime.now(), "Borrow", "User " + userId + " borrowed Book " + bookId));
        // Check reservations
        notifyReservations(bookId);
        return true;
    }

    public double returnBook(int bookId) {
        Book b = books.get(bookId);
        if (b == null || b.copiesAvailable >= 1) return -1; // not borrowed or invalid
        b.copiesAvailable++;
        if (b.copiesAvailable == 1) { // assuming 1 copy per book for simplicity, but can be adjusted
            b.isBorrowed = false;
            b.borrowedByUserId = null;
            LocalDate returnDate = LocalDate.now();
            long daysLate = ChronoUnit.DAYS.between(b.dueDate, returnDate);
            double fine = daysLate > 0 ? daysLate * 5.0 : 0.0; // ₹5 per day fine
            if (fine > 0) {
                User u = users.get(b.borrowedByUserId);
                if (u != null) {
                    Map<String, Object> fineEntry = new HashMap<>();
                    fineEntry.put("date", returnDate);
                    fineEntry.put("amount", fine);
                    fineEntry.put("paid", false);
                    u.fineHistory.add(fineEntry);
                }
            }
            b.borrowDate = null;
            b.dueDate = null;
            // Log activity
            activityLogs.add(new ActivityLog(LocalDateTime.now(), "Return", "Book " + bookId + " returned, fine: " + fine));
            // Check reservations
            notifyReservations(bookId);
        }
        return 0.0;
    }

    // Statistics
    public int getTotalBooks() {
        return books.size();
    }

    public int getIssuedBooks() {
        return (int) books.values().stream().filter(b -> b.isBorrowed).count();
    }

    public int getAvailableBooks() {
        return (int) books.values().stream().filter(b -> !b.isBorrowed).count();
    }

    public int getActiveUsers() {
        return users.size();
    }

    // Search books by title, author, or category
    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(query.toLowerCase()) ||
                b.author.toLowerCase().contains(query.toLowerCase()) ||
                b.category.toLowerCase().contains(query.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    // Reservation methods
    public boolean reserveBook(int userId, int bookId) {
        Book b = books.get(bookId);
        User u = users.get(userId);
        if (b == null || u == null || b.copiesAvailable > 0) return false; // only reserve if unavailable
        reservations.add(new Reservation(userId, bookId, LocalDate.now()));
        activityLogs.add(new ActivityLog(LocalDateTime.now(), "Reserve", "User " + userId + " reserved Book " + bookId));
        return true;
    }

    public void notifyReservations(int bookId) {
        List<Reservation> toNotify = reservations.stream().filter(r -> r.bookId == bookId).collect(Collectors.toList());
        for (Reservation r : toNotify) {
            // In real app, send notification or email
            System.out.println("Notify User " + r.userId + ": Book " + bookId + " is now available!");
            reservations.remove(r);
        }
    }

    // Recommendations
    public List<Book> getRecommendations(Book borrowedBook) {
        List<Book> recs = new ArrayList<>();
        for (Book b : books.values()) {
            if (!b.equals(borrowedBook) && (b.category.equals(borrowedBook.category) || borrowedBook.similarBooks.contains(b.title))) {
                recs.add(b);
            }
        }
        return recs.stream().limit(3).collect(Collectors.toList());
    }

    // Get overdue books
    public List<Book> getOverdueBooks() {
        LocalDate today = LocalDate.now();
        return books.values().stream().filter(b -> b.dueDate != null && b.dueDate.isBefore(today)).collect(Collectors.toList());
    }

    // Get activity logs
    public List<ActivityLog> getActivityLogs() {
        return new ArrayList<>(activityLogs);
    }

    // Import/Export (simplified, for books)
    public void exportBooksToCSV(String filename) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filename)) {
            writer.write("ID,Title,Author,Category,ISBN,Copies\n");
            for (Book b : books.values()) {
                writer.write(String.format("%d,%s,%s,%s,%s,%d\n", b.id, b.title, b.author, b.category, b.isbn, b.copiesAvailable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importBooksFromCSV(String filename) {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    String category = parts[3];
                    String isbn = parts[4];
                    int copies = Integer.parseInt(parts[5]);
                    books.put(id, new Book(id, title, author, category, isbn, copies));
                    if (id >= nextBookId) nextBookId = id + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

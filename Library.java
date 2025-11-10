import java.util.List;

public class Library {
    private Storage storage;

    public Library(Storage storage) {
        this.storage = storage;
    }

    public void addBook(Book book) {
        storage.addBook(book);
    }

    public List<Book> getAllBooks() {
        return storage.getAllBooks();
    }

    public Book findBookByIsbn(String isbn) {
        return storage.findBookByIsbn(isbn);
    }

    public boolean borrowBook(String isbn, User user) {
        Book book = findBookByIsbn(isbn);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            // In a real system, you'd track who borrowed it
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }
}

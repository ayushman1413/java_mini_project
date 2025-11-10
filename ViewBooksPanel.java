import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewBooksPanel extends JPanel {
    private JTextArea booksArea;
    private JButton refreshButton;
    private Library library;

    public ViewBooksPanel(Library library) {
        this.library = library;
        initializeComponents();
        setupLayout();
        setupListeners();
        refreshBooks();
    }

    private void initializeComponents() {
        booksArea = new JTextArea(20, 50);
        booksArea.setEditable(false);
        refreshButton = new JButton("Refresh");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(new JScrollPane(booksArea), BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        refreshButton.addActionListener(e -> refreshBooks());
    }

    private void refreshBooks() {
        List<Book> books = library.getAllBooks();
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.toString()).append(" - ").append(book.isAvailable() ? "Available" : "Borrowed").append("\n");
        }
        booksArea.setText(sb.toString());
    }
}

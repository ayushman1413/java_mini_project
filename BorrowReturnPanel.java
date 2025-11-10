import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowReturnPanel extends JPanel {
    private JTextField isbnField;
    private JButton borrowButton;
    private JButton returnButton;
    private Library library;
    private User user; // Assuming a single user for simplicity

    public BorrowReturnPanel(Library library, User user) {
        this.library = library;
        this.user = user;
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    private void initializeComponents() {
        isbnField = new JTextField(20);
        borrowButton = new JButton("Borrow Book");
        returnButton = new JButton("Return Book");
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(borrowButton, gbc);
        gbc.gridx = 1;
        add(returnButton, gbc);
    }

    private void setupListeners() {
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText().trim();
                if (!isbn.isEmpty()) {
                    if (library.borrowBook(isbn, user)) {
                        JOptionPane.showMessageDialog(BorrowReturnPanel.this, "Book borrowed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(BorrowReturnPanel.this, "Book not available or not found!");
                    }
                } else {
                    JOptionPane.showMessageDialog(BorrowReturnPanel.this, "Please enter ISBN!");
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText().trim();
                if (!isbn.isEmpty()) {
                    if (library.returnBook(isbn)) {
                        JOptionPane.showMessageDialog(BorrowReturnPanel.this, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(BorrowReturnPanel.this, "Book not found or already available!");
                    }
                } else {
                    JOptionPane.showMessageDialog(BorrowReturnPanel.this, "Please enter ISBN!");
                }
            }
        });
    }
}

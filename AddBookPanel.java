import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookPanel extends JPanel {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JButton addButton;
    private Library library;

    public AddBookPanel(Library library) {
        this.library = library;
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    private void initializeComponents() {
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        isbnField = new JTextField(20);
        addButton = new JButton("Add Book");
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(addButton, gbc);
    }

    private void setupListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String isbn = isbnField.getText().trim();

                if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                    Book book = new Book(title, author, isbn);
                    library.addBook(book);
                    JOptionPane.showMessageDialog(AddBookPanel.this, "Book added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AddBookPanel.this, "Please fill all fields!");
                }
            }
        });
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
    }
}

import javax.swing.*;
import java.awt.*;

public class AddBookPanel extends JPanel {
    public AddBookPanel(Library library) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(20);
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField(20);
        JButton addBtn = new JButton("Add Book");

        c.gridx = 0; c.gridy = 0; add(titleLabel, c);
        c.gridx = 1; c.gridy = 0; add(titleField, c);
        c.gridx = 0; c.gridy = 1; add(authorLabel, c);
        c.gridx = 1; c.gridy = 1; add(authorField, c);
        c.gridx = 1; c.gridy = 2; add(addBtn, c);

        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both title and author.");
                return;
            }
            Book b = library.addBook(title, author);
            JOptionPane.showMessageDialog(this, "Added: " + b);
            titleField.setText("");
            authorField.setText("");
        });
    }
}

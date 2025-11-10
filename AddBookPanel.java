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
        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Fiction", "Science", "History", "Biography", "Other"});
        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField(20);
        JLabel copiesLabel = new JLabel("Copies:");
        JTextField copiesField = new JTextField(5);
        JButton addBtn = new JButton("Add Book");

        c.gridx = 0; c.gridy = 0; add(titleLabel, c);
        c.gridx = 1; c.gridy = 0; add(titleField, c);
        c.gridx = 0; c.gridy = 1; add(authorLabel, c);
        c.gridx = 1; c.gridy = 1; add(authorField, c);
        c.gridx = 0; c.gridy = 2; add(categoryLabel, c);
        c.gridx = 1; c.gridy = 2; add(categoryCombo, c);
        c.gridx = 0; c.gridy = 3; add(isbnLabel, c);
        c.gridx = 1; c.gridy = 3; add(isbnField, c);
        c.gridx = 0; c.gridy = 4; add(copiesLabel, c);
        c.gridx = 1; c.gridy = 4; add(copiesField, c);
        c.gridx = 1; c.gridy = 5; add(addBtn, c);

        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            String isbn = isbnField.getText().trim();
            String copiesStr = copiesField.getText().trim();
            if (title.isEmpty() || author.isEmpty() || category == null) {
                JOptionPane.showMessageDialog(this, "Please enter title, author, and category.");
                return;
            }
            int copies = 1;
            try {
                if (!copiesStr.isEmpty()) copies = Integer.parseInt(copiesStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid copies number.");
                return;
            }
            Book b = library.addBook(title, author, category, isbn, copies);
            JOptionPane.showMessageDialog(this, "Added: " + b);
            titleField.setText("");
            authorField.setText("");
            isbnField.setText("");
            copiesField.setText("");
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BorrowReturnPanel extends JPanel {
    private Library library;
    private JComboBox<String> userCombo;
    private JComboBox<String> bookCombo;

    public BorrowReturnPanel(Library library) {
        this.library = library;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Select User:");
        userCombo = new JComboBox<>();
        JLabel bookLabel = new JLabel("Select Available Book:");
        bookCombo = new JComboBox<>();
        JButton borrowBtn = new JButton("Borrow");
        JButton refreshBtn = new JButton("Refresh Lists");
        JButton returnBtn = new JButton("Return Book by ID");

        c.gridx=0; c.gridy=0; add(userLabel,c);
        c.gridx=1; c.gridy=0; add(userCombo,c);
        c.gridx=0; c.gridy=1; add(bookLabel,c);
        c.gridx=1; c.gridy=1; add(bookCombo,c);
        c.gridx=1; c.gridy=2; add(borrowBtn,c);
        c.gridx=1; c.gridy=3; add(refreshBtn,c);
        c.gridx=0; c.gridy=4; add(returnBtn,c);

        borrowBtn.addActionListener(e -> doBorrow());
        refreshBtn.addActionListener(e -> refreshLists());
        returnBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Enter Book ID to return:");
            if (idStr==null) return;
            try {
                int id = Integer.parseInt(idStr.trim());
                boolean ok = library.returnBook(id);
                JOptionPane.showMessageDialog(this, ok? "Returned book "+id : "Return failed");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID");
            }
        });

        refreshLists();
    }

    private void refreshLists() {
        userCombo.removeAllItems();
        for (User u : library.listAllUsers()) {
            userCombo.addItem(u.id + " - " + u.name);
        }
        bookCombo.removeAllItems();
        for (Book b : library.listAllBooks()) {
            if (!b.isBorrowed) bookCombo.addItem(b.id + " - " + b.title);
        }
    }

    private void doBorrow() {
        if (userCombo.getItemCount() == 0 || bookCombo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Ensure users and available books exist.");
            return;
        }
        String userItem = (String) userCombo.getSelectedItem();
        String bookItem = (String) bookCombo.getSelectedItem();
        int userId = Integer.parseInt(userItem.split(" - ")[0].trim());
        int bookId = Integer.parseInt(bookItem.split(" - ")[0].trim());
        String today = LocalDate.now().toString();
        boolean ok = library.borrowBook(userId, bookId, today);
        JOptionPane.showMessageDialog(this, ok? "Borrowed!" : "Borrow failed");
        refreshLists();
    }
}

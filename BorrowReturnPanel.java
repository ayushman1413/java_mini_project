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
        JButton generatePDFBtn = new JButton("Generate PDF Slip");

        c.gridx=0; c.gridy=0; add(userLabel,c);
        c.gridx=1; c.gridy=0; add(userCombo,c);
        c.gridx=0; c.gridy=1; add(bookLabel,c);
        c.gridx=1; c.gridy=1; add(bookCombo,c);
        c.gridx=1; c.gridy=2; add(borrowBtn,c);
        c.gridx=1; c.gridy=3; add(refreshBtn,c);
        c.gridx=0; c.gridy=4; add(returnBtn,c);
        c.gridx=1; c.gridy=4; add(generatePDFBtn,c);

        borrowBtn.addActionListener(e -> doBorrow());
        refreshBtn.addActionListener(e -> refreshLists());
        returnBtn.addActionListener(e -> doReturn());
        generatePDFBtn.addActionListener(e -> generatePDF());

        refreshLists();
    }

    private void refreshLists() {
        userCombo.removeAllItems();
        for (User u : library.listAllUsers()) {
            userCombo.addItem(u.id + " - " + u.name);
        }
        bookCombo.removeAllItems();
        for (Book b : library.listAllBooks()) {
            if (b.copiesAvailable > 0) bookCombo.addItem(b.id + " - " + b.title + " (" + b.copiesAvailable + " available)");
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
        boolean ok = library.borrowBook(userId, bookId);
        if (ok) {
            Book b = library.books.get(bookId);
            JOptionPane.showMessageDialog(this, "Borrowed! Due date: " + b.dueDate);
        } else {
            JOptionPane.showMessageDialog(this, "Borrow failed");
        }
        refreshLists();
    }

    private void doReturn() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Book ID to return:");
        if (idStr==null) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            double fine = library.returnBook(id);
            if (fine >= 0) {
                String msg = "Returned book " + id;
                if (fine > 0) msg += ". Fine: ₹" + fine;
                JOptionPane.showMessageDialog(this, msg);
            } else {
                JOptionPane.showMessageDialog(this, "Return failed");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }

    private void generatePDF() {
        // Placeholder for PDF generation - requires iText library
        JOptionPane.showMessageDialog(this, "PDF generation requires iText library. Feature not implemented yet.");
    }
}

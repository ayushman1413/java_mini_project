import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReservationPanel extends JPanel {
    private Library library;

    public ReservationPanel(Library library) {
        this.library = library;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> userCombo = new JComboBox<>();
        JComboBox<String> bookCombo = new JComboBox<>();
        JButton reserveBtn = new JButton("Reserve Book");
        JButton refreshBtn = new JButton("Refresh");

        c.gridx=0; c.gridy=0; add(new JLabel("User:"), c);
        c.gridx=1; c.gridy=0; add(userCombo, c);
        c.gridx=0; c.gridy=1; add(new JLabel("Book:"), c);
        c.gridx=1; c.gridy=1; add(bookCombo, c);
        c.gridx=1; c.gridy=2; add(reserveBtn, c);
        c.gridx=1; c.gridy=3; add(refreshBtn, c);

        refreshBtn.addActionListener(e -> refreshLists(userCombo, bookCombo));
        reserveBtn.addActionListener(e -> {
            if (userCombo.getItemCount() == 0 || bookCombo.getItemCount() == 0) return;
            int userId = Integer.parseInt(userCombo.getSelectedItem().toString().split(" - ")[0]);
            int bookId = Integer.parseInt(bookCombo.getSelectedItem().toString().split(" - ")[0]);
            if (library.reserveBook(userId, bookId)) {
                JOptionPane.showMessageDialog(this, "Reserved!");
                refreshLists(userCombo, bookCombo);
            } else {
                JOptionPane.showMessageDialog(this, "Reservation failed");
            }
        });

        refreshLists(userCombo, bookCombo);
    }

    private void refreshLists(JComboBox<String> userCombo, JComboBox<String> bookCombo) {
        userCombo.removeAllItems();
        for (User u : library.listAllUsers()) {
            userCombo.addItem(u.id + " - " + u.name);
        }
        bookCombo.removeAllItems();
        for (Book b : library.listAllBooks()) {
            if (b.copiesAvailable == 0) bookCombo.addItem(b.id + " - " + b.title);
        }
    }
}

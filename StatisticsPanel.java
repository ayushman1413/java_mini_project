import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private Library library;
    private JLabel totalBooksLabel;
    private JLabel issuedBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel activeUsersLabel;

    public StatisticsPanel(Library library) {
        this.library = library;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);

        totalBooksLabel = new JLabel("Total Books: " + library.getTotalBooks());
        issuedBooksLabel = new JLabel("Issued Books: " + library.getIssuedBooks());
        availableBooksLabel = new JLabel("Available Books: " + library.getAvailableBooks());
        activeUsersLabel = new JLabel("Active Users: " + library.getActiveUsers());

        c.gridx = 0; c.gridy = 0; add(totalBooksLabel, c);
        c.gridx = 0; c.gridy = 1; add(issuedBooksLabel, c);
        c.gridx = 0; c.gridy = 2; add(availableBooksLabel, c);
        c.gridx = 0; c.gridy = 3; add(activeUsersLabel, c);

        JButton refreshBtn = new JButton("Refresh Stats");
        c.gridx = 0; c.gridy = 4; add(refreshBtn, c);

        refreshBtn.addActionListener(e -> refreshStats());
    }

    public void refreshStats() {
        totalBooksLabel.setText("Total Books: " + library.getTotalBooks());
        issuedBooksLabel.setText("Issued Books: " + library.getIssuedBooks());
        availableBooksLabel.setText("Available Books: " + library.getAvailableBooks());
        activeUsersLabel.setText("Active Users: " + library.getActiveUsers());
    }
}

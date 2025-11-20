import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class DashboardPanel extends JPanel {
    private Library library;

    public DashboardPanel(Library library) {
        this.library = library;
        setLayout(new BorderLayout());

        // Summary cards
        JPanel cards = new JPanel(new GridLayout(1,4));
        cards.add(createCard("Total Books", String.valueOf(library.getTotalBooks())));
        cards.add(createCard("Issued Books", String.valueOf(library.getIssuedBooks())));
        cards.add(createCard("Available Books", String.valueOf(library.getAvailableBooks())));
        cards.add(createCard("Active Users", String.valueOf(library.getActiveUsers())));
        add(cards, BorderLayout.NORTH);
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Available", library.getAvailableBooks());
        dataset.setValue("Issued", library.getIssuedBooks());
        JFreeChart chart = ChartFactory.createPieChart("Book Status", dataset, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(title));
        JLabel label = new JLabel(value, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        card.add(label, BorderLayout.CENTER);
        return card;
    }
}

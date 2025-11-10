import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class NotificationPanel extends JPanel {
    private Library library;
    private JTextArea notificationArea;

    public NotificationPanel(Library library) {
        this.library = library;
        setLayout(new BorderLayout());
        notificationArea = new JTextArea();
        notificationArea.setEditable(false);
        add(new JScrollPane(notificationArea), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh Notifications");
        add(refresh, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadNotifications());

        loadNotifications();
    }

    public void loadNotifications() {
        StringBuilder sb = new StringBuilder();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<Book> books = library.listAllBooks();
        int dueToday = 0, dueTomorrow = 0, overdue = 0;
        for (Book b : books) {
            if (b.dueDate != null) {
                if (b.dueDate.equals(today)) dueToday++;
                else if (b.dueDate.equals(tomorrow)) dueTomorrow++;
                else if (b.dueDate.isBefore(today)) overdue++;
            }
        }

        sb.append("Books due today: ").append(dueToday).append("\n");
        sb.append("Books due tomorrow: ").append(dueTomorrow).append("\n");
        sb.append("Overdue books: ").append(overdue).append("\n");

        notificationArea.setText(sb.toString());
    }
}

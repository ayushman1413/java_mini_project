import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CalendarPanel extends JPanel {
    private Library library;
    private JTextArea calendarArea;

    public CalendarPanel(Library library) {
        this.library = library;
        setLayout(new BorderLayout());
        calendarArea = new JTextArea();
        calendarArea.setEditable(false);
        add(new JScrollPane(calendarArea), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh Calendar");
        add(refresh, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadCalendar());

        loadCalendar();
    }

    public void loadCalendar() {
        StringBuilder sb = new StringBuilder("Due Dates Calendar:\n\n");
        List<Book> books = library.listAllBooks();
        for (Book b : books) {
            if (b.dueDate != null) {
                sb.append(b.dueDate).append(": ").append(b.title).append(" (User ").append(b.borrowedByUserId).append(")\n");
            }
        }
        calendarArea.setText(sb.toString());
    }
}

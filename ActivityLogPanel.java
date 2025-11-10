import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActivityLogPanel extends JPanel {
    private Library library;
    private JTable table;
    private DefaultTableModel model;

    public ActivityLogPanel(Library library) {
        this.library = library;
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Timestamp","Action","Details"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh Logs");
        add(refresh, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadLogs());

        loadLogs();
    }

    public void loadLogs() {
        model.setRowCount(0);
        List<ActivityLog> logs = library.getActivityLogs();
        for (ActivityLog log : logs) {
            model.addRow(new Object[]{log.timestamp, log.action, log.details});
        }
    }
}

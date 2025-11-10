import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewBooksPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Library library;

    public ViewBooksPanel(Library library) {
        this.library = library;
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"ID","Title","Author","Status","BorrowedBy","BorrowDate"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton refresh = new JButton("Refresh");
        JButton delete = new JButton("Delete Selected");
        bottom.add(refresh);
        bottom.add(delete);
        add(bottom, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadData());
        delete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this,"Select a row"); return; }
            int id = (int) model.getValueAt(r, 0);
            if (library.removeBook(id)) {
                JOptionPane.showMessageDialog(this, "Book removed: " + id);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove");
            }
        });

        loadData();
    }

    public void loadData() {
        model.setRowCount(0);
        List<Book> books = library.listAllBooks();
        for (Book b : books) {
            model.addRow(new Object[]{b.id, b.title, b.author, (b.isBorrowed ? "Borrowed":"Available"),
                    (b.borrowedByUserId==null? "": b.borrowedByUserId), b.borrowDate});
        }
    }
}

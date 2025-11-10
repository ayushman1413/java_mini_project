import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ViewBooksPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Library library;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    public ViewBooksPanel(Library library) {
        this.library = library;
        setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchField.addActionListener(e -> filterTable());
        searchPanel.add(searchField);
        add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID","Title","Author","Category","ISBN","Copies","Status","BorrowedBy","DueDate"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton refresh = new JButton("Refresh");
        JButton delete = new JButton("Delete Selected");
        JButton export = new JButton("Export Borrowed");
        bottom.add(refresh);
        bottom.add(delete);
        bottom.add(export);
        add(bottom, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadData());
        delete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this,"Select a row"); return; }
            int id = (int) model.getValueAt(table.convertRowIndexToModel(r), 0);
            if (library.removeBook(id)) {
                JOptionPane.showMessageDialog(this, "Book removed: " + id);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove");
            }
        });
        export.addActionListener(e -> exportBorrowedBooks());

        loadData();
    }

    public void loadData() {
        model.setRowCount(0);
        List<Book> books = library.listAllBooks();
        for (Book b : books) {
            model.addRow(new Object[]{b.id, b.title, b.author, b.category, b.isbn, b.copiesAvailable,
                    (b.isBorrowed ? "Borrowed":"Available"),
                    (b.borrowedByUserId==null? "": b.borrowedByUserId), (b.dueDate==null? "": b.dueDate)});
        }
    }

    private void filterTable() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private void exportBorrowedBooks() {
        try (FileWriter writer = new FileWriter("borrowed_books.csv")) {
            writer.write("ID,Title,Author,Category,ISBN,BorrowedBy,DueDate\n");
            for (Book b : library.listAllBooks()) {
                if (b.isBorrowed) {
                    writer.write(String.format("%d,%s,%s,%s,%s,%d,%s\n",
                            b.id, b.title, b.author, b.category, b.isbn, b.borrowedByUserId, b.dueDate));
                }
            }
            JOptionPane.showMessageDialog(this, "Exported to borrowed_books.csv");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Export failed: " + e.getMessage());
        }
    }
}

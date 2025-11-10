import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {
    private Library library;
    private User loggedInUser;
    private ViewBooksPanel viewBooksPanel;
    private AddBookPanel addBookPanel;
    private BorrowReturnPanel borrowReturnPanel;
    private StatisticsPanel statisticsPanel;
    private JPanel cards;

    public MainFrame(Library library, User loggedInUser) {
        this.library = library;
        this.loggedInUser = loggedInUser;
        setTitle("Library Management System - Logged in as: " + loggedInUser.name + " (" + loggedInUser.role + ")");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top menu
        JPanel top = new JPanel();
        JButton btnView = new JButton("View Books");
        JButton btnAdd = new JButton("Add Book");
        JButton btnBorrow = new JButton("Borrow/Return");
        JButton btnStats = new JButton("Statistics");
        JButton btnAddUser = new JButton("Add User");
        JButton btnDarkMode = new JButton("Toggle Dark Mode");
        top.add(btnView); top.add(btnAdd); top.add(btnBorrow); top.add(btnStats); top.add(btnAddUser); top.add(btnDarkMode);
        add(top, BorderLayout.NORTH);

        // Cards area
        cards = new JPanel(new CardLayout());
        viewBooksPanel = new ViewBooksPanel(library);
        addBookPanel = new AddBookPanel(library);
        borrowReturnPanel = new BorrowReturnPanel(library);
        statisticsPanel = new StatisticsPanel(library);

        cards.add(viewBooksPanel, "VIEW");
        cards.add(addBookPanel, "ADD");
        cards.add(borrowReturnPanel, "BORROW");
        cards.add(statisticsPanel, "STATS");
        add(cards, BorderLayout.CENTER);

        // Role-based access
        if (!"Admin".equals(loggedInUser.role)) {
            btnAdd.setEnabled(false);
            btnAddUser.setEnabled(false);
        }

        // Add user quick dialog
        btnAddUser.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "User name:");
            if (name == null || name.trim().isEmpty()) return;
            String email = JOptionPane.showInputDialog(this, "Email (optional):");
            String phone = JOptionPane.showInputDialog(this, "Phone (optional):");
            String role = JOptionPane.showInputDialog(this, "Role (Admin/User):");
            if (role == null || (!role.equals("Admin") && !role.equals("User"))) role = "User";
            String studentId = JOptionPane.showInputDialog(this, "Student ID (optional):");
            library.addUser(name.trim(), email==null? "": email.trim(), phone==null? "": phone.trim(), role, studentId==null? "": studentId.trim());
            JOptionPane.showMessageDialog(this, "User added.");
        });

        btnView.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "VIEW");
            viewBooksPanel.loadData();
        });
        btnAdd.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "ADD");
        });
        btnBorrow.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "BORROW");
        });
        btnStats.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "STATS");
            statisticsPanel.refreshStats();
        });
        btnDarkMode.addActionListener(e -> toggleDarkMode());

        // Notification for due dates
        checkDueDates();

        // On close save
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Storage.save(library, "library_data.ser");
                super.windowClosing(e);
            }
        });

        setVisible(true);
    }

    private void toggleDarkMode() {
        // Simple dark mode toggle - in real app, use UIManager
        getContentPane().setBackground(Color.DARK_GRAY);
        JOptionPane.showMessageDialog(this, "Dark mode toggled (basic implementation).");
    }

    private void checkDueDates() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        List<Book> books = library.listAllBooks();
        for (Book b : books) {
            if (b.dueDate != null && (b.dueDate.equals(today) || b.dueDate.equals(tomorrow))) {
                JOptionPane.showMessageDialog(this, "Book '" + b.title + "' is due " +
                    (b.dueDate.equals(today) ? "today" : "tomorrow") + "!");
            }
        }
    }
}
